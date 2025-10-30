package top.yaotutu.deskmate.presentation.ui.component.animation.core

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import top.yaotutu.deskmate.presentation.ui.theme.MetroDuration
import top.yaotutu.deskmate.presentation.ui.theme.MetroEasing

/**
 * 脉冲/缩放动画
 *
 * @param enabled 是否启用动画
 * @param pulseDurationMillis 脉冲动画持续时间
 * @param scaleRange 缩放范围（1.0 为原始大小）
 * @param content 内容
 */
@Composable
fun PulseTileAnimation(
    enabled: Boolean = true,
    pulseDurationMillis: Int = MetroDuration.PULSE_CYCLE / 2,
    scaleRange: Pair<Float, Float> = Pair(1.0f, 1.02f),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")

    // 使用 Metro EaseInOut 缓动曲线，提供平滑的呼吸效果
    val scale by infiniteTransition.animateFloat(
        initialValue = scaleRange.first,
        targetValue = scaleRange.second,
        animationSpec = infiniteRepeatable(
            animation = tween(pulseDurationMillis, easing = MetroEasing.EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        modifier = modifier.graphicsLayer {
            scaleX = if (enabled) scale else 1f
            scaleY = if (enabled) scale else 1f
        }
    ) {
        content()
    }
}
