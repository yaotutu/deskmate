package top.yaotutu.deskmate.presentation.ui.component.legacy

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.ui.component.base.Tile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSize
import top.yaotutu.deskmate.presentation.ui.component.base.TileGrid
import top.yaotutu.deskmate.presentation.ui.component.animation.FlipTileAnimation
import top.yaotutu.deskmate.presentation.ui.component.animation.PulseTileAnimation
import top.yaotutu.deskmate.presentation.ui.component.animation.SlideTileAnimation

// ==================== CompositionLocal 网格参数传递 ====================

/**
 * 网格参数 CompositionLocal
 */
val LocalBaseCellSize = compositionLocalOf<Dp> { error("BaseCellSize not provided") }
val LocalDynamicGap = compositionLocalOf<Dp> { error("DynamicGap not provided") }
val LocalColumns = compositionLocalOf<Int> { error("Columns not provided") }

/**
 * 提供网格参数的容器
 *
 * @param baseCellSize 基础格子尺寸（正方形边长）
 * @param dynamicGap 动态间距
 * @param columns 实际列数
 * @param content 子组件
 */
@Composable
fun ProvideTileGrid(
    baseCellSize: Dp,
    dynamicGap: Dp,
    columns: Int,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalBaseCellSize provides baseCellSize,
        LocalDynamicGap provides dynamicGap,
        LocalColumns provides columns
    ) {
        content()
    }
}

// ==================== 基础瓷砖组件 ====================

/**
 * 时钟瓷砖 (4×2)
 *
 * 特性：
 * - 自动翻转动画
 * - 正面显示时间，背面显示日期和农历
 *
 * @param time 当前时间（如 "10:07"）
 * @param date 日期（如 "星期一, 10月 27日"）
 * @param lunarDate 农历（如 "农历八月廿一"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 */
@Composable
fun ClockTile(
    time: String,
    date: String,
    lunarDate: String,
    backgroundColor: Color = MetroTileColors.Time,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.WIDE_MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        FlipTileAnimation(
            frontContent = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = time,
                        fontSize = 96.sp,
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                        lineHeight = 96.sp
                    )
                }
            },
            backContent = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = date,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        text = lunarDate,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        )
    }
}

/**
 * 天气瓷砖 (2×2)
 *
 * 特性：
 * - 脉冲动画效果
 * - 显示图标和温度
 *
 * @param temperature 温度值
 * @param icon 天气图标（如 "☀", "☁", "⛈"）
 * @param backgroundColor 背景颜色（默认 Metro 橙）
 * @param enableAnimation 是否启用脉冲动画
 */
@Composable
fun WeatherTile(
    temperature: Int,
    icon: String = "☀",
    backgroundColor: Color = MetroTileColors.Weather,
    enableAnimation: Boolean = true,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        PulseTileAnimation(
            enabled = enableAnimation,
            scaleRange = Pair(1.0f, 1.02f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = icon,
                        fontSize = 72.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$temperature°",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                        lineHeight = 56.sp
                    )
                }
            }
        }
    }
}

/**
 * 日历瓷砖 (2×2)
 *
 * 特性：
 * - 显示月份和日期
 * - 超大日期数字
 *
 * @param month 月份（如 "十月"）
 * @param day 日期数字
 * @param backgroundColor 背景颜色（默认 Metro 绿）
 */
@Composable
fun CalendarTile(
    month: String,
    day: Int,
    backgroundColor: Color = MetroTileColors.Calendar,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = month,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = day.toString(),
                    fontSize = 88.sp,
                    fontWeight = FontWeight.Thin,
                    color = Color.White,
                    lineHeight = 88.sp
                )
            }
        }
    }
}

/**
 * 待办瓷砖 (2×4 竖长条)
 *
 * 特性：
 * - 显示待办事项列表
 * - 左对齐布局
 *
 * @param title 标题
 * @param items 待办事项列表
 * @param backgroundColor 背景颜色（默认 Metro 亮紫）
 */
@Composable
fun TodoTile(
    title: String = "待办",
    items: List<String>,
    backgroundColor: Color = MetroTileColors.Todo,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.TALL,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            items.take(3).forEach { item ->
                Text(
                    text = item,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.95f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

/**
 * 新闻瓷砖 (4×4 大方块)
 *
 * 特性：
 * - 自动轮播新闻
 * - 滑动切换动画
 *
 * @param newsItems 新闻项列表（标题 + 内容）
 * @param backgroundColor 背景颜色（默认 Metro 鲜红）
 * @param slideIntervalMillis 切换间隔时间
 */
@Composable
fun NewsTile(
    newsItems: List<Pair<String, String>>,
    backgroundColor: Color = MetroTileColors.News,
    slideIntervalMillis: Long = 4000,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.EXTRA_LARGE,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        val composableItems: List<@Composable () -> Unit> = newsItems.map { (title, content) ->
            @Composable {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = title,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Text(
                            text = content,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.95f),
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }

        SlideTileAnimation(
            contents = composableItems,
            slideIntervalMillis = slideIntervalMillis
        )
    }
}

/**
 * 自定义内容瓷砖 - 2×2 正方形
 *
 * 用于完全自定义内容的场景
 *
 * @param backgroundColor 背景颜色
 * @param content 自定义内容
 */
@Composable
fun CustomSquareTile(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

/**
 * 自定义内容瓷砖 - 4×2 横条
 */
@Composable
fun CustomMediumWideTile(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.WIDE_MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

/**
 * 自定义内容瓷砖 - 2×4 竖长条
 */
@Composable
fun CustomTallTile(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.TALL,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

/**
 * 自定义内容瓷砖 - 4×4 大方块
 */
@Composable
fun CustomLargeTile(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.EXTRA_LARGE,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

// ==================== 增强版瓷砖组件 ====================

/**
 * 增强版时钟瓷砖 (4×2) - 更接近原版 Windows Phone Metro
 *
 * 改进点：
 * - ✅ 12小时制时移除前导零（9:41 而不是 09:41）
 * - ✅ 支持 AM/PM 标识显示
 * - ✅ 左对齐布局（原版标准）
 * - ✅ 可选的冒号闪烁动画
 * - ✅ 响应式字号（根据瓷砖高度自动计算）
 *
 * @param hour 小时 (0-23)
 * @param minute 分钟 (0-59)
 * @param use24Hour 是否使用24小时制（默认 true）
 * @param showBlinkingColon 是否显示闪烁的冒号（默认 false）
 * @param date 日期文本
 * @param lunarDate 农历文本（可选）
 * @param alignment 时间对齐方式（默认左对齐 CenterStart）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 */
@Composable
fun EnhancedClockTile(
    hour: Int,
    minute: Int,
    use24Hour: Boolean = true,
    showBlinkingColon: Boolean = false,
    date: String,
    lunarDate: String = "",
    alignment: Alignment = Alignment.CenterStart,
    backgroundColor: Color = MetroTileColors.Time,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    // 格式化时间
    val (timeText, period) = remember(hour, minute, use24Hour) {
        if (use24Hour) {
            // 24小时制：保留前导零
            String.format("%02d:%02d", hour, minute) to null
        } else {
            // 12小时制：移除前导零
            val displayHour = when {
                hour == 0 -> 12
                hour > 12 -> hour - 12
                else -> hour
            }
            val periodText = if (hour < 12) "AM" else "PM"
            String.format("%d:%02d", displayHour, minute) to periodText
        }
    }

    // 冒号闪烁动画
    var showColon by remember { mutableStateOf(true) }
    LaunchedEffect(showBlinkingColon) {
        if (showBlinkingColon) {
            while (true) {
                delay(1000)
                showColon = !showColon
            }
        } else {
            showColon = true
        }
    }

    val displayTime = if (showBlinkingColon && !showColon) {
        timeText.replace(":", " ")
    } else {
        timeText
    }

    // 响应式字号：瓷砖高度的 65%
    // 使用新版计算逻辑
    val tileHeight = TileGrid.calculateTileHeight(baseCellSize, gridRows = 2, dynamicGap)
    val timeFontSize = (tileHeight.value * 0.65f).sp

    Tile(
        size = TileSize.WIDE_MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        FlipTileAnimation(
            frontContent = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = alignment
                ) {
                    // 时间显示
                    Text(
                        text = displayTime,
                        fontSize = timeFontSize,
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                        lineHeight = timeFontSize,
                        modifier = if (alignment == Alignment.CenterStart) {
                            Modifier.padding(start = 16.dp)
                        } else {
                            Modifier
                        }
                    )

                    // AM/PM 标识（仅12小时制显示）
                    if (period != null) {
                        Text(
                            text = period,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 8.dp)
                        )
                    }
                }
            },
            backContent = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = when (alignment) {
                        Alignment.CenterStart -> Alignment.Start
                        else -> Alignment.CenterHorizontally
                    }
                ) {
                    Text(
                        text = date,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        modifier = if (alignment == Alignment.CenterStart) {
                            Modifier.padding(start = 16.dp)
                        } else {
                            Modifier
                        }
                    )
                    if (lunarDate.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lunarDate,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = if (alignment == Alignment.CenterStart) {
                                Modifier.padding(start = 16.dp)
                            } else {
                                Modifier
                            }
                        )
                    }
                }
            }
        )
    }
}
