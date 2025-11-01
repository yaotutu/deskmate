package top.yaotutu.deskmate.presentation.component.base.presets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.PulseContent

/**
 * 1×1 小方形瓷砖预设
 *
 * 空间限制：约 80-120dp
 * 设计原则：简洁明了，单一焦点
 *
 * 包含 5 种预设布局：
 * 1. SingleLabel - 单行大字（时间、数字）
 * 2. IconOnly - 纯图标（应用图标）
 * 3. IconWithBadge - 图标+角标（通知数量）
 * 4. MiniCounter - 小型计数器（步数）
 * 5. StatusIndicator - 状态指示器（在线状态）
 */
object SmallTilePresets {

    /**
     * 预设1：单行大字
     *
     * 适用场景：时间、温度、单个数字
     *
     * @param text 要显示的文本
     * @param fontSize 字体大小（默认 36sp）
     * @param color 文字颜色（默认白色）
     * @param fontWeight 字体粗细（默认 Thin）
     */
    @Composable
    fun SingleLabel(
        text: String,
        fontSize: TextUnit = 36.sp,
        color: Color = Color.White,
        fontWeight: FontWeight = FontWeight.Thin
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = color,
                lineHeight = fontSize
            )
        }
    }

    /**
     * 预设2：纯图标 ⭐ 固定动画：PULSE 轻微脉冲
     *
     * 适用场景：应用快捷方式、功能按钮
     * 最佳动画：轻微脉冲，突出图标的重要性
     *
     * @param icon 图标字符（Emoji 或图标字体）
     * @param iconSize 图标大小（默认 48sp）
     * @param color 图标颜色（默认白色）
     */
    @Composable
    fun IconOnly(
        icon: String,
        iconSize: TextUnit = 48.sp,
        color: Color = Color.White
    ) {
        // 固定使用 PULSE 动画
        PulseContent {
            Text(
                text = icon,
                fontSize = iconSize,
                color = color
            )
        }
    }

    /**
     * 预设3：图标+角标
     *
     * 适用场景：通知数量、未读消息
     *
     * @param icon 主图标
     * @param badgeText 角标文本（为空则不显示）
     * @param iconSize 图标大小（默认 40sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun IconWithBadge(
        icon: String,
        badgeText: String = "",
        iconSize: TextUnit = 40.sp,
        color: Color = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = icon,
                    fontSize = iconSize,
                    color = color
                )
                if (badgeText.isNotEmpty()) {
                    Text(
                        text = badgeText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }

    /**
     * 预设4：小型计数器
     *
     * 适用场景：步数、点赞数、统计数据
     *
     * @param value 数值
     * @param label 标签文本（可选）
     * @param valueSize 数值字体大小（默认 32sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun MiniCounter(
        value: String,
        label: String? = null,
        valueSize: TextUnit = 32.sp,
        color: Color = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = value,
                    fontSize = valueSize,
                    fontWeight = FontWeight.Thin,
                    color = color
                )
                label?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }

    /**
     * 预设5：状态指示器
     *
     * 适用场景：在线状态、开关状态、连接状态
     *
     * @param status 状态文本
     * @param icon 状态图标（可选）
     * @param statusSize 状态文本大小（默认 28sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun StatusIndicator(
        status: String,
        icon: String? = null,
        statusSize: TextUnit = 28.sp,
        color: Color = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                icon?.let {
                    Text(
                        text = it,
                        fontSize = 32.sp,
                        color = color
                    )
                }
                Text(
                    text = status,
                    fontSize = statusSize,
                    fontWeight = FontWeight.Light,
                    color = color
                )
            }
        }
    }
}
