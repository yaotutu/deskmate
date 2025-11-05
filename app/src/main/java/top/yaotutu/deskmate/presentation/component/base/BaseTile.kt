package top.yaotutu.deskmate.presentation.component.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import top.yaotutu.deskmate.presentation.component.animation.FlipTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.PulseTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.SlideTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.FadeTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.PeekTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.MarqueeTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.RotateTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.ShimmerTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.WipeTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.DepthTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.BounceTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.ShakeTileAnimation
import top.yaotutu.deskmate.presentation.component.animation.CounterAnimation
import top.yaotutu.deskmate.presentation.component.common.TileClickEffect
import top.yaotutu.deskmate.presentation.component.common.TileWithInteraction

/**
 * BaseTile - 瓷砖框架层核心组件
 *
 * 职责：
 * 1. 自动获取布局参数（LocalBaseCellSize、LocalDynamicGap）
 * 2. 自动应用简单动画（PULSE）
 * 3. 自动处理点击交互
 * 4. 提供统一的容器样式
 *
 * 对于复杂动画（FLIP、SLIDE），业务组件需要在 content 中使用对应的辅助组件：
 * - FlipContent - 翻转动画
 * - SlideContent - 滑动动画
 *
 * 业务组件使用示例：
 * ```
 * // 示例1：无动画
 * @Composable
 * fun ClockSimpleTile(time: String) {
 *     BaseTile(spec = TileSpec.small(MetroColors.Blue)) {
 *         Text(time, ...)
 *     }
 * }
 *
 * // 示例2：脉冲动画（自动处理）
 * @Composable
 * fun WeatherTile(temperature: Int) {
 *     BaseTile(spec = TileSpec.square(MetroColors.Orange, AnimationType.PULSE)) {
 *         Text("$temperature°", ...)
 *     }
 * }
 *
 * // 示例3：翻转动画（使用 FlipContent）
 * @Composable
 * fun ClockDetailedTile(time: String, date: String) {
 *     BaseTile(spec = TileSpec.wideMedium(MetroColors.Blue, AnimationType.FLIP)) {
 *         FlipContent(
 *             front = { Text(time, ...) },
 *             back = { Text(date, ...) }
 *         )
 *     }
 * }
 * ```
 *
 * @param spec 瓷砖规格配置
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param content 业务内容
 */
@Composable
fun BaseTile(
    spec: TileSpec,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // 框架自动获取布局参数（业务组件无需关心）
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    // ⭐ 根据 spec.animation 自动应用简单动画
    val animatedContent: @Composable () -> Unit = {
        when (spec.animation) {
            AnimationType.PULSE -> PulseContent(
                scaleRange = 0.98f to 1.02f,  // 增大缩放范围：从 98% 到 102%（共 4% 变化）
                pulseDurationMillis = 800      // 加快速度：0.8秒一个方向
            ) { content() }
            AnimationType.ROTATE -> RotateContent { content() }
            AnimationType.SHIMMER -> ShimmerContent { content() }
            AnimationType.DEPTH -> DepthContent { content() }
            AnimationType.BOUNCE -> BounceContent { content() }
            AnimationType.SHAKE -> ShakeContent { content() }

            // 复杂动画需要在 content 中显式调用对应的包装器
            // FLIP - 需要 FlipContent(front, back)
            // SLIDE - 需要 SlideContent(contents)
            // FADE - 需要 FadeContent(contents)
            // PEEK - 需要 PeekContent(mainContent, peekContent)
            // MARQUEE - 需要 MarqueeContent(...) { content }
            // WIPE - 需要 WipeContent(contents)
            // COUNTER - 需要 CounterContent(targetValue) { value -> content }

            else -> content()  // NONE 或需要手动处理的动画
        }
    }

    // 调用底层 Tile 函数（框架处理布局）
    Tile(
        columns = spec.columns,
        rows = spec.rows,
        backgroundColor = spec.color,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        onClick = onClick,
        clickEffect = TileClickEffect.PRESS_SCALE,
        modifier = modifier,
        content = animatedContent  // ← 使用包装后的 content
    )
}

// ==================== 辅助组件 ====================

/**
 * FlipContent - 翻转动画内容辅助组件
 *
 * 用于在 BaseTile 中定义翻转动画的正反面内容。
 * 这个组件会自动调用 FlipTileAnimation 处理翻转效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.wideMedium(MetroColors.Blue, AnimationType.FLIP)) {
 *     FlipContent(
 *         front = {
 *             Text("10:12", fontSize = 96.sp, ...)
 *         },
 *         back = {
 *             Column {
 *                 Text("2025年1月28日", ...)
 *                 Text("农历腊月廿九", ...)
 *             }
 *         }
 *     )
 * }
 * ```
 *
 * @param front 正面内容
 * @param back 背面内容
 */
@Composable
fun FlipContent(
    front: @Composable () -> Unit,
    back: @Composable () -> Unit
) {
    FlipTileAnimation(
        frontContent = front,
        backContent = back
    )
}

/**
 * SlideContent - 滑动动画内容辅助组件
 *
 * 用于在 BaseTile 中定义滑动动画的多个内容项。
 * 这个组件会自动调用 SlideTileAnimation 处理滑动效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.large(MetroColors.Red, AnimationType.SLIDE)) {
 *     SlideContent(
 *         listOf(
 *             { NewsItem("新闻1") },
 *             { NewsItem("新闻2") },
 *             { NewsItem("新闻3") }
 *         )
 *     )
 * }
 * ```
 *
 * @param contents 内容列表
 */
@Composable
fun SlideContent(
    contents: List<@Composable () -> Unit>
) {
    SlideTileAnimation(contents = contents)
}

/**
 * FadeContent - 淡入淡出动画内容辅助组件
 *
 * 用于在 BaseTile 中定义淡入淡出动画的多个内容项。
 * 这个组件会自动调用 FadeTileAnimation 处理淡入淡出效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Green, AnimationType.FADE)) {
 *     FadeContent(
 *         listOf(
 *             { Text("内容1", fontSize = 32.sp, color = Color.White) },
 *             { Text("内容2", fontSize = 32.sp, color = Color.White) },
 *             { Text("内容3", fontSize = 32.sp, color = Color.White) }
 *         )
 *     )
 * }
 * ```
 *
 * @param contents 内容列表
 * @param fadeDurationMillis 淡入淡出持续时间（默认 500ms）
 * @param fadeIntervalMillis 自动切换间隔时间（默认 6000ms）
 */
@Composable
fun FadeContent(
    contents: List<@Composable () -> Unit>,
    fadeDurationMillis: Int = top.yaotutu.deskmate.presentation.theme.MetroDuration.SLOW,
    fadeIntervalMillis: Long = top.yaotutu.deskmate.presentation.theme.MetroDuration.FLIP_CYCLE.toLong()
) {
    FadeTileAnimation(
        contents = contents,
        fadeDurationMillis = fadeDurationMillis,
        fadeIntervalMillis = fadeIntervalMillis
    )
}

/**
 * CounterContent - 数字滚动动画内容辅助组件
 *
 * 用于在 BaseTile 中定义数字滚动动画。
 * 这个组件会自动调用 CounterAnimation 处理数字变化的滚动效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Orange, AnimationType.COUNTER)) {
 *     CounterContent(
 *         targetValue = temperature,
 *         content = { value ->
 *             Column(
 *                 Modifier.fillMaxSize(),
 *                 Arrangement.Center,
 *                 Alignment.CenterHorizontally
 *             ) {
 *                 Text("$value°", fontSize = 64.sp, fontWeight = FontWeight.Thin, color = Color.White)
 *                 Text("温度", fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
 *             }
 *         }
 *     )
 * }
 * ```
 *
 * @param targetValue 目标数值
 * @param durationMillis 动画持续时间（默认 500ms）
 * @param content 渲染函数，接收当前动画值
 */
@Composable
fun CounterContent(
    targetValue: Int,
    durationMillis: Int = top.yaotutu.deskmate.presentation.theme.MetroDuration.SLOW,
    content: @Composable (Int) -> Unit
) {
    CounterAnimation(
        targetValue = targetValue,
        durationMillis = durationMillis,
        content = content
    )
}

/**
 * PeekContent - 探出动画内容辅助组件 ⭐ Windows Phone 标志性动画
 *
 * 用于在 BaseTile 中定义探出动画的主要内容和探出内容。
 * 这个组件会自动调用 PeekTileAnimation 处理探出效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Blue, AnimationType.PEEK)) {
 *     PeekContent(
 *         mainContent = {
 *             Column {
 *                 Text("📧", fontSize = 64.sp)
 *                 Text("3 封新邮件", fontSize = 20.sp, color = Color.White)
 *             }
 *         },
 *         peekContent = {
 *             Column {
 *                 Text("来自：张三", fontSize = 16.sp, color = Color.White)
 *                 Text("会议提醒", fontSize = 14.sp, color = Color.White.copy(0.8f))
 *             }
 *         },
 *         peekHeight = 0.4f
 *     )
 * }
 * ```
 *
 * @param mainContent 主要内容（一直显示）
 * @param peekContent 探出内容（从指定方向探出）
 * @param peekHeight 探出高度比例（0.0-1.0，默认 0.3）
 * @param direction 探出方向（默认从底部探出）
 */
@Composable
fun PeekContent(
    mainContent: @Composable () -> Unit,
    peekContent: @Composable () -> Unit,
    peekHeight: Float = 0.3f,
    direction: top.yaotutu.deskmate.presentation.component.animation.PeekDirection =
        top.yaotutu.deskmate.presentation.component.animation.PeekDirection.BOTTOM
) {
    PeekTileAnimation(
        mainContent = mainContent,
        peekContent = peekContent,
        peekHeight = peekHeight,
        direction = direction
    )
}

/**
 * MarqueeContent - 跑马灯动画内容辅助组件
 *
 * 用于在 BaseTile 中定义跑马灯滚动内容。
 * 这个组件会自动调用 MarqueeTileAnimation 处理连续滚动效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.wideMedium(MetroColors.Red, AnimationType.MARQUEE)) {
 *     MarqueeContent(
 *         direction = MarqueeDirection.HORIZONTAL,
 *         speed = 40f
 *     ) {
 *         Text(
 *             text = "突发新闻：这是一条很长的新闻标题，需要滚动显示...",
 *             fontSize = 20.sp,
 *             fontWeight = FontWeight.Light,
 *             color = Color.White
 *         )
 *     }
 * }
 * ```
 *
 * @param direction 滚动方向（默认水平滚动）
 * @param speed 滚动速度（dp/秒，默认 30）
 * @param spacing 循环间距（dp，默认 50）
 * @param content 要滚动的内容
 */
@Composable
fun MarqueeContent(
    direction: top.yaotutu.deskmate.presentation.component.animation.MarqueeDirection =
        top.yaotutu.deskmate.presentation.component.animation.MarqueeDirection.HORIZONTAL,
    speed: Float = 30f,
    spacing: Int = 50,
    content: @Composable () -> Unit
) {
    MarqueeTileAnimation(
        direction = direction,
        speed = speed,
        spacing = spacing,
        content = content
    )
}

/**
 * WipeContent - 擦除动画内容辅助组件
 *
 * 用于在 BaseTile 中定义擦除动画的多个内容项。
 * 这个组件会自动调用 WipeTileAnimation 处理擦除切换效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.large(MetroColors.Red, AnimationType.WIPE)) {
 *     WipeContent(
 *         contents = listOf(
 *             { NewsItem("新闻标题1") },
 *             { NewsItem("新闻标题2") },
 *             { NewsItem("新闻标题3") }
 *         ),
 *         direction = WipeDirection.LEFT_TO_RIGHT,
 *         style = WipeStyle.SLIDE
 *     )
 * }
 * ```
 *
 * @param contents 内容列表（至少2项）
 * @param direction 擦除方向（默认从左到右）
 * @param style 擦除样式（默认滑动）
 */
@Composable
fun WipeContent(
    contents: List<@Composable () -> Unit>,
    direction: top.yaotutu.deskmate.presentation.component.animation.WipeDirection =
        top.yaotutu.deskmate.presentation.component.animation.WipeDirection.LEFT_TO_RIGHT,
    style: top.yaotutu.deskmate.presentation.component.animation.WipeStyle =
        top.yaotutu.deskmate.presentation.component.animation.WipeStyle.SLIDE
) {
    WipeTileAnimation(
        contents = contents,
        direction = direction,
        style = style
    )
}

/**
 * DepthContent - 深度动画内容辅助组件
 *
 * 用于在 BaseTile 中定义深度/透视动画。
 * 这个组件会自动调用 DepthTileAnimation 处理 3D 深度效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Purple, AnimationType.DEPTH)) {
 *     DepthContent(
 *         scaleRange = 0.95f to 1.05f,
 *         shadowRange = 2f to 8f
 *     ) {
 *         Column {
 *             Text("📷", fontSize = 64.sp)
 *             Text("照片", fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
 *         }
 *     }
 * }
 * ```
 *
 * @param scaleRange 缩放范围（默认 0.95f to 1.05f）
 * @param shadowRange 阴影范围（默认 2f to 8f）
 * @param content 内容
 */
@Composable
fun DepthContent(
    scaleRange: Pair<Float, Float> = 0.95f to 1.05f,
    shadowRange: Pair<Float, Float> = 2f to 8f,
    content: @Composable () -> Unit
) {
    DepthTileAnimation(
        scaleRange = scaleRange,
        shadowRange = shadowRange,
        content = content
    )
}

/**
 * PulseContent - 脉冲动画内容辅助组件
 *
 * 用于在 BaseTile 中定义脉冲动画。
 * 这个组件会自动调用 PulseTileAnimation 处理呼吸效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Blue)) {
 *     PulseContent(
 *         scaleRange = 1.0f to 1.02f
 *     ) {
 *         Text("📱", fontSize = 64.sp, color = Color.White)
 *     }
 * }
 * ```
 *
 * @param scaleRange 缩放范围（默认 1.0f to 1.02f）
 * @param pulseDurationMillis 脉冲持续时间（默认 MetroDuration.PULSE_CYCLE / 2）
 * @param content 内容
 */
@Composable
fun PulseContent(
    scaleRange: Pair<Float, Float> = 1.0f to 1.02f,
    pulseDurationMillis: Int = top.yaotutu.deskmate.presentation.theme.MetroDuration.PULSE_CYCLE / 2,
    content: @Composable () -> Unit
) {
    PulseTileAnimation(
        scaleRange = scaleRange,
        pulseDurationMillis = pulseDurationMillis,
        content = content
    )
}

/**
 * RotateContent - 旋转动画内容辅助组件
 *
 * 用于在 BaseTile 中定义旋转动画。
 * 这个组件会自动调用 RotateTileAnimation 处理旋转效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Orange)) {
 *     RotateContent {
 *         Text("⚙️", fontSize = 64.sp, color = Color.White)
 *     }
 * }
 * ```
 *
 * @param content 内容
 */
@Composable
fun RotateContent(
    content: @Composable () -> Unit
) {
    RotateTileAnimation(content = content)
}

/**
 * ShimmerContent - 微光动画内容辅助组件
 *
 * 用于在 BaseTile 中定义微光动画。
 * 这个组件会自动调用 ShimmerTileAnimation 处理微光效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Blue)) {
 *     ShimmerContent {
 *         Text("加载中...", fontSize = 24.sp, color = Color.White)
 *     }
 * }
 * ```
 *
 * @param content 内容
 */
@Composable
fun ShimmerContent(
    content: @Composable () -> Unit
) {
    ShimmerTileAnimation(content = content)
}

/**
 * BounceContent - 弹跳动画内容辅助组件
 *
 * 用于在 BaseTile 中定义弹跳动画。
 * 这个组件会自动调用 BounceTileAnimation 处理弹跳效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Green)) {
 *     BounceContent {
 *         Text("🔔", fontSize = 64.sp, color = Color.White)
 *     }
 * }
 * ```
 *
 * @param content 内容
 */
@Composable
fun BounceContent(
    content: @Composable () -> Unit
) {
    BounceTileAnimation(content = content)
}

/**
 * ShakeContent - 抖动动画内容辅助组件
 *
 * 用于在 BaseTile 中定义抖动动画。
 * 这个组件会自动调用 ShakeTileAnimation 处理抖动效果。
 *
 * 使用示例：
 * ```
 * BaseTile(spec = TileSpec.square(MetroColors.Red)) {
 *     ShakeContent {
 *         Text("⚠️", fontSize = 64.sp, color = Color.White)
 *     }
 * }
 * ```
 *
 * @param content 内容
 */
@Composable
fun ShakeContent(
    content: @Composable () -> Unit
) {
    ShakeTileAnimation(content = content)
}
