package top.yaotutu.deskmate.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

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

    // 根据动画类型包装内容（仅自动处理简单动画）
    val animatedContent: @Composable () -> Unit = when (spec.animation) {
        AnimationType.PULSE -> {
            // 脉冲动画：自动包装
            { PulseTileAnimation { Box { content() } } }
        }
        else -> {
            // 其他类型（NONE、FLIP、SLIDE）：不自动处理
            // FLIP 和 SLIDE 需要业务组件使用 FlipContent/SlideContent 辅助组件
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
