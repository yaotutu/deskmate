package top.yaotutu.deskmate.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import top.yaotutu.deskmate.data.repository.LayoutConfigRepository
import top.yaotutu.deskmate.presentation.ui.component.base.TileGridContainer
import top.yaotutu.deskmate.presentation.ui.component.legacy.ProvideTileGrid
import top.yaotutu.deskmate.presentation.ui.component.layout.VerticalPriorityLayout
import top.yaotutu.deskmate.presentation.ui.component.factory.TileFactory
import top.yaotutu.deskmate.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // 加载布局配置
    val layoutConfig = remember {
        val repository = LayoutConfigRepository(context)
        repository.loadLayoutConfig() ?: repository.getDefaultLayoutConfig()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF000000))  // Metro 风格：纯黑背景
            .padding(8.dp)
    ) {
        // Windows Phone 动态瓷砖布局 - 配置驱动的垂直优先布局
        TileGridContainer(modifier = Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns, screenHeight ->
            // 使用 CompositionLocal 提供网格参数，简化组件使用
            ProvideTileGrid(baseCellSize = baseCellSize, dynamicGap = dynamicGap, columns = columns) {
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
                        index = index
                    )
                }
            }
        }
    }
}
