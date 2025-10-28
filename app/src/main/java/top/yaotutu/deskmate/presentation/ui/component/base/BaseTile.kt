package top.yaotutu.deskmate.presentation.ui.component.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import top.yaotutu.deskmate.presentation.ui.component.animation.*
import top.yaotutu.deskmate.presentation.ui.component.interaction.TileClickEffect
import top.yaotutu.deskmate.presentation.ui.component.interaction.TileWithInteraction
import top.yaotutu.deskmate.presentation.ui.component.legacy.LocalBaseCellSize
import top.yaotutu.deskmate.presentation.ui.component.legacy.LocalDynamicGap

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
    content: @Composable BoxScope.() -> Unit
) {
    // 框架自动获取布局参数（业务组件无需关心）
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    // 根据动画类型包装内容
    val animatedContent: @Composable () -> Unit = when (spec.animation) {
        AnimationType.PULSE -> {
            // 脉冲动画：周期性缩放
            { PulseTileAnimation { Box { content() } } }
        }
        AnimationType.ROTATE -> {
            // 旋转动画：持续旋转（用于刷新指示）
            { RotateTileAnimation { Box { content() } } }
        }
        AnimationType.BOUNCE -> {
            // 弹跳动画：上下弹跳（用于新内容提醒）
            { BounceTileAnimation { Box { content() } } }
        }
        AnimationType.SHAKE -> {
            // 抖动动画：横向抖动（用于重要通知）
            { ShakeTileAnimation { Box { content() } } }
        }
        AnimationType.SHIMMER -> {
            // 微光动画：加载状态指示
            { ShimmerTileAnimation { Box { content() } } }
        }
        AnimationType.FLIP, AnimationType.SLIDE, AnimationType.FADE, AnimationType.COUNTER -> {
            // 复杂动画：需要业务组件使用辅助组件
            // - FLIP: 使用 FlipContent
            // - SLIDE: 使用 SlideContent
            // - FADE: 使用 FadeContent
            // - COUNTER: 使用 CounterContent
            { Box { content() } }
        }
        AnimationType.NONE -> {
            // 无动画：直接渲染
            { Box { content() } }
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
        content = animatedContent
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
    fadeDurationMillis: Int = top.yaotutu.deskmate.presentation.ui.theme.MetroDuration.SLOW,
    fadeIntervalMillis: Long = top.yaotutu.deskmate.presentation.ui.theme.MetroDuration.FLIP_CYCLE.toLong()
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
    durationMillis: Int = top.yaotutu.deskmate.presentation.ui.theme.MetroDuration.SLOW,
    content: @Composable (Int) -> Unit
) {
    CounterAnimation(
        targetValue = targetValue,
        durationMillis = durationMillis,
        content = content
    )
}
