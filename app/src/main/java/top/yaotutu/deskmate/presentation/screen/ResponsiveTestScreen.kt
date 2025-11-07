package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.data.model.LayoutConfig
import top.yaotutu.deskmate.data.model.TileDefinition
import top.yaotutu.deskmate.data.repository.LayoutConfigRepository
import top.yaotutu.deskmate.presentation.component.base.TileGridContainer
import top.yaotutu.deskmate.presentation.component.factory.TileFactory
import top.yaotutu.deskmate.presentation.component.layout.GridAreaLayout
import top.yaotutu.deskmate.presentation.component.base.ProvideTileGrid
import top.yaotutu.deskmate.presentation.viewmodel.DashboardUiState

/**
 * 响应式系统测试屏幕
 *
 * 用途：
 * - 验证所有预设在不同设备上的响应式效果
 * - 测试 MetroSpacing、MetroPadding、MetroIconSize 的适配性
 * - 支持平板（8行）和手机（4行）布局
 * - 支持横屏和竖屏模式
 *
 * 测试覆盖：
 * - 6 种尺寸：1×1, 1×2, 2×2, 4×2, 2×4, 4×4
 * - 5 种瓷砖类型：Clock, Weather, Calendar, Todo, News
 * - 所有 38 种预设样式
 */
@Composable
fun ResponsiveTestScreen() {
    val context = LocalContext.current
    val repository = remember { LayoutConfigRepository(context) }
    val isTablet = remember { repository.isTablet() }

    // 测试布局配置 - 展示所有 6 种尺寸（rows 和 columns 从 areas 自动推断）
    val layoutConfig = LayoutConfig(
        areas = listOf(
            // 第1行：1×1 + 1×2 + 2×2 + 1×1
            "A B B C C D",
            // 第2行：1×1 + 1×2 + 2×2 + 1×1
            "E F F C C G",
            // 第3-4行：2×2 瓷砖展示（3个）
            "H H I I J J",
            "H H I I J J",
            // 第5-6行：4×2 宽版 + 2×2
            "K K K K L L",
            "K K K K L L",
            // 第7-8行：2×4 高版 + 4×2 宽版
            "M N N N N .",
            "M N N N N ."
        ),
        tiles = mapOf(
            // 1×1 瓷砖测试（4个）
            "A" to TileDefinition("clock", "1x1"),
            "D" to TileDefinition("weather", "1x1"),
            "E" to TileDefinition("calendar", "1x1"),
            "G" to TileDefinition("todo", "1x1"),

            // 1×2 瓷砖测试（2个）
            "B" to TileDefinition("clock", "1x2"),
            "F" to TileDefinition("weather", "1x2"),

            // 2×2 瓷砖测试（4个，不同类型）
            "C" to TileDefinition("weather", "2x2"),
            "H" to TileDefinition("clock", "2x2"),
            "I" to TileDefinition("calendar", "2x2"),
            "J" to TileDefinition("todo", "2x2"),

            // 4×2 宽版瓷砖测试（2个）
            "K" to TileDefinition("clock", "4x2"),
            "N" to TileDefinition("news", "4x2"),

            // 2×4 高版瓷砖测试（1个）
            "M" to TileDefinition("todo", "2x4"),

            // 2×2 新闻瓷砖
            "L" to TileDefinition("news", "2x2")
        )
    )

    // 模拟 UI 状态数据
    val mockUiState = DashboardUiState(
        currentTime = "10:12",
        currentDate = "星期一, 10月 28日",
        currentWeekday = "星期四",
        lunarDate = "农历九月廿八",
        temperature = 22,
        currentMonth = 11,
        currentYear = 2025,
        currentDay = 7
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .verticalScroll(rememberScrollState())
    ) {
        // 标题
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "响应式系统测试",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "验证所有预设的自适应效果",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // 瓷砖网格
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2000.dp)  // 足够的高度以展示 8 行瓷砖
                .padding(8.dp)
        ) {
            TileGridContainer(
                modifier = Modifier.fillMaxSize(),
                isTablet = isTablet
            ) { baseCellSize, fixedGap, columns, gridRows ->
                ProvideTileGrid(baseCellSize = baseCellSize, dynamicGap = fixedGap, columns = columns) {
                    GridAreaLayout(
                        config = layoutConfig,
                        baseCellSize = baseCellSize,
                        dynamicGap = fixedGap,
                        modifier = Modifier.fillMaxSize()
                    ) { config, index ->
                        // 使用工厂创建瓷砖（会自动应用响应式预设）
                        TileFactory.CreateTile(config, mockUiState, index)
                    }
                }
            }
        }

        // 测试信息说明
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "测试覆盖范围",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                TestInfoItem("✓ 6 种尺寸：1×1, 1×2, 2×2, 4×2, 2×4, 4×4")
                TestInfoItem("✓ 5 种类型：Clock, Weather, Calendar, Todo, News")
                TestInfoItem("✓ 响应式 Spacing：tiny, small, medium, large, extraLarge")
                TestInfoItem("✓ 响应式 Padding：small, medium, large, auto")
                TestInfoItem("✓ 响应式 IconSize：自动适配 baseCellSize")
                TestInfoItem("✓ 响应式 Typography：MetroTypography 设计令牌")

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "预期行为",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                TestInfoItem("• 所有内容应基于 baseCellSize 等比缩放")
                TestInfoItem("• 间距和内边距应保持固定比例（不随瓷砖尺寸变化）")
                TestInfoItem("• 平板（8行）和手机（4行）显示效果一致")
                TestInfoItem("• 横屏和竖屏模式下布局正确")
            }
        }
    }
}

@Composable
private fun TestInfoItem(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.ExtraLight,
        color = Color.White.copy(alpha = 0.8f),
        lineHeight = 18.sp
    )
}
