package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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

    // 加载布局配置（根据设备类型自动选择）
    val configResult = remember {
        val repository = LayoutConfigRepository(context)
        // 🎯 自动加载配置：
        // - 平板（sw >= 600dp）: layout_tablet.json (rows=4)
        // - 手机（sw < 600dp）: layout_phone.json (rows=2)
        repository.loadLayoutConfigForDevice()
    }

    // 提取实际使用的配置
    val layoutConfig = when (configResult) {
        is ConfigLoadResult.Success -> configResult.config
        is ConfigLoadResult.Error -> configResult.fallbackConfig
            ?: LayoutConfigRepository(context).getSafeDefaultLayoutConfig()
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
            // 最佳实践：固定间距（8dp）+ 动态瓷砖尺寸
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000000))  // 黑色背景
                    .padding(8.dp)
            ) {
                TileGridContainer(
                    modifier = Modifier.fillMaxSize(),
                    gridRows = layoutConfig.rows
                ) { baseCellSize, fixedGap, columns, screenHeight ->
                    ProvideTileGrid(
                        baseCellSize = baseCellSize,
                        dynamicGap = fixedGap,  // 使用固定间距
                        columns = columns
                    ) {
                        GridAreaLayout(
                            config = layoutConfig,
                            baseCellSize = baseCellSize,
                            dynamicGap = fixedGap,  // 使用固定间距
                            modifier = Modifier  // 不添加背景，渲染真实瓷砖
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
