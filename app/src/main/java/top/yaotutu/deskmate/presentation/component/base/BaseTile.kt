package top.yaotutu.deskmate.presentation.component.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import top.yaotutu.deskmate.presentation.component.common.TileClickEffect

/**
 * BaseTile - 瓷砖框架层核心组件 ⭐ 2025-01-10 重构版
 *
 * 职责：
 * 1. 自动获取布局参数（LocalBaseCellSize、LocalDynamicGap）
 * 2. 提供统一的动画配置 DSL（AnimationScope）
 * 3. 自动处理点击交互
 * 4. 提供统一的容器样式
 *
 * 核心改进：
 * - ✅ **API 一致性**: 所有动画都通过 AnimationScope DSL 配置
 * - ✅ **零配置体验**: Preset 自动选择最佳动画
 * - ✅ **类型安全**: 编译时检查动画配置是否正确
 * - ✅ **代码简洁**: 消除 400+ 行辅助函数,统一动画处理
 *
 * 业务组件使用示例：
 * ```kotlin
 * // 示例1：简单内容 + 自动动画
 * @Composable
 * fun ClockSimpleTile(time: String) {
 *     BaseTile(spec = TileSpec.small(MetroColors.Blue)) {
 *         single { Text(time, ...) }
 *     }
 * }
 *
 * // 示例2：翻转动画
 * @Composable
 * fun ClockDetailedTile(time: String, date: String) {
 *     BaseTile(spec = TileSpec.square(MetroColors.Blue, AnimationType.FLIP)) {
 *         flip(
 *             front = { Text(time, ...) },
 *             back = { Text(date, ...) }
 *         )
 *     }
 * }
 *
 * // 示例3：滑动动画
 * @Composable
 * fun NewsTile(items: List<News>) {
 *     BaseTile(spec = TileSpec.wideMedium(MetroColors.Red, AnimationType.SLIDE)) {
 *         slide(
 *             { NewsItem(items[0]) },
 *             { NewsItem(items[1]) },
 *             { NewsItem(items[2]) }
 *         )
 *     }
 * }
 * ```
 *
 * @param spec 瓷砖规格配置
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param content 业务内容（使用 AnimationScope DSL 配置）
 */
@Composable
fun BaseTile(
    spec: TileSpec,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable AnimationScope.() -> Unit
) {
    // 框架自动获取布局参数（业务组件无需关心）
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    // 创建 AnimationScope 实例
    var animatedContent: (@Composable () -> Unit)? = null

    val scope = AnimationScopeImpl(
        animationType = spec.animation,
        applyAnimation = { composable ->
            animatedContent = composable
        }
    )

    // 执行 content lambda,配置动画
    scope.content()

    // 调用底层 Tile 函数（框架处理布局）
    Tile(
        rows = spec.rows,
        columns = spec.columns,
        backgroundColor = spec.color,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        onClick = onClick,
        clickEffect = TileClickEffect.PRESS_SCALE,
        modifier = modifier,
        content = animatedContent ?: {}
    )
}
