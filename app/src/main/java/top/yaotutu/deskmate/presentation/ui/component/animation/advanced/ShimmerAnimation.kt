package top.yaotutu.deskmate.presentation.ui.component.animation.advanced

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import top.yaotutu.deskmate.presentation.ui.theme.MetroDuration

/**
 * 微光动画 - Metro 风格
 *
 * 用于加载状态指示、表示内容正在更新
 *
 * @param enabled 是否启用动画
 * @param shimmerDurationMillis 微光扫过持续时间
 * @param shimmerIntervalMillis 扫过间隔时间
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun ShimmerTileAnimation(
    enabled: Boolean = true,
    shimmerDurationMillis: Int = MetroDuration.SHIMMER_CYCLE,
    shimmerIntervalMillis: Long = MetroDuration.SHIMMER_INTERVAL.toLong(),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_transition")

    // 微光位置动画（从左到右）
    val shimmerPosition by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(shimmerDurationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
            // 去掉初始延迟，立即开始播放
        ),
        label = "shimmer_position"
    )

    // 微光透明度（在边缘淡出）
    val shimmerAlpha = remember(shimmerPosition) {
        val distance = kotlin.math.abs(shimmerPosition)
        (1f - distance).coerceIn(0f, 0.3f)
    }

    Box(modifier = modifier) {
        content()

        // 微光叠加层
        if (enabled) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        translationX = size.width * shimmerPosition
                        alpha = shimmerAlpha
                    }
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0f),
                                Color.White.copy(alpha = 0.4f),
                                Color.White.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }
    }
}
