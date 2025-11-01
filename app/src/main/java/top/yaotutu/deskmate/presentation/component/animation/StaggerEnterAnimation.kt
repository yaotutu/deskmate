package top.yaotutu.deskmate.presentation.component.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.theme.MetroDuration
import top.yaotutu.deskmate.presentation.theme.MetroEasing

/**
 * 错峰进入动画（Stagger Animation）- Metro 风格
 *
 * 瓷砖依次出现，每个瓷砖延迟固定时间，
 * 创造出流畅的连续动画效果。
 *
 * @param index 瓷砖索引（用于计算延迟）
 * @param delayMillis 每个瓷砖之间的延迟时间
 * @param durationMillis 单个瓷砖的动画持续时间
 * @param initialAlpha 初始透明度
 * @param initialOffsetY 初始 Y 轴偏移（向下为正）
 * @param initialScale 初始缩放比例
 * @param enabled 是否启用动画（用于测试或性能优化）
 * @param content 瓷砖内容
 */
@Composable
fun StaggerEnterAnimation(
    index: Int,
    delayMillis: Int = MetroDuration.STAGGER_DELAY,
    durationMillis: Int = MetroDuration.STANDARD,
    initialAlpha: Float = 0f,
    initialOffsetY: Float = 20f,
    initialScale: Float = 0.9f,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // 计算当前瓷砖的延迟时间
    val totalDelay = index * delayMillis

    // 动画状态
    var isVisible by remember { mutableStateOf(false) }

    // 启动动画
    LaunchedEffect(enabled) {
        if (enabled) {
            delay(totalDelay.toLong())
            isVisible = true
        } else {
            isVisible = true
        }
    }

    // 透明度动画
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else initialAlpha,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = MetroEasing.Standard
        ),
        label = "stagger_alpha_$index"
    )

    // Y 轴偏移动画
    val offsetY by animateFloatAsState(
        targetValue = if (isVisible) 0f else initialOffsetY,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = MetroEasing.Standard
        ),
        label = "stagger_offsetY_$index"
    )

    // 缩放动画
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else initialScale,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = MetroEasing.Standard
        ),
        label = "stagger_scale_$index"
    )

    Box(
        modifier = modifier.graphicsLayer {
            this.alpha = alpha
            this.translationY = offsetY
            this.scaleX = scale
            this.scaleY = scale
        }
    ) {
        content()
    }
}
