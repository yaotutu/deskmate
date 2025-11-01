package top.yaotutu.deskmate.presentation.component.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.theme.MetroDuration
import top.yaotutu.deskmate.presentation.theme.MetroEasing

/**
 * 旋转动画 - Metro 风格
 *
 * 用于数据刷新指示、加载状态等场景
 *
 * @param enabled 是否启用动画
 * @param continuous 是否持续旋转（true=持续，false=单次旋转）
 * @param rotateDurationMillis 旋转一周的持续时间
 * @param clockwise 是否顺时针旋转
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun RotateTileAnimation(
    enabled: Boolean = true,
    continuous: Boolean = true,
    rotateDurationMillis: Int = MetroDuration.ROTATE_CYCLE,
    clockwise: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotate_transition")

    // 持续旋转动画
    val rotationContinuous by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (clockwise) 360f else -360f,
        animationSpec = infiniteRepeatable(
            animation = tween(rotateDurationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotate_continuous"
    )

    // 单次旋转动画
    var trigger by remember { mutableStateOf(0) }
    val rotationSingle by animateFloatAsState(
        targetValue = if (clockwise) trigger * 360f else -trigger * 360f,
        animationSpec = tween(
            durationMillis = rotateDurationMillis,
            easing = MetroEasing.Standard
        ),
        label = "rotate_single"
    )

    // 触发单次旋转
    LaunchedEffect(enabled, continuous) {
        if (enabled && !continuous) {
            while (true) {
                delay(MetroDuration.ROTATE_INTERVAL.toLong())
                trigger++
            }
        }
    }

    Box(
        modifier = modifier.graphicsLayer {
            rotationZ = if (enabled) {
                if (continuous) rotationContinuous else rotationSingle
            } else 0f
        }
    ) {
        content()
    }
}
