package top.yaotutu.deskmate.presentation.ui.component.animation.core

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.ui.theme.MetroDuration
import top.yaotutu.deskmate.presentation.ui.theme.MetroEasing
import kotlin.math.roundToInt

/**
 * 探出方向枚举
 */
enum class PeekDirection {
    /** 从顶部探出 */
    TOP,
    /** 从底部探出 */
    BOTTOM
}

/**
 * Windows Phone 风格的探出动画（Peek Animation）
 *
 * 这是 Windows Phone Live Tile 的标志性动画之一。
 * 内容从底部或顶部探出一部分预览，停留片刻后收回。
 *
 * 动画流程：
 * 1. 显示主要内容（mainContent）
 * 2. 探出内容（peekContent）从指定方向探出
 * 3. 停留 2 秒显示预览
 * 4. 探出内容收回
 * 5. 循环
 *
 * 使用场景：
 * - 邮件瓷砖预览新邮件
 * - 日历瓷砖预览下一个事件
 * - 通知瓷砖预览消息内容
 * - 新闻瓷砖预览更多标题
 *
 * 使用示例：
 * ```
 * PeekTileAnimation(
 *     mainContent = {
 *         // 主要内容：显示邮件数量
 *         Column {
 *             Text("📧", fontSize = 64.sp)
 *             Text("3 封新邮件", fontSize = 20.sp, color = Color.White)
 *         }
 *     },
 *     peekContent = {
 *         // 探出内容：显示最新邮件预览
 *         Column {
 *             Text("来自：张三", fontSize = 16.sp, color = Color.White)
 *             Text("会议提醒", fontSize = 14.sp, color = Color.White.copy(0.8f))
 *         }
 *     },
 *     peekHeight = 0.4f,  // 探出 40% 高度
 *     direction = PeekDirection.BOTTOM
 * )
 * ```
 *
 * @param mainContent 主要内容（一直显示）
 * @param peekContent 探出内容（从指定方向探出）
 * @param peekHeight 探出高度比例（0.0-1.0，默认 0.3 即探出 30%）
 * @param direction 探出方向（默认从底部探出）
 * @param peekDuration 探出动画持续时间（毫秒，默认 400ms）
 * @param holdDuration 探出后停留时间（毫秒，默认 2000ms）
 * @param peekInterval 探出动画循环间隔（毫秒，默认 8000ms）
 * @param modifier 修饰符
 */
@Composable
fun PeekTileAnimation(
    mainContent: @Composable () -> Unit,
    peekContent: @Composable () -> Unit,
    peekHeight: Float = 0.3f,
    direction: PeekDirection = PeekDirection.BOTTOM,
    peekDuration: Int = MetroDuration.MEDIUM,
    holdDuration: Long = 2000L,
    peekInterval: Long = 8000L,
    modifier: Modifier = Modifier
) {
    // 瓷砖高度（用于计算偏移）
    var tileHeight by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    // 动画状态：0 = 收回, 1 = 探出
    var isPeeking by remember { mutableStateOf(false) }

    // 自动探出循环
    LaunchedEffect(Unit) {
        delay(peekInterval) // 首次延迟
        while (true) {
            isPeeking = true  // 开始探出
            delay(peekDuration.toLong() + holdDuration)  // 探出动画 + 停留时间
            isPeeking = false  // 收回
            delay(peekInterval)  // 等待下一次探出
        }
    }

    // 探出进度动画（0.0 = 完全收回, 1.0 = 完全探出）
    val peekProgress by animateFloatAsState(
        targetValue = if (isPeeking) 1f else 0f,
        animationSpec = tween(
            durationMillis = peekDuration,
            easing = MetroEasing.Standard
        ),
        label = "peek_progress"
    )

    // 计算探出距离（像素）
    val peekDistance = remember(tileHeight, peekHeight) {
        (tileHeight * peekHeight).toInt()
    }

    // 计算当前偏移量
    val currentOffset = remember(peekProgress, peekDistance) {
        (peekDistance * peekProgress).roundToInt()
    }

    Box(
        modifier = modifier
            .clipToBounds()  // 裁剪溢出内容
            .onSizeChanged { size ->
                tileHeight = size.height
            }
    ) {
        // 主要内容（固定显示）
        Box {
            mainContent()
        }

        // 探出内容（从指定方向探出）
        Box(
            modifier = Modifier.offset {
                when (direction) {
                    PeekDirection.BOTTOM -> {
                        // 从底部探出：初始在下方，向上移动
                        IntOffset(0, tileHeight - currentOffset)
                    }
                    PeekDirection.TOP -> {
                        // 从顶部探出：初始在上方，向下移动
                        IntOffset(0, currentOffset - peekDistance)
                    }
                }
            }
        ) {
            peekContent()
        }
    }
}
