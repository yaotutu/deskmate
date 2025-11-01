package top.yaotutu.deskmate.presentation.component.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.theme.MetroDuration
import top.yaotutu.deskmate.presentation.theme.MetroEasing

/**
 * 擦除方向枚举
 */
enum class WipeDirection {
    /** 从左到右擦除 */
    LEFT_TO_RIGHT,
    /** 从右到左擦除 */
    RIGHT_TO_LEFT,
    /** 从上到下擦除 */
    TOP_TO_BOTTOM,
    /** 从下到上擦除 */
    BOTTOM_TO_TOP
}

/**
 * 擦除样式枚举
 */
enum class WipeStyle {
    /** 滑动擦除（新内容平滑推入） */
    SLIDE,
    /** 揭示擦除（新内容逐渐揭示） */
    REVEAL
}

/**
 * Windows Phone 风格的擦除/揭示动画（Wipe Animation）
 *
 * 新内容从指定方向"擦除"旧内容，类似百叶窗或推拉效果。
 * 比简单的 Slide 动画更具视觉冲击力。
 *
 * 动画原理：
 * - SLIDE 样式：新内容推入，旧内容同步移出
 * - REVEAL 样式：新内容逐渐揭示，旧内容保持不动
 *
 * 使用场景：
 * - 新闻内容切换
 * - 图片轮播
 * - 重要信息更新
 * - 内容刷新提示
 *
 * 使用示例：
 * ```
 * // 新闻内容从左到右擦除切换
 * WipeTileAnimation(
 *     contents = listOf(
 *         { NewsItem("新闻标题1") },
 *         { NewsItem("新闻标题2") },
 *         { NewsItem("新闻标题3") }
 *     ),
 *     direction = WipeDirection.LEFT_TO_RIGHT,
 *     style = WipeStyle.SLIDE,
 *     wipeDuration = 500,
 *     wipeInterval = 4000L
 * )
 * ```
 *
 * @param contents 内容列表（至少2项）
 * @param direction 擦除方向（默认从左到右）
 * @param style 擦除样式（默认滑动）
 * @param wipeDuration 擦除动画持续时间（毫秒，默认 400ms）
 * @param wipeInterval 内容切换间隔（毫秒，默认 4000ms）
 * @param modifier 修饰符
 */
@Composable
fun WipeTileAnimation(
    contents: List<@Composable () -> Unit>,
    direction: WipeDirection = WipeDirection.LEFT_TO_RIGHT,
    style: WipeStyle = WipeStyle.SLIDE,
    wipeDuration: Int = MetroDuration.MEDIUM,
    wipeInterval: Long = MetroDuration.SLIDE_CYCLE.toLong(),
    modifier: Modifier = Modifier
) {
    // 确保至少有2项内容
    if (contents.size < 2) {
        Box(modifier = modifier) {
            contents.firstOrNull()?.invoke()
        }
        return
    }

    // 当前内容索引
    var currentIndex by remember { mutableStateOf(0) }
    // 前一个内容索引
    var previousIndex by remember { mutableStateOf(0) }
    // 是否正在擦除
    var isWiping by remember { mutableStateOf(false) }

    // 自动切换循环
    LaunchedEffect(Unit) {
        delay(wipeInterval)  // 首次延迟
        while (true) {
            previousIndex = currentIndex
            currentIndex = (currentIndex + 1) % contents.size
            isWiping = true
            delay(wipeDuration.toLong())
            isWiping = false
            delay(wipeInterval)
        }
    }

    // 擦除进度动画（0.0 = 显示旧内容, 1.0 = 显示新内容）
    val wipeProgress by animateFloatAsState(
        targetValue = if (isWiping) 1f else 0f,
        animationSpec = tween(
            durationMillis = wipeDuration,
            easing = MetroEasing.Standard
        ),
        label = "wipe_progress"
    )

    // 内容尺寸
    var containerWidth by remember { mutableStateOf(0f) }
    var containerHeight by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .clipToBounds()
            .onSizeChanged { size ->
                containerWidth = size.width.toFloat()
                containerHeight = size.height.toFloat()
            }
    ) {
        // 旧内容（正在被擦除）
        Box(
            modifier = Modifier.drawWithContent {
                when (style) {
                    WipeStyle.SLIDE -> {
                        // 滑动样式：旧内容同步移出
                        drawWithWipeClip(
                            direction = direction,
                            progress = wipeProgress,
                            isOldContent = true,
                            containerWidth = containerWidth,
                            containerHeight = containerHeight
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }
                    WipeStyle.REVEAL -> {
                        // 揭示样式：旧内容逐渐被遮盖
                        drawWithWipeClip(
                            direction = direction,
                            progress = wipeProgress,
                            isOldContent = true,
                            containerWidth = containerWidth,
                            containerHeight = containerHeight
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }
                }
            }
        ) {
            contents[previousIndex]()
        }

        // 新内容（正在进入）
        if (isWiping) {
            Box(
                modifier = Modifier.drawWithContent {
                    // 新内容逐渐揭示
                    drawWithWipeClip(
                        direction = direction,
                        progress = wipeProgress,
                        isOldContent = false,
                        containerWidth = containerWidth,
                        containerHeight = containerHeight
                    ) {
                        this@drawWithContent.drawContent()
                    }
                }
            ) {
                contents[currentIndex]()
            }
        }
    }
}

/**
 * 绘制带擦除裁剪的内容
 */
private fun DrawScope.drawWithWipeClip(
    direction: WipeDirection,
    progress: Float,
    isOldContent: Boolean,
    containerWidth: Float,
    containerHeight: Float,
    draw: DrawScope.() -> Unit
) {
    when (direction) {
        WipeDirection.LEFT_TO_RIGHT -> {
            if (isOldContent) {
                // 旧内容：从左侧被擦除，保留右侧部分
                val remainWidth = containerWidth * (1f - progress)
                clipRect(
                    left = containerWidth - remainWidth,
                    top = 0f,
                    right = containerWidth,
                    bottom = containerHeight
                ) {
                    draw()
                }
            } else {
                // 新内容：从左侧进入
                val visibleWidth = containerWidth * progress
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = visibleWidth,
                    bottom = containerHeight
                ) {
                    draw()
                }
            }
        }
        WipeDirection.RIGHT_TO_LEFT -> {
            if (isOldContent) {
                // 旧内容：从右侧被擦除，保留左侧部分
                val visibleWidth = containerWidth * (1f - progress)
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = visibleWidth,
                    bottom = containerHeight
                ) {
                    draw()
                }
            } else {
                // 新内容：从右侧进入
                val visibleWidth = containerWidth * progress
                clipRect(
                    left = containerWidth - visibleWidth,
                    top = 0f,
                    right = containerWidth,
                    bottom = containerHeight
                ) {
                    draw()
                }
            }
        }
        WipeDirection.TOP_TO_BOTTOM -> {
            if (isOldContent) {
                // 旧内容：从顶部被擦除，保留底部部分
                val remainHeight = containerHeight * (1f - progress)
                clipRect(
                    left = 0f,
                    top = containerHeight - remainHeight,
                    right = containerWidth,
                    bottom = containerHeight
                ) {
                    draw()
                }
            } else {
                // 新内容：从顶部进入
                val visibleHeight = containerHeight * progress
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = containerWidth,
                    bottom = visibleHeight
                ) {
                    draw()
                }
            }
        }
        WipeDirection.BOTTOM_TO_TOP -> {
            if (isOldContent) {
                // 旧内容：从底部被擦除，保留顶部部分
                val visibleHeight = containerHeight * (1f - progress)
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = containerWidth,
                    bottom = visibleHeight
                ) {
                    draw()
                }
            } else {
                // 新内容：从底部进入
                val visibleHeight = containerHeight * progress
                clipRect(
                    left = 0f,
                    top = containerHeight - visibleHeight,
                    right = containerWidth,
                    bottom = containerHeight
                ) {
                    draw()
                }
            }
        }
    }
}
