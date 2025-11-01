package top.yaotutu.deskmate.presentation.component.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.theme.MetroDuration
import top.yaotutu.deskmate.presentation.theme.MetroEasing

/**
 * 滑动切换动画（向上滑动）
 *
 * @param contents 要轮播的内容列表
 * @param slideDurationMillis 滑动动画持续时间
 * @param slideIntervalMillis 自动切换间隔时间
 * @param modifier 修饰符
 */
@Composable
fun SlideTileAnimation(
    contents: List<@Composable () -> Unit>,
    slideDurationMillis: Int = MetroDuration.MEDIUM,
    slideIntervalMillis: Long = MetroDuration.SLIDE_CYCLE.toLong(),
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableStateOf(0) }

    // 自动切换
    LaunchedEffect(Unit) {
        while (true) {
            delay(slideIntervalMillis)
            currentIndex = (currentIndex + 1) % contents.size
        }
    }

    // 使用 Metro 标准缓动曲线
    val offsetY by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(
            durationMillis = slideDurationMillis,
            easing = MetroEasing.Standard
        ),
        label = "slide_offset"
    )

    Box(modifier = modifier) {
        contents[currentIndex]()
    }
}
