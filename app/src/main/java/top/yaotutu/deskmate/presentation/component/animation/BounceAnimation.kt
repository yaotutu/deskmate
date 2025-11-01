package top.yaotutu.deskmate.presentation.component.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import top.yaotutu.deskmate.presentation.theme.MetroEasing

/**
 * 弹跳动画 - Metro 风格（持续循环版本）
 *
 * 用于强调新内容到达、重要数据更新提醒
 * 持续上下弹跳，效果更加明显
 *
 * @param enabled 是否启用动画
 * @param bounceDurationMillis 单次弹跳周期时间
 * @param bounceHeight 弹跳高度（dp）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun BounceTileAnimation(
    enabled: Boolean = true,
    bounceDurationMillis: Int = 2000,  // 放慢到2秒一个周期
    bounceHeight: Float = 40f,  // 适中高度40dp
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bounce_transition")

    // 持续上下弹跳动画
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -bounceHeight,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = bounceDurationMillis / 2,
                easing = MetroEasing.EaseOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce_offsetY"
    )

    Box(
        modifier = modifier.graphicsLayer {
            translationY = if (enabled) offsetY else 0f
        }
    ) {
        content()
    }
}
