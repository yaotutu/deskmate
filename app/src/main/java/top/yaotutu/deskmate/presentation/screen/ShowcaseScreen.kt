package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.data.model.LayoutConfig
import top.yaotutu.deskmate.data.model.TileDefinition
import top.yaotutu.deskmate.data.repository.LayoutConfigRepository
import top.yaotutu.deskmate.presentation.component.base.TileGridContainer
import top.yaotutu.deskmate.presentation.component.factory.TileFactory
import top.yaotutu.deskmate.presentation.component.layout.GridAreaLayout
import top.yaotutu.deskmate.presentation.component.base.ProvideTileGrid
import top.yaotutu.deskmate.presentation.viewmodel.DashboardUiState

/**
 * 桌面展示页面 - 完整的 Metro 风格桌面
 *
 * 特性：
 * - 自动适配手机（4行）和平板（8行）
 * - 展示所有尺寸的瓷砖组合
 * - 真实的使用场景布局
 * - 无任何测试标记
 */
@Composable
fun ShowcaseScreen() {
    val context = LocalContext.current
    val repository = remember { LayoutConfigRepository(context) }
    val isTablet = remember { repository.isTablet() }

    // 根据设备类型选择布局配置
    val layoutConfig = if (isTablet) {
        // 平板布局 - 8行完整版
        LayoutConfig(
            areas = listOf(
                // 第1-2行：大时钟 + 天气 + 快捷图标
                "C C W W A B",
                "C C W W D E",
                // 第3-4行：日历 + 待办 + 新闻
                "F F G G H H",
                "F F G G H H",
                // 第5-6行：大新闻横幅 + 副时钟
                "I I I I J J",
                "I I I I J J",
                // 第7-8行：详细天气 + 小部件组
                "K K K K L M",
                "K K K K N O"
            ),
            tiles = mapOf(
                // 第1-2行：核心信息
                "C" to TileDefinition("clock", "2x2"),
                "W" to TileDefinition("weather", "2x2"),
                "A" to TileDefinition("calendar", "1x1"),
                "B" to TileDefinition("todo", "1x1"),
                "D" to TileDefinition("news", "1x1"),
                "E" to TileDefinition("weather", "1x1"),

                // 第3-4行：日程管理
                "F" to TileDefinition("calendar", "2x2"),
                "G" to TileDefinition("todo", "2x2"),
                "H" to TileDefinition("news", "2x2"),

                // 第5-6行：资讯区
                "I" to TileDefinition("news", "4x2"),
                "J" to TileDefinition("clock", "2x2"),

                // 第7-8行：天气详情 + 快捷方式
                "K" to TileDefinition("weather", "4x2"),
                "L" to TileDefinition("clock", "1x1"),
                "M" to TileDefinition("calendar", "1x1"),
                "N" to TileDefinition("todo", "1x1"),
                "O" to TileDefinition("news", "1x1")
            )
        )
    } else {
        // 手机布局 - 4行版本
        LayoutConfig(
            areas = listOf(
                // 第1-2行：大时钟 + 天气 + 快捷图标
                "C C W W A B",
                "C C W W D E",
                // 第3-4行：日历 + 待办 + 新闻
                "F F G G H H",
                "F F G G H H"
            ),
            tiles = mapOf(
                // 第1-2行：核心信息
                "C" to TileDefinition("clock", "2x2"),
                "W" to TileDefinition("weather", "2x2"),
                "A" to TileDefinition("calendar", "1x1"),
                "B" to TileDefinition("todo", "1x1"),
                "D" to TileDefinition("news", "1x1"),
                "E" to TileDefinition("weather", "1x1"),

                // 第3-4行：日程管理
                "F" to TileDefinition("calendar", "2x2"),
                "G" to TileDefinition("todo", "2x2"),
                "H" to TileDefinition("news", "2x2")
            )
        )
    }

    // 模拟真实数据
    val uiState = DashboardUiState(
        currentTime = "14:32",
        currentDate = "星期四, 11月 7日",
        currentWeekday = "星期四",
        currentDayNumber = "7",
        currentMonthName = "11月",
        lunarDate = "农历十月初七",
        lunarDayName = "初七",
        lunarMonthName = "十月",
        temperature = 18,
        currentMonth = 11,
        currentYear = 2025,
        currentDay = 7
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(8.dp)
    ) {
        val containerWidth = maxWidth   // 获取容器实际宽度
        val containerHeight = maxHeight // 获取容器实际高度

        TileGridContainer(
            modifier = Modifier.fillMaxSize(),
            isTablet = isTablet
        ) { baseCellSize, fixedGap, columns, gridRows ->
            ProvideTileGrid(baseCellSize = baseCellSize, dynamicGap = fixedGap, columns = columns) {
                GridAreaLayout(
                    config = layoutConfig,
                    baseCellSize = baseCellSize,
                    dynamicGap = fixedGap,
                    screenWidth = containerWidth,   // 传递屏幕宽度
                    screenHeight = containerHeight, // 传递屏幕高度，确保8行完整显示
                    modifier = Modifier.fillMaxSize()
                ) { config, index ->
                    TileFactory.CreateTile(config, uiState, index)
                }
            }
        }
    }
}
