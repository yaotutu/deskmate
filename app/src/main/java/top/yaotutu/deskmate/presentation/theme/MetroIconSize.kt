package top.yaotutu.deskmate.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import top.yaotutu.deskmate.presentation.component.base.LocalTileBaseUnit

/**
 * Metro 响应式图标尺寸系统
 *
 * 基于瓷砖基准单元（1×1 瓷砖尺寸）计算所有图标尺寸，确保图标随设备自动适配。
 *
 * ## 核心理念
 *
 * **基准单元 = 1×1 瓷砖的宽高**
 *
 * 所有图标尺寸都是基准单元的固定比例：
 * ```
 * 图标尺寸 = LocalTileBaseUnit.current × 比例系数
 * ```
 *
 * 不同尺寸瓷砖使用相同比例系数，图标自然随瓷砖大小等比缩放。
 *
 * ## 设计原则
 *
 * - **统一标准**：所有瓷砖使用相同的图标尺寸级别
 * - **自动适配**：图标随 baseCellSize 自动调整
 * - **比例协调**：与 MetroTypography 的字号比例保持协调
 * - **支持多种用途**：既适用于 Material Icon，也适用于 Emoji
 *
 * ## 使用示例
 *
 * ### 示例1：Material Icon
 * ```kotlin
 * Icon(
 *     imageVector = Icons.Default.Warning,
 *     contentDescription = "警告",
 *     modifier = Modifier.size(MetroIconSize.medium())
 * )
 * ```
 *
 * ### 示例2：Emoji 图标
 * ```kotlin
 * Text(
 *     text = "🌤️",
 *     fontSize = MetroIconSize.large().value.sp  // 转换为 sp 用于文字
 * )
 * ```
 *
 * ### 示例3：自定义尺寸
 * ```kotlin
 * val baseUnit = LocalTileBaseUnit.current
 * val customIconSize = baseUnit * 0.25f  // 25% 的自定义图标尺寸
 * ```
 *
 * ## 比例系数表
 *
 * | 级别 | 比例 | 典型值（baseCellSize=70dp） | 用途 |
 * |------|------|---------------------------|------|
 * | extraLarge | 30% | 21.0dp | 大瓷砖（4×4）主视觉元素 |
 * | large | 23% | 16.1dp | 中等瓷砖（2×2）主图标 |
 * | medium | 18% | 12.6dp | 小瓷砖（1×2）图标 |
 * | small | 12% | 8.4dp | 角标、状态指示器 |
 *
 * ## 与 MetroTypography 的关系
 *
 * ```
 * MetroIconSize.large()      ≈ MetroTypography.displayMedium()
 * MetroIconSize.medium()     ≈ MetroTypography.titleLarge()
 * MetroIconSize.small()      ≈ MetroTypography.bodyLarge()
 * ```
 *
 * 这确保图标与文字在视觉上保持协调。
 *
 * @since 2025-01-07
 * @author Deskmate Team
 */
object MetroIconSize {

    /**
     * 超大图标 - 用于大瓷砖（4×4）的主要视觉元素
     *
     * 比例：基准单元的 30%
     *
     * 典型值：
     * - baseCellSize = 70dp → 21.0dp
     * - baseCellSize = 100dp → 30.0dp
     *
     * 使用场景：
     * - 4×4 瓷砖的中心图标
     * - 大型天气图标
     * - 主要装饰性元素
     *
     * 对应字号级别：≈ MetroTypography.displayLarge()
     */
    @Composable
    fun extraLarge(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.30f
    }

    /**
     * 大图标 - 用于中等瓷砖（2×2）的主图标
     *
     * 比例：基准单元的 23%
     *
     * 典型值：
     * - baseCellSize = 70dp → 16.1dp
     * - baseCellSize = 100dp → 23.0dp
     *
     * 使用场景：
     * - 2×2 瓷砖的主图标
     * - 4×2, 2×4 瓷砖的辅助图标
     * - 标准 Emoji 显示
     *
     * 对应字号级别：≈ MetroTypography.displayMedium()
     */
    @Composable
    fun large(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.23f
    }

    /**
     * 中图标 - 用于小瓷砖（1×2, 1×1）的图标
     *
     * 比例：基准单元的 18%
     *
     * 典型值：
     * - baseCellSize = 70dp → 12.6dp
     * - baseCellSize = 100dp → 18.0dp
     *
     * 使用场景：
     * - 1×2 瓷砖的主图标
     * - 1×1 瓷砖的图标
     * - 列表项的前导图标
     *
     * 对应字号级别：≈ MetroTypography.titleLarge()
     */
    @Composable
    fun medium(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.18f
    }

    /**
     * 小图标 - 用于角标、状态指示器
     *
     * 比例：基准单元的 12%
     *
     * 典型值：
     * - baseCellSize = 70dp → 8.4dp
     * - baseCellSize = 100dp → 12.0dp
     *
     * 使用场景：
     * - 角标图标
     * - 状态指示器（已读/未读）
     * - 小型装饰图标
     * - ErrorTile 的警告图标
     *
     * 对应字号级别：≈ MetroTypography.bodyLarge()
     */
    @Composable
    fun small(): Dp {
        val baseUnit = LocalTileBaseUnit.current
        return baseUnit * 0.12f
    }
}
