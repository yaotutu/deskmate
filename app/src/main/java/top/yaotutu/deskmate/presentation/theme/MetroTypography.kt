package top.yaotutu.deskmate.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.LocalBaseCellSize

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
     * 超大号字体 - 主要时间、温度显示
     *
     * 占 baseCellSize 的 40%
     *
     * 典型用途：
     * - Clock 时间显示（"10:12"）
     * - Weather 温度显示（"22°"）
     */
    @Composable
    fun displayHuge(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.40f).sp
    }

    /**
     * 大号字体 - 主标题、日期显示
     *
     * 占 baseCellSize 的 30%
     *
     * 典型用途：
     * - 2x2 瓷砖主内容
     * - 日历日期显示
     */
    @Composable
    fun displayLarge(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.30f).sp
    }

    /**
     * 中号字体 - 副标题、图标
     *
     * 占 baseCellSize 的 23%
     *
     * 典型用途：
     * - 2x2 瓷砖副标题
     * - News 新闻标题
     * - 图标文字
     */
    @Composable
    fun displayMedium(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.23f).sp
    }

    /**
     * 标题字体 - 小尺寸瓷砖主内容
     *
     * 占 baseCellSize 的 18%
     *
     * 典型用途：
     * - 1x1 瓷砖主文字
     * - Todo 任务标题
     */
    @Composable
    fun titleLarge(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.18f).sp
    }

    /**
     * 正文大号 - 辅助信息、日期等
     *
     * 占 baseCellSize 的 10%
     *
     * 典型用途：
     * - 日期文字（"星期一, 10月 28日"）
     * - 天气状况（"晴天"）
     */
    @Composable
    fun bodyLarge(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.10f).sp
    }

    /**
     * 正文中号 - 次要文字、描述
     *
     * 占 baseCellSize 的 8%
     *
     * 典型用途：
     * - News 新闻摘要
     * - Calendar 事件描述
     */
    @Composable
    fun bodyMedium(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.08f).sp
    }

    /**
     * 正文小号 - 辅助文字
     *
     * 占 baseCellSize 的 7%
     *
     * 典型用途：
     * - 农历显示
     * - Todo 完成数量
     */
    @Composable
    fun bodySmall(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.07f).sp
    }

    /**
     * 标签字体 - 最小文字、角标
     *
     * 占 baseCellSize 的 6%
     *
     * 典型用途：
     * - 角标文字
     * - 状态标签
     */
    @Composable
    fun labelSmall(): TextUnit {
        val baseCellSize = LocalBaseCellSize.current
        return (baseCellSize.value * 0.06f).sp
    }
}
