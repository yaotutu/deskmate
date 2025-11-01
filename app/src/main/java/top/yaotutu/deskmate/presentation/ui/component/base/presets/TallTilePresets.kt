package top.yaotutu.deskmate.presentation.ui.component.base.presets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.component.base.SlideContent

/**
 * 2×4 高矩形瓷砖预设
 *
 * 空间：约 160-240dp × 320-480dp
 * 设计原则：纵向布局，列表展示
 *
 * 包含 6 种预设布局：
 * 1. VerticalList - 纵向列表（3-5项）
 * 2. Timeline - 时间线纵向展示
 * 3. StepProgress - 步骤进度
 * 4. DetailedCard - 详细卡片（多行信息）
 * 5. ChatPreview - 聊天预览
 * 6. WeatherForecast - 天气预报纵向
 */
object TallTilePresets {

    /**
     * 预设1：纵向列表 ⭐ 固定动画：SLIDE 纵向滑动
     *
     * 适用场景：待办事项、通知列表
     * 最佳动画：纵向滑动，展示列表项切换
     *
     * @param items 列表项（最多显示 5 项）
     * @param itemSize 列表项字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun VerticalList(
        items: List<String>,
        itemSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        // 固定使用 SLIDE 动画
        SlideContent(
            contents = listOf(
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
                    ) {
                        items.take(5).forEach { item ->
                            Text(
                                text = "• $item",
                                fontSize = itemSize,
                                fontWeight = FontWeight.Light,
                                color = color,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            )
        )
    }

    /**
     * 预设2：时间线纵向展示
     *
     * 适用场景：日程、历史记录
     *
     * @param items 时间线项目（Pair<时间, 事件>，最多 4 项）
     * @param timeSize 时间字体大小（默认 14sp）
     * @param eventSize 事件字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun Timeline(
        items: List<Pair<String, String>>,
        timeSize: TextUnit = 14.sp,
        eventSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            items.take(4).forEach { (time, event) ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = time,
                        fontSize = timeSize,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.8f)
                    )
                    Text(
                        text = event,
                        fontSize = eventSize,
                        fontWeight = FontWeight.Light,
                        color = color,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    /**
     * 预设3：步骤进度
     *
     * 适用场景：流程引导、进度追踪
     *
     * @param steps 步骤列表（Pair<步骤名, 是否完成>，最多 4 步）
     * @param stepSize 步骤字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun StepProgress(
        steps: List<Pair<String, Boolean>>,
        stepSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            steps.take(4).forEachIndexed { index, (step, completed) ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (completed) "✓" else "${index + 1}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = if (completed) color else color.copy(alpha = 0.6f)
                    )
                    Text(
                        text = step,
                        fontSize = stepSize,
                        fontWeight = FontWeight.Light,
                        color = if (completed) color else color.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    /**
     * 预设4：详细卡片
     *
     * 适用场景：联系人详情、多行信息展示
     *
     * @param icon 顶部图标
     * @param title 标题
     * @param details 详细信息列表（最多 3 项）
     * @param iconSize 图标大小（默认 64sp）
     * @param titleSize 标题字体大小（默认 24sp）
     * @param detailSize 详情字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun DetailedCard(
        icon: String,
        title: String,
        details: List<String>,
        iconSize: TextUnit = 64.sp,
        titleSize: TextUnit = 24.sp,
        detailSize: TextUnit = 14.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = iconSize,
                color = color
            )
            Text(
                text = title,
                fontSize = titleSize,
                fontWeight = FontWeight.Light,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                details.take(3).forEach { detail ->
                    Text(
                        text = detail,
                        fontSize = detailSize,
                        fontWeight = FontWeight.ExtraLight,
                        color = color.copy(alpha = 0.9f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    /**
     * 预设5：聊天预览
     *
     * 适用场景：消息列表、对话预览
     *
     * @param messages 消息列表（Pair<发送者, 消息内容>，最多 3 条）
     * @param senderSize 发送者字体大小（默认 14sp）
     * @param messageSize 消息字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun ChatPreview(
        messages: List<Pair<String, String>>,
        senderSize: TextUnit = 14.sp,
        messageSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            messages.take(3).forEach { (sender, message) ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = sender,
                        fontSize = senderSize,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.8f)
                    )
                    Text(
                        text = message,
                        fontSize = messageSize,
                        fontWeight = FontWeight.ExtraLight,
                        color = color,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    /**
     * 预设6：天气预报纵向
     *
     * 适用场景：多日天气预报
     *
     * @param forecasts 预报列表（Triple<日期, 天气图标, 温度>，最多 4 天）
     * @param dateSize 日期字体大小（默认 14sp）
     * @param iconSize 天气图标大小（默认 32sp）
     * @param tempSize 温度字体大小（默认 20sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun WeatherForecast(
        forecasts: List<Triple<String, String, String>>,
        dateSize: TextUnit = 14.sp,
        iconSize: TextUnit = 32.sp,
        tempSize: TextUnit = 20.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            forecasts.take(4).forEach { (date, icon, temp) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = date,
                        fontSize = dateSize,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.8f)
                    )
                    Text(
                        text = icon,
                        fontSize = iconSize,
                        color = color
                    )
                    Text(
                        text = temp,
                        fontSize = tempSize,
                        fontWeight = FontWeight.Thin,
                        color = color
                    )
                }
            }
        }
    }
}
