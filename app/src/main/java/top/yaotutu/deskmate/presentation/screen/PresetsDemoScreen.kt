package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.data.model.LayoutConfig
import top.yaotutu.deskmate.data.model.TileDefinition
import top.yaotutu.deskmate.presentation.component.base.TileGridContainer
import top.yaotutu.deskmate.presentation.component.factory.TileFactory
import top.yaotutu.deskmate.presentation.component.layout.GridAreaLayout
import top.yaotutu.deskmate.presentation.component.base.ProvideTileGrid
import top.yaotutu.deskmate.presentation.viewmodel.DashboardUiState

/**
 * 预设系统演示主页
 *
 * 特性：
 * - 展示所有 6 种尺寸的预设
 * - 充分利用屏幕空间，无留白
 * - 覆盖多种使用场景
 * - 采用配置驱动方式
 */
@Composable
fun PresetsDemoScreen() {
    // 定义布局配置 - 网格区域版本
    val layoutConfig = LayoutConfig(
        columns = 6,
        rows = 4,
        areas = listOf(
            "A B B C C D",
            "A B B C C D",
            "E E F F G G",
            "E E F F G G"
        ),
        tiles = mapOf(
            "A" to TileDefinition("clock", "1x1"),
            "B" to TileDefinition("clock", "2x2"),
            "C" to TileDefinition("clock", "2x2"),
            "D" to TileDefinition("clock", "1x1"),
            "E" to TileDefinition("clock", "2x2"),
            "F" to TileDefinition("clock", "2x2"),
            "G" to TileDefinition("clock", "2x2")
        )
    )

    // 模拟 UI 状态数据
    val mockUiState = DashboardUiState(
        currentTime = "10:12",
        currentDate = "星期一, 10月 28日",
        lunarDate = "农历九月廿八",
        temperature = 22
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        TileGridContainer(Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns, screenHeight ->
            ProvideTileGrid(baseCellSize = baseCellSize, dynamicGap = dynamicGap, columns = columns) {
                GridAreaLayout(
                    config = layoutConfig,
                    baseCellSize = baseCellSize,
                    dynamicGap = dynamicGap,
                    modifier = Modifier.fillMaxSize()
                ) { config, index ->
                    // 使用工厂创建瓷砖（会自动使用预设）
                    TileFactory.CreateTile(config, mockUiState, index)
                }
            }
        }
    }
}
