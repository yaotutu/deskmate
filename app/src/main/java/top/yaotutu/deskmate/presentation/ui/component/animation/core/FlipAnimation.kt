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
 * Windows Phone 风格的瓷砖翻转动画
 *
 * @param frontContent 正面内容
 * @param backContent 背面内容
 * @param flipDurationMillis 翻转动画持续时间
 * @param flipIntervalMillis 自动翻转间隔时间
 * @param modifier 修饰符
 */
@Composable
fun FlipTileAnimation(
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    flipDurationMillis: Int = MetroDuration.SLOW,
    flipIntervalMillis: Long = MetroDuration.FLIP_CYCLE.toLong(),
    modifier: Modifier = Modifier
) {
    var isFlipped by remember { mutableStateOf(false) }

    // 自动翻转
    LaunchedEffect(Unit) {
        while (true) {
            delay(flipIntervalMillis)
            isFlipped = !isFlipped
        }
    }

    // 使用 Metro 标准缓动曲线，提供更流畅的翻转效果
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = flipDurationMillis,
            easing = MetroEasing.Standard
        ),
        label = "flip_rotation"
    )

    Box(modifier = modifier) {
        // 正面
        Box(
            modifier = Modifier.graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
        ) {
            if (rotation <= 90f) {
                frontContent()
            }
        }

        // 背面
        Box(
            modifier = Modifier.graphicsLayer {
                rotationY = rotation - 180f
                cameraDistance = 12f * density
            }
        ) {
            if (rotation > 90f) {
                backContent()
            }
        }
    }
}
