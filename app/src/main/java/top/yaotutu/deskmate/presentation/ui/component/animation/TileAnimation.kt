package top.yaotutu.deskmate.presentation.ui.component.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

/**
 * 数字滚动动画
 *
 * @param targetValue 目标数值
 * @param durationMillis 动画持续时间
 * @param modifier 修饰符
 */
@Composable
fun CounterAnimation(
    targetValue: Int,
    durationMillis: Int = MetroDuration.SLOW,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    // 使用 Metro 标准缓动曲线
    val animatedValue by animateIntAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = MetroEasing.Standard
        ),
        label = "counter_value"
    )

    Box(modifier = modifier) {
        content(animatedValue)
    }
}

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

/**
 * 旋转动画 - Metro 风格
 *
 * 用于数据刷新指示、加载状态等场景
 *
 * @param enabled 是否启用动画
 * @param continuous 是否持续旋转（true=持续，false=单次旋转）
 * @param rotateDurationMillis 旋转一周的持续时间
 * @param clockwise 是否顺时针旋转
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun RotateTileAnimation(
    enabled: Boolean = true,
    continuous: Boolean = true,
    rotateDurationMillis: Int = MetroDuration.ROTATE_CYCLE,
    clockwise: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotate_transition")

    // 持续旋转动画
    val rotationContinuous by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (clockwise) 360f else -360f,
        animationSpec = infiniteRepeatable(
            animation = tween(rotateDurationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotate_continuous"
    )

    // 单次旋转动画
    var trigger by remember { mutableStateOf(0) }
    val rotationSingle by animateFloatAsState(
        targetValue = if (clockwise) trigger * 360f else -trigger * 360f,
        animationSpec = tween(
            durationMillis = rotateDurationMillis,
            easing = MetroEasing.Standard
        ),
        label = "rotate_single"
    )

    // 触发单次旋转
    LaunchedEffect(enabled, continuous) {
        if (enabled && !continuous) {
            while (true) {
                delay(MetroDuration.ROTATE_INTERVAL.toLong())
                trigger++
            }
        }
    }

    Box(
        modifier = modifier.graphicsLayer {
            rotationZ = if (enabled) {
                if (continuous) rotationContinuous else rotationSingle
            } else 0f
        }
    ) {
        content()
    }
}

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

/**
 * 抖动动画 - Metro 风格（持续循环版本）
 *
 * 用于重要提醒、紧急通知、错误提示
 * 持续左右抖动，效果更加明显
 *
 * @param enabled 是否启用动画
 * @param shakeDurationMillis 单次抖动周期时间
 * @param shakeDistance 抖动距离（dp）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun ShakeTileAnimation(
    enabled: Boolean = true,
    shakeDurationMillis: Int = 1500,  // 放慢到1.5秒一个周期
    shakeDistance: Float = 30f,  // 适中距离30dp
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shake_transition")

    // 持续左右抖动动画
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -shakeDistance,
        targetValue = shakeDistance,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = shakeDurationMillis / 2,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake_offsetX"
    )

    Box(
        modifier = modifier.graphicsLayer {
            translationX = if (enabled) offsetX else 0f
        }
    ) {
        content()
    }
}

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
