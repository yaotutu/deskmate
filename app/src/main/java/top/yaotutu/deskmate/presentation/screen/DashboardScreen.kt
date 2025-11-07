package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.yaotutu.deskmate.data.model.ConfigLoadResult
import top.yaotutu.deskmate.data.repository.LayoutConfigRepository
import top.yaotutu.deskmate.presentation.component.base.ProvideTileGrid
import top.yaotutu.deskmate.presentation.component.base.TileGridContainer
import top.yaotutu.deskmate.presentation.component.common.ConfigErrorBanner
import top.yaotutu.deskmate.presentation.component.factory.TileFactory
import top.yaotutu.deskmate.presentation.component.layout.GridAreaLayout
import top.yaotutu.deskmate.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onTileClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val repository = remember { LayoutConfigRepository(context) }

    // 检测设备类型（2025-01-06 重构）
    val isTablet = remember { repository.isTablet() }

    // 加载布局配置（根据设备类型自动选择）
    val configResult = remember {
        // 📱 自动加载设备对应的配置文件
        val configFileName = if (isTablet) {
            "layout_tablet.json"  // 平板：8行×14列（支持横向滚动）
        } else {
            "layout_phone.json"   // 手机：4行×10列（支持横向滚动）
        }
        repository.loadLayoutConfigWithResult(configFileName)
    }

    // 提取实际使用的配置
    val layoutConfig = when (configResult) {
        is ConfigLoadResult.Success -> configResult.config
        is ConfigLoadResult.Error -> configResult.fallbackConfig
            ?: repository.getSafeDefaultLayoutConfig()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF000000))  // Metro 风格：纯黑背景
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 错误横幅（如果配置加载失败）
            if (configResult is ConfigLoadResult.Error) {
                ConfigErrorBanner(
                    error = configResult,
                    modifier = Modifier
                )
            }

            // Windows Phone 动态瓷砖布局 - 网格区域布局系统
            // 2025-01-07 重构：支持横向滚动 + 配置驱动
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000000))  // 黑色背景
                    .padding(8.dp)
            ) {
                val screenHeight = maxHeight
                val scrollState = rememberScrollState()

                Box(modifier = Modifier.horizontalScroll(scrollState)) {
                    TileGridContainer(
                        modifier = Modifier.height(screenHeight),  // ✅ 使用屏幕高度
                        isTablet = isTablet  // ✅ 传递设备类型
                    ) { baseCellSize, fixedGap, columns, gridRows ->
                        // ⭐ 根据设备类型设置实际列数和行数（支持横向滚动）
                        val totalColumns = if (isTablet) 14 else 10
                        val totalRows = if (isTablet) 8 else 4
                        val contentWidth = baseCellSize * totalColumns + fixedGap * (totalColumns - 1)
                        val contentHeight = baseCellSize * totalRows + fixedGap * (totalRows - 1)

                        ProvideTileGrid(
                            baseCellSize = baseCellSize,
                            dynamicGap = fixedGap,
                            columns = totalColumns
                        ) {
                            GridAreaLayout(
                                config = layoutConfig,
                                baseCellSize = baseCellSize,
                                dynamicGap = fixedGap,
                                modifier = Modifier
                                    .width(contentWidth)
                                    .height(contentHeight)  // ✅ 设置正确的高度，防止底部被截断
                            ) { tileConfig, index ->
                                // 使用瓷砖工厂创建真实瓷砖
                                TileFactory.CreateTile(
                                    config = tileConfig,
                                    uiState = uiState,
                                    index = index,
                                    onClick = onTileClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
