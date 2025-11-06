package top.yaotutu.deskmate.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

/**
 * Metro 全局缩放系统
 *
 * 提供类似前端 zoom 的容器级缩放功能，所有瓷砖内容（字体、图标、间距等）
 * 随屏幕尺寸自动等比缩放，无需修改任何瓷砖组件代码。
 *
 * ## 核心原理
 *
 * ```
 * 基准屏幕：1080×1920 手机，4 行布局
 * 基准 baseCellSize = 264dp
 *
 * 缩放系数 scaleRatio = 当前 baseCellSize / 基准 baseCellSize
 *
 * 容器整体缩放：Box(modifier = Modifier.scale(scaleRatio)) { ... }
 * ```
 *
 * ## 使用示例
 *
 * ```kotlin
 * // 在容器层面应用缩放
 * val scaleRatio = MetroScaleSystem.calculateScaleRatio(baseCellSize)
 *
 * Box(modifier = Modifier.graphicsLayer {
 *     scaleX = scaleRatio
 *     scaleY = scaleRatio
 * }) {
 *     // 所有内容自动等比缩放
 *     VerticalPriorityLayout(...)
 * }
 * ```
 *
 * @since 2025-01-05
 */
object MetroScaleSystem {
    /**
     * 基准 baseCellSize - 手机
     *
     * **2025-01-06 重构**：新计算公式（不预留间距）
     *
     * 对应标准手机屏幕（1080×1920）在 4 行布局下的基础格子尺寸。
     *
     * 计算公式：
     * ```
     * baseCellSize = min(1080, 1920) / 4
     *              = 1080 / 4
     *              = 270dp
     * ```
     */
    const val BASE_CELL_SIZE_PHONE = 270f

    /**
     * 基准 baseCellSize - 平板
     *
     * **2025-01-06 新增**
     *
     * 对应标准平板屏幕（1600×2560）在 8 行布局下的基础格子尺寸。
     *
     * 计算公式：
     * ```
     * baseCellSize = min(1600, 2560) / 8
     *              = 1600 / 8
     *              = 200dp
     * ```
     */
    const val BASE_CELL_SIZE_TABLET = 200f

    /**
     * 根据设备类型获取基准 baseCellSize
     *
     * @param isTablet 是否为平板设备
     * @return 对应的基准尺寸
     */
    fun getBaseCellSize(isTablet: Boolean): Float {
        return if (isTablet) BASE_CELL_SIZE_TABLET else BASE_CELL_SIZE_PHONE
    }

    /**
     * 计算全局缩放系数
     *
     * **2025-01-06 重构**：支持双基准（平板/手机）
     *
     * 根据当前屏幕的 baseCellSize 计算相对于基准屏幕的缩放比例。
     *
     * @param currentBaseCellSize 当前屏幕计算出的 baseCellSize
     * @param isTablet 是否为平板设备
     * @return 缩放系数（0.5 ~ 2.0 之间的浮点数）
     *
     * ## 示例（手机）
     *
     * ```
     * 小屏幕（750×1334）：
     * - baseCellSize = 750 / 4 = 187.5dp
     * - scaleRatio = 187.5 / 270 ≈ 0.69
     * → 内容缩小到 69%
     *
     * 标准屏幕（1080×1920）：
     * - baseCellSize = 1080 / 4 = 270dp
     * - scaleRatio = 270 / 270 = 1.0
     * → 内容保持原始大小
     * ```
     *
     * ## 示例（平板）
     *
     * ```
     * 小平板（1024×600）：
     * - baseCellSize = 600 / 8 = 75dp
     * - scaleRatio = 75 / 200 = 0.375
     * → 内容缩小到 37.5%
     *
     * 标准平板（1600×2560）：
     * - baseCellSize = 1600 / 8 = 200dp
     * - scaleRatio = 200 / 200 = 1.0
     * → 内容保持原始大小
     * ```
     */
    fun calculateScaleRatio(currentBaseCellSize: Dp, isTablet: Boolean): Float {
        val baseSize = getBaseCellSize(isTablet)
        return currentBaseCellSize.value / baseSize
    }

    /**
     * 计算缩放后的实际尺寸
     *
     * 当容器应用缩放后，需要调整容器的逻辑尺寸以适配缩放后的实际大小。
     *
     * @param originalSize 原始尺寸
     * @param scaleRatio 缩放系数
     * @return 缩放后的逻辑尺寸
     *
     * ## 原理
     *
     * ```
     * 如果内容设置为 1000dp，应用 1.5x 缩放后：
     * - 视觉尺寸 = 1000dp × 1.5 = 1500dp
     * - 为了让视觉尺寸正好是 1000dp，容器逻辑尺寸应为：
     *   逻辑尺寸 = 1000dp / 1.5 = 666.67dp
     * ```
     */
    fun calculateScaledSize(originalSize: Dp, scaleRatio: Float): Dp {
        return Dp(originalSize.value / scaleRatio)
    }
}

/**
 * CompositionLocal 用于向下传递缩放系数
 *
 * 用于调试和特殊场景下需要获取当前缩放系数的组件。
 * 大部分组件无需关心缩放系数，因为缩放在容器层面自动应用。
 */
val LocalScaleRatio = compositionLocalOf { 1f }

/**
 * 提供缩放系数的 CompositionLocal
 *
 * @param scaleRatio 当前缩放系数
 * @param content 子组件
 */
@Composable
fun ProvideScaleRatio(
    scaleRatio: Float,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalScaleRatio provides scaleRatio,
        content = content
    )
}
