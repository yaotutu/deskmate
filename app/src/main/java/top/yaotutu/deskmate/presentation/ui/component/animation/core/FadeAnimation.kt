package top.yaotutu.deskmate.presentation.ui.component.animation.core

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.ui.theme.MetroDuration
import top.yaotutu.deskmate.presentation.ui.theme.MetroEasing

/**
 * 淡入淡出动画
 *
 * @param contents 要切换的内容列表
 * @param fadeDurationMillis 淡入淡出持续时间
 * @param fadeIntervalMillis 自动切换间隔时间
 * @param modifier 修饰符
 */
@Composable
fun FadeTileAnimation(
    contents: List<@Composable () -> Unit>,
    fadeDurationMillis: Int = MetroDuration.SLOW,
    fadeIntervalMillis: Long = MetroDuration.FLIP_CYCLE.toLong(),
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableStateOf(0) }

    // 自动切换
    LaunchedEffect(Unit) {
        while (true) {
            delay(fadeIntervalMillis)
            currentIndex = (currentIndex + 1) % contents.size
        }
    }

    // 使用 Metro 标准缓动曲线
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = fadeDurationMillis,
            easing = MetroEasing.Standard
        ),
        label = "fade_alpha"
    )

    Box(
        modifier = modifier.graphicsLayer {
            this.alpha = alpha
        }
    ) {
        contents[currentIndex]()
    }
}
