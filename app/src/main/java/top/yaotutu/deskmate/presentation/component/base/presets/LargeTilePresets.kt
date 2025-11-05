package top.yaotutu.deskmate.presentation.component.base.presets

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
import top.yaotutu.deskmate.presentation.component.base.RotateContent
import top.yaotutu.deskmate.presentation.theme.MetroTypography

/**
 * 4×4 大方形瓷砖预设
 *
 * 空间：约 320-480dp × 320-480dp
 * 设计原则：丰富内容，复杂布局
 *
 * 包含 8 种预设布局：
 * 1. Dashboard - 仪表盘（多指标）
 * 2. RichCard - 丰富卡片（所有信息）
 * 3. Calendar - 日历视图
 * 4. PhotoGrid - 照片网格（4×4）
 * 5. NewsList - 新闻列表（多项）
 * 6. DetailedInfo - 详细信息展示
 * 7. ChartDisplay - 图表展示占位
 * 8. FeatureShowcase - 功能展示
 */
object LargeTilePresets {

    /**
     * 预设1：仪表盘（多指标）⭐ 固定动画：ROTATE 数据刷新
     *
     * 适用场景：数据监控、综合看板
     * 最佳动画：持续旋转，表示数据刷新和实时监控
     *
     * @param title 仪表盘标题
     * @param metrics 指标列表（Triple<标签, 数值, 单位>，最多 6 个）
     * @param titleSize 标题字体大小（默认 24sp）
     * @param valueSize 数值字体大小（默认 28sp）
     * @param labelSize 标签字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun Dashboard(
        title: String,
        metrics: List<Triple<String, String, String>>,
        titleSize: TextUnit = MetroTypography.bodyLarge(),
        valueSize: TextUnit = MetroTypography.bodyLarge(),
        labelSize: TextUnit = MetroTypography.bodySmall(),
        color: Color = Color.White
    ) {
        // 固定使用 ROTATE 动画
        RotateContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = titleSize,
                    fontWeight = FontWeight.Light,
                    color = color
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    metrics.take(6).chunked(2).forEach { rowMetrics ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowMetrics.forEach { (label, value, unit) ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
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
                                                fontSize = 16.sp,
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
                }
            }
        }
    }

    /**
     * 预设2：丰富卡片
     *
     * 适用场景：综合信息展示、详细页面
     *
     * @param icon 顶部图标
     * @param title 主标题
     * @param subtitle 副标题
     * @param details 详细信息列表（最多 5 项）
     * @param iconSize 图标大小（默认 80sp）
     * @param titleSize 标题字体大小（默认 32sp）
     * @param subtitleSize 副标题字体大小（默认 18sp）
     * @param detailSize 详情字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun RichCard(
        icon: String,
        title: String,
        subtitle: String,
        details: List<String>,
        iconSize: TextUnit = MetroTypography.displayLarge(),
        titleSize: TextUnit = MetroTypography.titleLarge(),
        subtitleSize: TextUnit = MetroTypography.bodyMedium(),
        detailSize: TextUnit = MetroTypography.bodyMedium(),
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = iconSize,
                color = color
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
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
                    color = color.copy(alpha = 0.9f)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                details.take(5).forEach { detail ->
                    Text(
                        text = detail,
                        fontSize = detailSize,
                        fontWeight = FontWeight.ExtraLight,
                        color = color.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    /**
     * 预设3：日历视图
     *
     * 适用场景：月历、日程安排
     *
     * @param month 月份标题
     * @param days 日期列表（最多 31 天）
     * @param highlightedDays 高亮日期集合
     * @param monthSize 月份字体大小（默认 20sp）
     * @param daySize 日期字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun Calendar(
        month: String,
        days: List<String>,
        highlightedDays: Set<String> = emptySet(),
        monthSize: TextUnit = MetroTypography.bodyLarge(),
        daySize: TextUnit = MetroTypography.bodySmall(),
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = month,
                fontSize = monthSize,
                fontWeight = FontWeight.Light,
                color = color,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                days.chunked(7).take(5).forEach { week ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        week.forEach { day ->
                            Text(
                                text = day,
                                fontSize = daySize,
                                fontWeight = if (day in highlightedDays) FontWeight.Normal else FontWeight.ExtraLight,
                                color = if (day in highlightedDays) color else color.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * 预设4：照片网格
     *
     * 适用场景：相册、图片墙
     *
     * @param photos 照片标识列表（Emoji 或图标，最多 16 个）
     * @param photoSize 照片大小（默认 32sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun PhotoGrid(
        photos: List<String>,
        photoSize: TextUnit = MetroTypography.titleLarge(),
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            photos.chunked(4).take(4).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { photo ->
                        Text(
                            text = photo,
                            fontSize = photoSize,
                            color = color
                        )
                    }
                }
            }
        }
    }

    /**
     * 预设5：新闻列表
     *
     * 适用场景：新闻头条、文章列表
     *
     * @param title 列表标题
     * @param items 新闻列表（Pair<标题, 摘要>，最多 4 条）
     * @param titleSize 列表标题字体大小（默认 20sp）
     * @param itemTitleSize 新闻标题字体大小（默认 16sp）
     * @param summarySize 摘要字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun NewsList(
        title: String,
        items: List<Pair<String, String>>,
        titleSize: TextUnit = MetroTypography.bodyLarge(),
        itemTitleSize: TextUnit = MetroTypography.bodyMedium(),
        summarySize: TextUnit = MetroTypography.bodySmall(),
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                fontSize = titleSize,
                fontWeight = FontWeight.Light,
                color = color
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items.take(4).forEach { (itemTitle, summary) ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = itemTitle,
                            fontSize = itemTitleSize,
                            fontWeight = FontWeight.Light,
                            color = color,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = summary,
                            fontSize = summarySize,
                            fontWeight = FontWeight.ExtraLight,
                            color = color.copy(alpha = 0.85f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    /**
     * 预设6：详细信息展示
     *
     * 适用场景：用户资料、产品详情
     *
     * @param header 头部标题
     * @param infoItems 信息项列表（Pair<标签, 值>，最多 6 项）
     * @param headerSize 头部字体大小（默认 24sp）
     * @param labelSize 标签字体大小（默认 14sp）
     * @param valueSize 值字体大小（默认 18sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun DetailedInfo(
        header: String,
        infoItems: List<Pair<String, String>>,
        headerSize: TextUnit = MetroTypography.bodyLarge(),
        labelSize: TextUnit = MetroTypography.bodySmall(),
        valueSize: TextUnit = MetroTypography.bodyMedium(),
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = header,
                fontSize = headerSize,
                fontWeight = FontWeight.Light,
                color = color
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                infoItems.take(6).forEach { (label, value) ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = label,
                            fontSize = labelSize,
                            fontWeight = FontWeight.Light,
                            color = color.copy(alpha = 0.7f)
                        )
                        Text(
                            text = value,
                            fontSize = valueSize,
                            fontWeight = FontWeight.Light,
                            color = color,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    /**
     * 预设7：图表展示占位
     *
     * 适用场景：数据可视化、统计图表
     *
     * @param chartTitle 图表标题
     * @param chartIcon 图表图标/占位符
     * @param summary 摘要信息
     * @param titleSize 标题字体大小（默认 20sp）
     * @param iconSize 图标大小（默认 80sp）
     * @param summarySize 摘要字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun ChartDisplay(
        chartTitle: String,
        chartIcon: String,
        summary: String,
        titleSize: TextUnit = MetroTypography.bodyLarge(),
        iconSize: TextUnit = MetroTypography.displayLarge(),
        summarySize: TextUnit = MetroTypography.bodyMedium(),
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = chartTitle,
                fontSize = titleSize,
                fontWeight = FontWeight.Light,
                color = color
            )
            Text(
                text = chartIcon,
                fontSize = iconSize,
                color = color
            )
            Text(
                text = summary,
                fontSize = summarySize,
                fontWeight = FontWeight.ExtraLight,
                color = color.copy(alpha = 0.9f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    /**
     * 预设8：功能展示
     *
     * 适用场景：应用介绍、功能列表
     *
     * @param mainIcon 主图标
     * @param appName 应用名称
     * @param features 功能列表（最多 5 个）
     * @param iconSize 图标大小（默认 64sp）
     * @param nameSize 应用名字体大小（默认 28sp）
     * @param featureSize 功能字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun FeatureShowcase(
        mainIcon: String,
        appName: String,
        features: List<String>,
        iconSize: TextUnit = MetroTypography.displayLarge(),
        nameSize: TextUnit = MetroTypography.bodyLarge(),
        featureSize: TextUnit = MetroTypography.bodyMedium(),
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = mainIcon,
                fontSize = iconSize,
                color = color
            )
            Text(
                text = appName,
                fontSize = nameSize,
                fontWeight = FontWeight.Light,
                color = color
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                features.take(5).forEach { feature ->
                    Text(
                        text = "• $feature",
                        fontSize = featureSize,
                        fontWeight = FontWeight.ExtraLight,
                        color = color.copy(alpha = 0.9f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
