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
 * Windows Phone Metro 风格的瓷砖交互包装器组件
 *
 * 提供高级组合效果和统一的交互接口
 */

/**
 * 7. 组合效果：按压 + 点击动效
 *
 * 效果：按压时缩小，释放后触发特定动效
 * 适用：需要同时有按压反馈和点击特效的场景
 *
 * @param onTap 点击回调
 * @param clickEffect 点击动效类型
 * @param modifier 修饰符
 * @param content 内容
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
 *
 * @param effect 交互效果类型
 * @param onTap 点击回调
 * @param modifier 修饰符
 * @param content 内容
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
