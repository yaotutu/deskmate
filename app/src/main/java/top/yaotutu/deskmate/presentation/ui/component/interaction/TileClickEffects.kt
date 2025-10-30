package top.yaotutu.deskmate.presentation.ui.component.interaction

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay

/**
 * Windows Phone Metro 风格的瓷砖点击交互动效 - 单一效果组件库
 *
 * 提供6种独立的点击反馈效果，可单独使用或组合使用
 */

/**
 * 1. 经典 Metro 按压效果 ⭐ 推荐
 *
 * 效果：按下时缩小至 95% 并降低透明度，释放后恢复
 * 适用：所有瓷砖的基础交互
 *
 * @param onTap 点击回调
 * @param scaleDown 按下时的缩放比例（默认 0.95）
 * @param alphaDown 按下时的透明度（默认 0.7）
 * @param durationMillis 动画时长（默认 150ms）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun TilePressEffect(
    onTap: () -> Unit = {},
    scaleDown: Float = 0.95f,
    alphaDown: Float = 0.7f,
    durationMillis: Int = 150,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDown else 1f,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing),
        label = "press_scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isPressed) alphaDown else 1f,
        animationSpec = tween(durationMillis),
        label = "press_alpha"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onTap() }
                )
            }
    ) {
        content()
    }
}

/**
 * 2. 按压闪烁效果
 *
 * 效果：按下时透明度快速降低，释放后快速恢复
 * 适用：强调性瓷砖，如重要通知
 *
 * @param onTap 点击回调
 * @param flashAlpha 闪烁时的透明度（默认 0.5）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun TilePressFlash(
    onTap: () -> Unit = {},
    flashAlpha: Float = 0.5f,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isPressed) flashAlpha else 1f,
        animationSpec = tween(100, easing = LinearEasing),
        label = "flash_alpha"
    )

    Box(
        modifier = modifier
            .graphicsLayer { this.alpha = alpha }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onTap() }
                )
            }
    ) {
        content()
    }
}

/**
 * 3. 点击弹跳效果
 *
 * 效果：点击后瓷砖先放大至 110%，然后弹回原始大小
 * 适用：游戏类、娱乐类瓷砖
 *
 * @param onTap 点击回调
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun TileTapBounce(
    onTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var shouldBounce by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (shouldBounce) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bounce_scale"
    )

    LaunchedEffect(shouldBounce) {
        if (shouldBounce) {
            delay(200)
            shouldBounce = false
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        shouldBounce = true
                        onTap()
                    }
                )
            }
    ) {
        content()
    }
}

/**
 * 4. 点击脉冲效果
 *
 * 效果：点击后瓷砖快速脉冲一次（缩小-放大-恢复）
 * 适用：刷新类、更新类瓷砖
 *
 * @param onTap 点击回调
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun TileTapPulse(
    onTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var shouldPulse by remember { mutableStateOf(false) }
    var pulsePhase by remember { mutableStateOf(0) } // 0: 正常, 1: 缩小, 2: 放大, 3: 恢复

    val scale by animateFloatAsState(
        targetValue = when (pulsePhase) {
            1 -> 0.9f
            2 -> 1.05f
            else -> 1f
        },
        animationSpec = tween(150, easing = FastOutSlowInEasing),
        label = "pulse_scale"
    )

    LaunchedEffect(shouldPulse) {
        if (shouldPulse) {
            pulsePhase = 1
            delay(150)
            pulsePhase = 2
            delay(150)
            pulsePhase = 0
            shouldPulse = false
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!shouldPulse) {
                            shouldPulse = true
                            onTap()
                        }
                    }
                )
            }
    ) {
        content()
    }
}

/**
 * 5. 点击触发翻转效果
 *
 * 效果：点击后触发瓷砖翻转，显示背面内容，再次点击翻回
 * 适用：有双面内容的瓷砖
 *
 * @param frontContent 正面内容
 * @param backContent 背面内容
 * @param modifier 修饰符
 */
@Composable
fun TileTapFlipTrigger(
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFlipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "flip_rotation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { isFlipped = !isFlipped }
                )
            }
    ) {
        if (rotation <= 90f) {
            frontContent()
        } else {
            Box(modifier = Modifier.graphicsLayer { rotationY = 180f }) {
                backContent()
            }
        }
    }
}

/**
 * 6. 点击抖动效果
 *
 * 效果：点击后瓷砖左右快速抖动，类似"摇一摇"
 * 适用：互动类、游戏类瓷砖
 *
 * @param onTap 点击回调
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun TileTapShake(
    onTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var shouldShake by remember { mutableStateOf(false) }
    var shakeOffset by remember { mutableStateOf(0f) }

    LaunchedEffect(shouldShake) {
        if (shouldShake) {
            // 抖动序列：右-左-右-左-中
            val sequence = listOf(10f, -10f, 8f, -8f, 5f, -5f, 0f)
            sequence.forEach { offset ->
                shakeOffset = offset
                delay(50)
            }
            shouldShake = false
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                translationX = shakeOffset
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!shouldShake) {
                            shouldShake = true
                            onTap()
                        }
                    }
                )
            }
    ) {
        content()
    }
}
