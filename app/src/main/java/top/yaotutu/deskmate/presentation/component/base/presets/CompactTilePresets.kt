package top.yaotutu.deskmate.presentation.component.base.presets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import top.yaotutu.deskmate.presentation.component.base.FadeContent
import top.yaotutu.deskmate.presentation.theme.MetroTypography
import top.yaotutu.deskmate.presentation.theme.MetroSpacing
import top.yaotutu.deskmate.presentation.theme.MetroPadding

/**
 * 1×2 紧凑横向瓷砖预设（1行×2列）
 *
 * 空间限制：宽约 160-240dp，高约 80-120dp
 * 设计原则：横向布局，信息紧凑
 *
 * 包含 5 种预设布局：
 * 1. TimeDateCompact - 时间+日期紧凑排列
 * 2. IconLabel - 图标+标签横向排列
 * 3. ProgressBar - 进度条显示
 * 4. DualValue - 两个数值并排
 * 5. StatusText - 状态文本横向显示
 */
object CompactTilePresets {

    /**
     * 预设1：时间+日期紧凑排列
     *
     * 适用场景：紧凑时钟、日期显示
     *
     * @param time 时间文本
     * @param date 日期文本
     * @param timeSize 时间字体大小（默认 40sp）
     * @param dateSize 日期字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun TimeDateCompact(
        time: String,
        date: String,
        timeSize: TextUnit = MetroTypography.titleLarge(),
        dateSize: TextUnit = MetroTypography.bodySmall(),
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                fontSize = timeSize,
                fontWeight = FontWeight.Thin,
                color = color
            )
            Text(
                text = date,
                fontSize = dateSize,
                fontWeight = FontWeight.Light,
                color = color.copy(alpha = 0.9f)
            )
        }
    }

    /**
     * 预设2：图标+标签横向排列
     *
     * 适用场景：快捷方式、功能按钮
     *
     * @param icon 图标
     * @param label 标签文本
     * @param iconSize 图标大小（默认 48sp）
     * @param labelSize 标签字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun IconLabel(
        icon: String,
        label: String,
        iconSize: TextUnit = MetroTypography.displayMedium(),
        labelSize: TextUnit = MetroTypography.bodyMedium(),
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MetroPadding.small()),
            horizontalArrangement = Arrangement.spacedBy(MetroSpacing.medium(), Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = iconSize,
                color = color
            )
            Text(
                text = label,
                fontSize = labelSize,
                fontWeight = FontWeight.Light,
                color = color
            )
        }
    }

    /**
     * 预设3：进度条显示 ⭐ 固定动画：FADE 淡入淡出
     *
     * 适用场景：任务进度、充电状态、下载进度
     * 最佳动画：淡入淡出，表示进度更新
     *
     * @param label 标签文本
     * @param progress 进度文本（如 "75%"）
     * @param labelSize 标签字体大小（默认 14sp）
     * @param progressSize 进度字体大小（默认 32sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun ProgressBar(
        label: String,
        progress: String,
        labelSize: TextUnit = MetroTypography.bodySmall(),
        progressSize: TextUnit = MetroTypography.titleLarge(),
        color: Color = Color.White
    ) {
        // 固定使用 FADE 动画
        FadeContent(
            contents = listOf(
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = MetroPadding.small()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = label,
                            fontSize = labelSize,
                            fontWeight = FontWeight.Light,
                            color = color.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(MetroSpacing.small()))
                        Text(
                            text = progress,
                            fontSize = progressSize,
                            fontWeight = FontWeight.Thin,
                            color = color
                        )
                    }
                }
            )
        )
    }

    /**
     * 预设4：两个数值并排
     *
     * 适用场景：数据对比、双指标显示
     *
     * @param leftValue 左侧数值
     * @param rightValue 右侧数值
     * @param leftLabel 左侧标签（可选）
     * @param rightLabel 右侧标签（可选）
     * @param valueSize 数值字体大小（默认 32sp）
     * @param labelSize 标签字体大小（默认 12sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun DualValue(
        leftValue: String,
        rightValue: String,
        leftLabel: String? = null,
        rightLabel: String? = null,
        valueSize: TextUnit = MetroTypography.titleLarge(),
        labelSize: TextUnit = MetroTypography.labelSmall(),
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧数值
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = leftValue,
                    fontSize = valueSize,
                    fontWeight = FontWeight.Thin,
                    color = color
                )
                leftLabel?.let {
                    Text(
                        text = it,
                        fontSize = labelSize,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.8f)
                    )
                }
            }

            // 右侧数值
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = rightValue,
                    fontSize = valueSize,
                    fontWeight = FontWeight.Thin,
                    color = color
                )
                rightLabel?.let {
                    Text(
                        text = it,
                        fontSize = labelSize,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }

    /**
     * 预设5：状态文本横向显示
     *
     * 适用场景：通知消息、状态提示
     *
     * @param icon 状态图标（可选）
     * @param statusText 状态文本
     * @param iconSize 图标大小（默认 32sp）
     * @param textSize 文本大小（默认 18sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun StatusText(
        statusText: String,
        icon: String? = null,
        iconSize: TextUnit = MetroTypography.titleLarge(),
        textSize: TextUnit = MetroTypography.bodyMedium(),
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MetroPadding.small()),
            horizontalArrangement = Arrangement.spacedBy(MetroSpacing.medium(), Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Text(
                    text = it,
                    fontSize = iconSize,
                    color = color
                )
            }
            Text(
                text = statusText,
                fontSize = textSize,
                fontWeight = FontWeight.Light,
                color = color,
                maxLines = 1
            )
        }
    }
}
