package top.yaotutu.deskmate.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.presentation.component.base.LocalTileBaseUnit

/**
 * Metro 响应式内边距系统
 *
 * 基于瓷砖基准单元（1×1 瓷砖尺寸）和瓷砖总格子数计算合适的内边距。
 *
 * ## 核心理念
 *
 * **基准单元 = 1×1 瓷砖的宽高**
 *
 * 内边距根据瓷砖大小自动调整：
 * - 小瓷砖（≤2格）：使用较小的内边距（6%），节省空间
 * - 中等瓷砖（≤8格）：使用标准内边距（8%）
 * - 大瓷砖（>8格）：使用较大的内边距（10%），增加留白
 *
 * ```
 * padding = LocalTileBaseUnit.current × 比例系数
 * ```
 *
 * ## 设计原则
 *
 * - **自动适配**：padding 随瓷砖大小和屏幕尺寸自动调整
 * - **比例协调**：与 MetroSpacing 的间距比例保持一致
 * - **统一标准**：所有瓷砖使用相同的计算逻辑
 *
 * ## 使用示例
 *
 * ### 示例1：预设组件中使用
 * ```kotlin
 * @Composable
 * fun MyPreset(text: String) {
 *     Column(
 *         modifier = Modifier
 *             .fillMaxSize()
 *             .padding(MetroPadding.auto(columns = 2, rows = 2))  // 2×2 瓷砖自动 padding
 *     ) {
 *         Text(text)
 *     }
 * }
 * ```
 *
 * ### 示例2：瓷砖组件中使用
 * ```kotlin
 * @Composable
 * fun MyTile() {
 *     BaseTile(spec = TileSpec.square(MetroColors.Blue)) {
 *         Box(
 *             modifier = Modifier
 *                 .fillMaxSize()
 *                 .padding(MetroPadding.forSize(TileSpec.square(MetroColors.Blue)))
 *         ) {
 *             // 内容
 *         }
 *     }
 * }
 * ```
 *
 * ### 示例3：自定义比例
 * ```kotlin
 * val customPadding = MetroPadding.custom(ratio = 0.07f)  // 7% 的自定义 padding
 * ```
 *
 * ## 比例系数表（⭐ 2025-01-07 优化）
 *
 * | 瓷砖大小 | 总格子数 | 比例 | 典型值（baseCellSize=70dp） |
 * |---------|---------|------|---------------------------|
 * | 小（1×1, 1×2） | ≤2 | 8% | 5.6dp | ⭐ 提升 |
 * | 中（2×2, 4×2, 2×4） | ≤8 | 8% | 5.6dp |
 * | 大（4×4） | >8 | 10% | 7.0dp |
 *
 * @since 2025-01-07
 * @author Deskmate Team
 */
object MetroPadding {

    /**
     * 自动计算内边距（推荐）⭐
     *
     * 根据瓷砖的列数和行数自动选择合适的内边距比例。
     *
     * @param columns 瓷砖占用的列数
     * @param rows 瓷砖占用的行数
     * @return 响应式内边距
     *
     * ## 示例
     * ```kotlin
     * // 1×1 小瓷砖
     * MetroPadding.auto(columns = 1, rows = 1)  // → 基准单元 × 6%
     *
     * // 2×2 中等瓷砖
     * MetroPadding.auto(columns = 2, rows = 2)  // → 基准单元 × 8%
     *
     * // 4×4 大瓷砖
     * MetroPadding.auto(columns = 4, rows = 4)  // → 基准单元 × 10%
     * ```
     */
    @Composable
    fun auto(columns: Int, rows: Int): Dp {
        val baseUnit = LocalTileBaseUnit.current
        val totalCells = columns * rows

        return when {
            totalCells <= 2 -> baseUnit * 0.12f  // ⭐ 小瓷砖：6% → 12%（超大优化）
            totalCells <= 8 -> baseUnit * 0.12f  // ⭐ 中等瓷砖：8% → 12%（超大优化）
            else -> baseUnit * 0.15f             // ⭐ 大瓷砖：10% → 15%（超大优化）
        }
    }

    /**
     * 根据 TileSpec 计算内边距
     *
     * 从 TileSpec 中提取 columns 和 rows 信息，自动计算内边距。
     *
     * @param spec 瓷砖规格
     * @return 响应式内边距
     *
     * ## 示例
     * ```kotlin
     * val spec = TileSpec.square(MetroColors.Blue)  // 2×2
     * val padding = MetroPadding.forSize(spec)      // → 基准单元 × 8%
     * ```
     */
    @Composable
    fun forSize(spec: top.yaotutu.deskmate.presentation.component.base.TileSpec): Dp {
        return auto(columns = spec.columns, rows = spec.rows)
    }

    /**
     * 小瓷砖内边距（1×1, 1×2）
     *
     * ⭐ 2025-01-07 大幅优化：6% → 10%（显著提升视觉舒适度）
     *
     * 典型值：
     * - baseCellSize = 70dp → 7.0dp
     * - baseCellSize = 100dp → 10.0dp
     *
     * 使用场景：
     * - 1×1 小方块瓷砖
     * - 1×2 窄高条瓷砖
     */
    @Composable
    fun small(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.10f
    }

    /**
     * 中等瓷砖内边距（2×2, 4×2, 2×4）
     *
     * 比例：基准单元的 8%
     *
     * 典型值：
     * - baseCellSize = 70dp → 5.6dp
     * - baseCellSize = 100dp → 8.0dp
     *
     * 使用场景：
     * - 2×2 标准方块瓷砖
     * - 4×2 宽版瓷砖
     * - 2×4 高版瓷砖
     */
    @Composable
    fun medium(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.08f
    }

    /**
     * 大瓷砖内边距（4×4）
     *
     * 比例：基准单元的 10%
     *
     * 典型值：
     * - baseCellSize = 70dp → 7.0dp
     * - baseCellSize = 100dp → 10.0dp
     *
     * 使用场景：
     * - 4×4 大型瓷砖
     * - 需要更多留白的复杂布局
     */
    @Composable
    fun large(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.10f
    }

    /**
     * 自定义比例的内边距
     *
     * 允许开发者指定自定义的比例系数。
     *
     * @param ratio 比例系数（0.0-1.0）
     * @return 响应式内边距
     *
     * ## 示例
     * ```kotlin
     * val padding = MetroPadding.custom(ratio = 0.07f)  // 7% 的 padding
     * ```
     */
    @Composable
    fun custom(ratio: Float): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * ratio.coerceIn(0f, 1f)
    }

    /**
     * 零内边距
     *
     * 用于需要完全填充的布局（如全屏图片）。
     */
    @Composable
    fun none(): Dp = 0.dp
}
