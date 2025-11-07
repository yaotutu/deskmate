package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
        // 🎯 加载测试配置：根据设备类型选择
        // TODO: 改回 repository.loadLayoutConfigForDevice() 用于生产环境
        val testFileName = if (isTablet) {
            "layout_size_test.json"        // 平板：8行测试布局
        } else {
            "layout_size_test_phone.json"  // 手机：4行测试布局
        }
        repository.loadLayoutConfigWithResult(testFileName)
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
            // 2025-01-06 重构：基于设备类型的固定行数 + 动态列数 + 容器级缩放
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000000))  // 黑色背景
                    .padding(8.dp)
            ) {
                val containerWidth = maxWidth   // ⭐ 获取容器实际宽度
                val containerHeight = maxHeight // ⭐ 获取容器实际高度

                TileGridContainer(
                    modifier = Modifier.fillMaxSize(),
                    isTablet = isTablet  // ✅ 传递设备类型
                ) { baseCellSize, fixedGap, columns, gridRows ->
                    ProvideTileGrid(
                        baseCellSize = baseCellSize,
                        dynamicGap = fixedGap,
                        columns = columns
                    ) {
                        GridAreaLayout(
                            config = layoutConfig,
                            baseCellSize = baseCellSize,
                            dynamicGap = fixedGap,
                            screenWidth = containerWidth,   // ⭐ 传递屏幕宽度
                            screenHeight = containerHeight, // ⭐ 传递屏幕高度，确保整个网格都能显示
                            modifier = Modifier.fillMaxSize()
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
