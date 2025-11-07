package top.yaotutu.deskmate.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import top.yaotutu.deskmate.presentation.component.base.LocalTileBaseUnit

/**
 * Metro 响应式间距系统
 *
 * 基于瓷砖基准单元（1×1 瓷砖尺寸）计算所有间距，确保内容随设备自动适配。
 *
 * ## 核心理念
 *
 * **基准单元 = 1×1 瓷砖的宽高**
 *
 * 所有间距都是基准单元的固定比例：
 * ```
 * 间距 = LocalTileBaseUnit.current × 比例系数
 * ```
 *
 * 不同尺寸瓷砖使用相同比例系数，自然随瓷砖大小等比缩放。
 *
 * ## 设计原则
 *
 * - **统一标准**：所有瓷砖使用相同的间距级别
 * - **自动适配**：间距随 baseCellSize 自动调整
 * - **比例协调**：与 MetroTypography 的字号比例保持协调
 * - **禁止硬编码**：不再使用固定的 8.dp、12.dp 等值
 *
 * ## 使用示例
 *
 * ### 示例1：标准间距
 * ```kotlin
 * @Composable
 * fun MyTile() {
 *     Column(
 *         verticalArrangement = Arrangement.spacedBy(MetroSpacing.medium())
 *     ) {
 *         Text("标题")
 *         Text("内容")
 *     }
 * }
 * ```
 *
 * ### 示例2：单个 Spacer
 * ```kotlin
 * Spacer(modifier = Modifier.height(MetroSpacing.large()))
 * ```
 *
 * ### 示例3：自定义比例
 * ```kotlin
 * val baseUnit = LocalTileBaseUnit.current
 * val customSpacing = baseUnit * 0.07f  // 7% 的自定义间距
 * ```
 *
 * ## 比例系数表
 *
 * | 级别 | 比例 | 典型值（baseCellSize=70dp） | 用途 |
 * |------|------|---------------------------|------|
 * | extraLarge | 10% | 7.0dp | 大瓷砖主要内容块之间 |
 * | large | 8% | 5.6dp | 中等瓷砖主要内容块之间 |
 * | medium | 6% | 4.2dp | 标准内容间距（最常用） |
 * | small | 4% | 2.8dp | 紧密相关的内容 |
 * | tiny | 2% | 1.4dp | 同一组内的元素 |
 *
 * @since 2025-01-07
 * @author Deskmate Team
 */
object MetroSpacing {

    /**
     * 超大间距 - 用于大瓷砖（4×4）主要内容块之间
     *
     * 比例：基准单元的 10%
     *
     * 典型值：
     * - baseCellSize = 70dp → 7.0dp
     * - baseCellSize = 100dp → 10.0dp
     *
     * 使用场景：
     * - 4×4 瓷砖的主要区域分隔
     * - 大型列表项之间的间距
     */
    @Composable
    fun extraLarge(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.10f
    }

    /**
     * 大间距 - 用于中等瓷砖（2×2, 4×2, 2×4）主要内容块之间
     *
     * 比例：基准单元的 8%
     *
     * 典型值：
     * - baseCellSize = 70dp → 5.6dp
     * - baseCellSize = 100dp → 8.0dp
     *
     * 使用场景：
     * - 2×2, 4×2, 2×4 瓷砖的内容分组
     * - 标题与正文之间
     * - 列表项之间
     */
    @Composable
    fun large(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.08f
    }

    /**
     * 中间距 - 标准内容间距（最常用）⭐
     *
     * 比例：基准单元的 6%
     *
     * 典型值：
     * - baseCellSize = 70dp → 4.2dp
     * - baseCellSize = 100dp → 6.0dp
     *
     * 使用场景：
     * - 大多数文本行之间的间距
     * - 图标与文字之间
     * - 按钮之间
     *
     * 这是**最常用**的间距级别，适合 80% 的场景。
     */
    @Composable
    fun medium(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.06f
    }

    /**
     * 小间距 - 用于紧密相关的内容
     *
     * 比例：基准单元的 4%
     *
     * 典型值：
     * - baseCellSize = 70dp → 2.8dp
     * - baseCellSize = 100dp → 4.0dp
     *
     * 使用场景：
     * - 标签与值之间（"温度: 22°C"）
     * - 紧密相关的文本行
     * - 小图标与文字之间
     */
    @Composable
    fun small(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.04f
    }

    /**
     * 微小间距 - 用于同一组内的元素
     *
     * 比例：基准单元的 2%
     *
     * 典型值：
     * - baseCellSize = 70dp → 1.4dp
     * - baseCellSize = 100dp → 2.0dp
     *
     * 使用场景：
     * - 非常紧密的元素排列
     * - 内联元素之间的微小间隔
     * - 装饰性分隔
     */
    @Composable
    fun tiny(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.02f
    }
}
