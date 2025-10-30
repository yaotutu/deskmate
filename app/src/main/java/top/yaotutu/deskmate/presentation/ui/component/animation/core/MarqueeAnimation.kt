package top.yaotutu.deskmate.presentation.ui.component.animation.core

import androidx.compose.animation.core.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

/**
 * 滚动方向枚举
 */
enum class MarqueeDirection {
    /** 水平滚动（从右到左） */
    HORIZONTAL,
    /** 垂直滚动（从下到上） */
    VERTICAL
}

/**
 * Windows Phone 风格的跑马灯动画（Marquee Animation）
 *
 * 连续循环滚动内容，常用于长文本、新闻标题、通知等场景。
 * 内容会无缝循环滚动，不会出现断点或停顿。
 *
 * 动画特点：
 * - 匀速滚动（使用 LinearEasing）
 * - 无缝循环（内容首尾相连）
 * - 自动测量内容尺寸
 * - 支持水平和垂直两个方向
 *
 * 使用场景：
 * - 新闻瓷砖的标题滚动
 * - 通知栏的消息滚动
 * - 长文本内容展示
 * - 股票代码/价格滚动
 *
 * 使用示例：
 * ```
 * // 水平滚动新闻标题
 * MarqueeTileAnimation(
 *     direction = MarqueeDirection.HORIZONTAL,
 *     speed = 40f  // 40 dp/秒
 * ) {
 *     Text(
 *         text = "突发新闻：这是一条很长的新闻标题，需要滚动显示...",
 *         fontSize = 20.sp,
 *         fontWeight = FontWeight.Light,
 *         color = Color.White
 *     )
 * }
 *
 * // 垂直滚动通知列表
 * MarqueeTileAnimation(
 *     direction = MarqueeDirection.VERTICAL,
 *     speed = 30f
 * ) {
 *     Column {
 *         Text("📧 新邮件：工作报告")
 *         Text("💬 新消息：团队会议")
 *         Text("📅 日程提醒：下午2点")
 *     }
 * }
 * ```
 *
 * @param direction 滚动方向（默认水平滚动）
 * @param speed 滚动速度（dp/秒，默认 30）
 * @param spacing 循环间距（dp，默认 50，用于内容首尾相连时的间隔）
 * @param modifier 修饰符
 * @param content 要滚动的内容
 */
@Composable
fun MarqueeTileAnimation(
    direction: MarqueeDirection = MarqueeDirection.HORIZONTAL,
    speed: Float = 30f,
    spacing: Int = 50,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // 内容尺寸
    var contentWidth by remember { mutableStateOf(0) }
    var contentHeight by remember { mutableStateOf(0) }

    // 容器尺寸
    var containerWidth by remember { mutableStateOf(0) }
    var containerHeight by remember { mutableStateOf(0) }

    // 计算滚动距离和时长
    val scrollDistance = when (direction) {
        MarqueeDirection.HORIZONTAL -> contentWidth + spacing
        MarqueeDirection.VERTICAL -> contentHeight + spacing
    }

    // 计算动画时长（毫秒）：距离 / 速度 * 1000
    val animationDuration = remember(scrollDistance, speed) {
        if (speed > 0) {
            ((scrollDistance / speed) * 1000).toInt().coerceAtLeast(1000)
        } else {
            5000  // 默认 5 秒
        }
    }

    // 无限滚动动画
    val infiniteTransition = rememberInfiniteTransition(label = "marquee_transition")

    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "marquee_progress"
    )

    // 计算当前偏移量
    val currentOffset = remember(animationProgress, scrollDistance) {
        (-scrollDistance * animationProgress).roundToInt()
    }

    Box(
        modifier = modifier
            .clipToBounds()  // 裁剪溢出内容
            .onSizeChanged { size ->
                containerWidth = size.width
                containerHeight = size.height
            }
    ) {
        when (direction) {
            MarqueeDirection.HORIZONTAL -> {
                // 水平滚动：使用 Row 并排放置两份内容
                Row(
                    modifier = Modifier.offset {
                        IntOffset(currentOffset, 0)
                    }
                ) {
                    // 第一份内容
                    Box(
                        modifier = Modifier.onSizeChanged { size ->
                            contentWidth = size.width
                        }
                    ) {
                        content()
                    }

                    // 间距
                    Box(modifier = Modifier.offset { IntOffset(spacing, 0) })

                    // 第二份内容（用于无缝循环）
                    Box {
                        content()
                    }
                }
            }
            MarqueeDirection.VERTICAL -> {
                // 垂直滚动：使用 Column 上下放置两份内容
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier.offset {
                        IntOffset(0, currentOffset)
                    }
                ) {
                    // 第一份内容
                    Box(
                        modifier = Modifier.onSizeChanged { size ->
                            contentHeight = size.height
                        }
                    ) {
                        content()
                    }

                    // 间距
                    Box(modifier = Modifier.offset { IntOffset(0, spacing) })

                    // 第二份内容（用于无缝循环）
                    Box {
                        content()
                    }
                }
            }
        }
    }
}
