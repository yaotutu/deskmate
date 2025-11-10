package top.yaotutu.deskmate.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.LocalTileBaseUnit

/**
 * Metro 设计系统 - 响应式字号系统
 *
 * 核心原则：字号 = baseCellSize × 比例系数
 * - baseCellSize = min(屏幕宽度, 屏幕高度) / 4
 * - 字号与瓷砖尺寸保持固定比例关系
 * - 无最小值限制，纯比例缩放
 * - 确保不同尺寸设备上视觉比例一致
 *
 * 设计理念：
 * - 横屏时 min(宽, 高) 就是高度
 * - 字号随baseCellSize等比例缩放
 * - 不再根据密度（density）调整，避免比例失调
 *
 * 使用示例：
 * ```kotlin
 * Text(
 *     text = "10:12",
 *     fontSize = MetroTypography.displayHuge(),  // 根据baseCellSize自动计算
 *     fontWeight = FontWeight.Thin
 * )
 * ```
 *
 * @since 2025-01-05
 * @author Claude Code
 */
object MetroTypography {

    /**
     * 超大号字体 - 4×4 大时钟主数字
     *
     * ⭐ 2025-01-08 重新设计：75%（适配新预设布局体系）
     * 实际大小：约 67sp (平板 89dp) / 140sp (手机 186dp)
     *
     * 典型用途：
     * - Clock4x4 时间显示（"10:12"）
     * - LargeNumber 预设的 number 参数
     */
    @Composable
    fun displayHuge(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.75f).sp
    }

    /**
     * 大号字体 - 2×2 主数字/时间
     *
     * ⭐ 2025-01-08 重新设计：50%（2×2 核心内容）
     * 实际大小：约 45sp (平板) / 93sp (手机)
     *
     * 典型用途：
     * - Counter 预设的 value 参数（天气温度、待办数量）
     * - TitleSubtitle 预设的 title 参数（时钟时间）
     * - LargeNumber 预设的 number 参数（日历日期）
     */
    @Composable
    fun displayLarge(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.50f).sp
    }

    /**
     * 中号字体 - 图标显示
     *
     * ⭐ 2025-01-08 重新设计：40%（图标尺寸）
     * 实际大小：约 36sp (平板) / 74sp (手机)
     *
     * 典型用途：
     * - IconTitleSubtitle 预设的 icon 参数
     */
    @Composable
    fun displayMedium(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.40f).sp
    }

    /**
     * 标题字体 - 较大正文/单位
     *
     * ⭐ 2025-01-08 重新设计：30%（次级重要内容）
     * 实际大小：约 27sp (平板) / 56sp (手机)
     *
     * 典型用途：
     * - Counter 预设的 unit 参数（"°C"、"/5"）
     * - HeaderBody 预设的 body 参数（新闻正文）
     */
    @Composable
    fun titleLarge(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.30f).sp
    }

    /**
     * 正文大号 - 标题/标签
     *
     * ⭐ 2025-01-08 重新设计：22%（标签文字）
     * 实际大小：约 20sp (平板) / 41sp (手机)
     *
     * 典型用途：
     * - LargeNumber 预设的 label 参数（日历月份）
     * - HeaderBody 预设的 header 参数（新闻标题）
     */
    @Composable
    fun bodyLarge(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.22f).sp
    }

    /**
     * 正文中号 - 副标题/说明
     *
     * ⭐ 2025-01-08 重新设计：18%（副标题文字）
     * 实际大小：约 16sp (平板) / 33sp (手机)
     *
     * 典型用途：
     * - Counter 预设的 label 参数（天气状况、任务标签）
     * - TitleSubtitle 预设的 subtitle 参数（时钟日期）
     */
    @Composable
    fun bodyMedium(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.18f).sp
    }

    /**
     * 正文小号 - 辅助文字
     *
     * ⭐ 2025-01-08 重新设计：16%（辅助说明）
     * 实际大小：约 14sp (平板) / 30sp (手机)
     *
     * 典型用途：
     * - IconTitleSubtitle 预设的 subtitle 参数
     * - 农历显示、事件描述
     */
    @Composable
    fun bodySmall(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.16f).sp
    }

    /**
     * 标签字体 - 最小文字
     *
     * ⭐ 2025-01-08 重新设计：12%（角标/状态）
     * 实际大小：约 11sp (平板) / 22sp (手机)
     *
     * 典型用途：
     * - 角标文字
     * - 状态标签
     */
    @Composable
    fun labelSmall(): TextUnit {
        val baseUnit = LocalTileBaseUnit.current
        return (baseUnit.value * 0.12f).sp
    }
}
