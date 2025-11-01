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
import top.yaotutu.deskmate.presentation.component.layout.VerticalPriorityLayout
import top.yaotutu.deskmate.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onTileClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // 加载布局配置（新版本 - 获取详细错误信息）
    val configResult = remember {
        val repository = LayoutConfigRepository(context)
        // 🕐 时钟展示模式：使用 clock_showcase.json（展示所有时钟变体）
        repository.loadLayoutConfigWithResult("clock_showcase.json")
        // 🎯 完美布局模式：repository.loadLayoutConfigWithResult("perfect_layout.json")
        // 💡 正常模式：repository.loadLayoutConfigWithResult()
        // 🎬 动画演示模式：repository.loadLayoutConfigWithResult("animation_demo.json")
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

            // Windows Phone 动态瓷砖布局 - 配置驱动的垂直优先布局
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                TileGridContainer(modifier = Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns, screenHeight ->
                    // 使用 CompositionLocal 提供网格参数，简化组件使用
                    ProvideTileGrid(
                        baseCellSize = baseCellSize,
                        dynamicGap = dynamicGap,
                        columns = columns
                    ) {
                        // 使用垂直优先布局引擎
                        VerticalPriorityLayout(
                            tiles = layoutConfig.tiles,
                            baseCellSize = baseCellSize,
                            dynamicGap = dynamicGap,
                            // maxHeight 需要包含瓷砖间的间距
                            // 4行瓷砖 = baseCellSize*4 + 中间3个间距
                            maxHeight = baseCellSize * 4 + dynamicGap * 3,
                            modifier = Modifier.fillMaxSize()
                        ) { tileConfig, index ->
                            // 使用瓷砖工厂创建瓷砖
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
