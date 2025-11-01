package top.yaotutu.deskmate.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.data.model.LayoutConfig
import top.yaotutu.deskmate.data.model.TileConfig
import top.yaotutu.deskmate.presentation.ui.component.base.TileGridContainer
import top.yaotutu.deskmate.presentation.ui.component.factory.TileFactory
import top.yaotutu.deskmate.presentation.ui.component.layout.VerticalPriorityLayout
import top.yaotutu.deskmate.presentation.ui.component.base.ProvideTileGrid
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
    // 定义布局配置 - 简化版测试
    val layoutConfig = LayoutConfig(
        tiles = listOf(
            // === 第 1 列 ===
            TileConfig("clock", "simple", 1, 1),          // 简单时钟
            TileConfig("demo_icon", "icon_only", 1, 1),   // 图标
            TileConfig("clock", "compact", 2, 1),         // 紧凑时钟
            TileConfig("demo_progress", "progress", 2, 1), // 进度条

            // === 第 2 列 ===
            TileConfig("clock", "standard", 2, 2),        // 标准时钟
            TileConfig("demo_weather", "counter", 2, 2),  // 天气

            // === 第 3 列 ===
            TileConfig("demo_music", "music", 2, 2),      // 音乐
            TileConfig("demo_photo", "photo", 2, 2),      // 照片

            // === 第 4 列 ===
            TileConfig("demo_media", "media_player", 4, 2), // 媒体播放器

            // === 第 5 列 ===
            TileConfig("demo_todo", "list", 2, 4),        // 待办列表

            // === 第 6 列 ===
            TileConfig("demo_dashboard", "dashboard", 4, 4) // 仪表盘
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
                VerticalPriorityLayout(
                    tiles = layoutConfig.tiles,
                    baseCellSize = baseCellSize,
                    dynamicGap = dynamicGap,
                    maxHeight = screenHeight,
                    modifier = Modifier.fillMaxSize()
                ) { config, index ->
                    // 使用工厂创建瓷砖（会自动使用预设）
                    TileFactory.CreateTile(config, mockUiState, index)
                }
            }
        }
    }
}
