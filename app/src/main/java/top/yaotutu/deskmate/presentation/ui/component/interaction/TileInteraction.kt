package top.yaotutu.deskmate.presentation.ui.component.interaction

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.ui.theme.MetroDuration

/**
 * Windows Phone Metro 风格的瓷砖点击交互动效
 *
 * 提供多种点击反馈效果，无需跳转页面，纯视觉反馈
 */

/**
 * 点击效果类型
 */
enum class TileClickEffect {
    PRESS_SCALE,        // 按压缩放（经典 Metro 效果）
    PRESS_FLASH,        // 按压闪烁
    TAP_BOUNCE,         // 点击弹跳
    TAP_PULSE,          // 点击脉冲
    TAP_FLIP_TRIGGER,   // 点击触发翻转
    TAP_SHAKE,          // 点击抖动
    NONE                // 无效果
}

/**
 * 1. 经典 Metro 按压效果 ⭐ 推荐
 *
 * 效果：按下时缩小至 95% 并降低透明度，释放后恢复
 * 适用：所有瓷砖的基础交互
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

/**
 * 7. 组合效果：按压 + 点击动效
 *
 * 效果：按压时缩小，释放后触发特定动效
 * 适用：需要同时有按压反馈和点击特效的场景
 */
@Composable
fun TileCombinedEffect(
    onTap: () -> Unit = {},
    clickEffect: TileClickEffect = TileClickEffect.TAP_PULSE,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var shouldTriggerEffect by remember { mutableStateOf(false) }

    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150, easing = FastOutSlowInEasing),
        label = "combined_press_scale"
    )

    val effectScale by animateFloatAsState(
        targetValue = when {
            !shouldTriggerEffect -> 1f
            clickEffect == TileClickEffect.TAP_BOUNCE -> 1.1f
            clickEffect == TileClickEffect.TAP_PULSE -> 1.05f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "combined_effect_scale"
    )

    LaunchedEffect(shouldTriggerEffect) {
        if (shouldTriggerEffect) {
            delay(300)
            shouldTriggerEffect = false
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = pressScale * effectScale
                scaleY = pressScale * effectScale
                alpha = if (isPressed) 0.7f else 1f
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        shouldTriggerEffect = true
                        onTap()
                    }
                )
            }
    ) {
        content()
    }
}

/**
 * 8. 通用瓷砖交互包装器
 *
 * 根据指定的效果类型自动选择合适的动效
 */
@Composable
fun TileWithInteraction(
    effect: TileClickEffect = TileClickEffect.PRESS_SCALE,
    onTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    when (effect) {
        TileClickEffect.PRESS_SCALE -> {
            TilePressEffect(onTap = onTap, modifier = modifier) {
                content()
            }
        }
        TileClickEffect.PRESS_FLASH -> {
            TilePressFlash(onTap = onTap, modifier = modifier) {
                content()
            }
        }
        TileClickEffect.TAP_BOUNCE -> {
            TileTapBounce(onTap = onTap, modifier = modifier) {
                content()
            }
        }
        TileClickEffect.TAP_PULSE -> {
            TileTapPulse(onTap = onTap, modifier = modifier) {
                content()
            }
        }
        TileClickEffect.TAP_SHAKE -> {
            TileTapShake(onTap = onTap, modifier = modifier) {
                content()
            }
        }
        TileClickEffect.NONE -> {
            Box(modifier = modifier) {
                content()
            }
        }
        else -> {
            TilePressEffect(onTap = onTap, modifier = modifier) {
                content()
            }
        }
    }
}

/**
 * 9. 长按检测 - Metro 编辑模式触发
 *
 * 效果：长按 500ms 触发编辑模式，带抖动反馈和震动反馈
 * 适用：瓷砖长按进入编辑模式
 *
 * @param onLongPress 长按回调
 * @param onTap 短按回调
 * @param longPressDurationMillis 长按时长（默认 500ms）
 * @param enableShakeOnLongPress 长按时是否抖动反馈（默认 true）
 * @param enableHapticFeedback 是否启用震动反馈（默认 true）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun TileWithLongPress(
    onLongPress: () -> Unit = {},
    onTap: () -> Unit = {},
    longPressDurationMillis: Long = 500L,
    enableShakeOnLongPress: Boolean = true,
    enableHapticFeedback: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var shouldShake by remember { mutableStateOf(false) }
    var shakeOffset by remember { mutableStateOf(0f) }

    // 获取触觉反馈控制器
    val hapticFeedback = LocalHapticFeedback.current

    // 抖动动画
    LaunchedEffect(shouldShake) {
        if (shouldShake) {
            // 快速小幅抖动：3次往返
            val sequence = listOf(4f, -4f, 3f, -3f, 2f, -2f, 0f)
            sequence.forEach { offset ->
                shakeOffset = offset
                delay(30)
            }
            shouldShake = false
        }
    }

    // 按压缩放动画
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(MetroDuration.FAST, easing = FastOutSlowInEasing),
        label = "long_press_scale"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
                translationX = shakeOffset
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        // 触发震动反馈（长按）
                        if (enableHapticFeedback) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        }

                        if (enableShakeOnLongPress) {
                            shouldShake = true
                        }
                        onLongPress()
                    },
                    onPress = {
                        isPressed = true
                        val released = tryAwaitRelease()
                        isPressed = false

                        // 如果是快速释放（非长按），才触发 onTap
                        if (released) {
                            // 等待一小段时间，避免与 onLongPress 冲突
                            delay(50)
                        }
                    },
                    onTap = {
                        // 触发震动反馈（点击）
                        if (enableHapticFeedback) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        }
                        onTap()
                    }
                )
            }
    ) {
        content()
    }
}
