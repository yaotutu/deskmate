package top.yaotutu.deskmate.presentation.ui.component.animation.interaction

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * 抖动动画 - Metro 风格（持续循环版本）
 *
 * 用于重要提醒、紧急通知、错误提示
 * 持续左右抖动，效果更加明显
 *
 * @param enabled 是否启用动画
 * @param shakeDurationMillis 单次抖动周期时间
 * @param shakeDistance 抖动距离（dp）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun ShakeTileAnimation(
    enabled: Boolean = true,
    shakeDurationMillis: Int = 1500,  // 放慢到1.5秒一个周期
    shakeDistance: Float = 30f,  // 适中距离30dp
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shake_transition")

    // 持续左右抖动动画
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -shakeDistance,
        targetValue = shakeDistance,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = shakeDurationMillis / 2,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake_offsetX"
    )

    Box(
        modifier = modifier.graphicsLayer {
            translationX = if (enabled) offsetX else 0f
        }
    ) {
        content()
    }
}
