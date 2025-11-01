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
 * 4×2 宽矩形瓷砖预设
 *
 * 空间：约 320-480dp × 160-240dp
 * 设计原则：横向展开，信息丰富
 *
 * 包含 6 种预设布局：
 * 1. HorizontalTitleSubtitle - 标题副标题横向排列
 * 2. IconTextSide - 图标和文字左右分布
 * 3. ThreeColumns - 三列信息展示
 * 4. Timeline - 时间线横向展示
 * 5. MetricsDashboard - 多指标仪表盘
 * 6. MediaPlayer - 媒体播放器布局
 */
object WideTilePresets {

    /**
     * 预设1：标题副标题横向排列
     *
     * 适用场景：宽版时钟、横向信息展示
     *
     * @param title 标题文本
     * @param subtitle 副标题文本
     * @param titleSize 标题字体大小（默认 72sp）
     * @param subtitleSize 副标题字体大小（默认 20sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun HorizontalTitleSubtitle(
        title: String,
        subtitle: String,
        titleSize: TextUnit = 72.sp,
        subtitleSize: TextUnit = 20.sp,
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = titleSize,
                fontWeight = FontWeight.Thin,
                color = color
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = subtitle,
                    fontSize = subtitleSize,
                    fontWeight = FontWeight.Light,
                    color = color.copy(alpha = 0.9f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    /**
     * 预设2：图标和文字左右分布
     *
     * 适用场景：应用详情、功能展示
     *
     * @param icon 图标
     * @param title 标题文本
     * @param subtitle 副标题文本
     * @param iconSize 图标大小（默认 80sp）
     * @param titleSize 标题字体大小（默认 28sp）
     * @param subtitleSize 副标题字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun IconTextSide(
        icon: String,
        title: String,
        subtitle: String,
        iconSize: TextUnit = 80.sp,
        titleSize: TextUnit = 28.sp,
        subtitleSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = iconSize,
                color = color
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    fontSize = titleSize,
                    fontWeight = FontWeight.Light,
                    color = color
                )
                Text(
                    text = subtitle,
                    fontSize = subtitleSize,
                    fontWeight = FontWeight.ExtraLight,
                    color = color.copy(alpha = 0.9f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    /**
     * 预设3：三列信息展示
     *
     * 适用场景：多数据对比、仪表盘
     *
     * @param items 三列数据（Triple<标题, 数值, 单位>）
     * @param valueSize 数值字体大小（默认 32sp）
     * @param labelSize 标题字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun ThreeColumns(
        items: List<Triple<String, String, String>>,
        valueSize: TextUnit = 32.sp,
        labelSize: TextUnit = 14.sp,
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.take(3).forEach { (label, value, unit) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = value,
                            fontSize = valueSize,
                            fontWeight = FontWeight.Thin,
                            color = color
                        )
                        if (unit.isNotEmpty()) {
                            Text(
                                text = unit,
                                fontSize = labelSize,
                                fontWeight = FontWeight.Light,
                                color = color,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                    Text(
                        text = label,
                        fontSize = labelSize,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }

    /**
     * 预设4：时间线横向展示
     *
     * 适用场景：日程安排、历史记录
     *
     * @param items 时间线项目（Pair<时间, 事件>）
     * @param timeSize 时间字体大小（默认 16sp）
     * @param eventSize 事件字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun Timeline(
        items: List<Pair<String, String>>,
        timeSize: TextUnit = 16.sp,
        eventSize: TextUnit = 14.sp,
        color: Color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.take(3).forEach { (time, event) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = time,
                        fontSize = timeSize,
                        fontWeight = FontWeight.Light,
                        color = color
                    )
                    Text(
                        text = event,
                        fontSize = eventSize,
                        fontWeight = FontWeight.ExtraLight,
                        color = color.copy(alpha = 0.9f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    /**
     * 预设5：多指标仪表盘
     *
     * 适用场景：数据监控、性能指标
     *
     * @param title 仪表盘标题
     * @param metrics 指标列表（Pair<标签, 数值>）
     * @param titleSize 标题字体大小（默认 20sp）
     * @param metricSize 指标字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun MetricsDashboard(
        title: String,
        metrics: List<Pair<String, String>>,
        titleSize: TextUnit = 20.sp,
        metricSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                fontSize = titleSize,
                fontWeight = FontWeight.Light,
                color = color
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                metrics.take(4).forEach { (label, value) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = value,
                            fontSize = metricSize,
                            fontWeight = FontWeight.Thin,
                            color = color
                        )
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = color.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }

    /**
     * 预设6：媒体播放器布局 ⭐ 固定动画：SLIDE 滑动轮播
     *
     * 适用场景：音乐播放器、视频播放器
     * 最佳动画：横向滑动，展示播放列表切换
     *
     * @param icon 播放状态图标
     * @param title 媒体标题
     * @param artist 艺术家/作者
     * @param duration 时长
     * @param iconSize 图标大小（默认 64sp）
     * @param titleSize 标题字体大小（默认 20sp）
     * @param subtextSize 副文本字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun MediaPlayer(
        icon: String,
        title: String,
        artist: String,
        duration: String = "",
        iconSize: TextUnit = 64.sp,
        titleSize: TextUnit = 20.sp,
        subtextSize: TextUnit = 14.sp,
        color: Color = Color.White
    ) {
        // 固定使用 SLIDE 动画
        SlideContent(
            contents = listOf(
                {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = icon,
                            fontSize = iconSize,
                            color = color
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = title,
                                fontSize = titleSize,
                                fontWeight = FontWeight.Light,
                                color = color,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = artist,
                                fontSize = subtextSize,
                                fontWeight = FontWeight.ExtraLight,
                                color = color.copy(alpha = 0.9f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (duration.isNotEmpty()) {
                                Text(
                                    text = duration,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    color = color.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            )
        )
    }
}
