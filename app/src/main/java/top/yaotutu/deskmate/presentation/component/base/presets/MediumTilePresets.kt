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
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.AnimationType
import top.yaotutu.deskmate.presentation.component.base.CounterContent
import top.yaotutu.deskmate.presentation.component.base.FlipContent

/**
 * 2×2 标准方形瓷砖预设
 *
 * 空间：约 160-240dp × 160-240dp
 * 设计原则：层次分明，信息平衡
 *
 * 包含 8 种预设布局：
 * 1. TitleSubtitle - 标题+副标题（最常用）
 * 2. IconTitle - 图标+标题
 * 3. IconTitleSubtitle - 图标+标题+副标题
 * 4. Counter - 大数字+单位+标签
 * 5. TwoRowList - 两行列表项
 * 6. IconGrid2x2 - 2×2 图标网格
 * 7. HeaderBody - 头部+主体内容
 * 8. ImageOverlay - 图片+覆盖文字
 */
object MediumTilePresets {

    /**
     * 预设1：标题+副标题（最常用）⭐ 固定动画：FLIP 翻转
     *
     * 适用场景：时钟、天气、日历
     * 最佳动画：正面显示标题，背面显示副标题，6秒翻转周期
     *
     * @param title 标题文本
     * @param subtitle 副标题文本
     * @param titleSize 标题字体大小（默认 56sp）
     * @param subtitleSize 副标题字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun TitleSubtitle(
        title: String,
        subtitle: String,
        titleSize: TextUnit = 56.sp,
        subtitleSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        // 固定使用 FLIP 动画
        FlipContent(
            front = {
                // 正面：显示标题
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontSize = titleSize,
                        fontWeight = FontWeight.Thin,
                        color = color,
                        lineHeight = titleSize
                    )
                }
            },
            back = {
                // 背面：显示副标题
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = subtitle,
                        fontSize = subtitleSize * 1.2f, // 背面字体稍大
                        fontWeight = FontWeight.Light,
                        color = color
                    )
                }
            }
        )
    }

    /**
     * 预设2：图标+标题
     *
     * 适用场景：应用瓷砖、功能入口
     *
     * @param icon 图标
     * @param title 标题文本
     * @param iconSize 图标大小（默认 64sp）
     * @param titleSize 标题字体大小（默认 20sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun IconTitle(
        icon: String,
        title: String,
        iconSize: TextUnit = 64.sp,
        titleSize: TextUnit = 20.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
        }
    }

    /**
     * 预设3：图标+标题+副标题 ⭐ 固定动画：PULSE 节奏感应
     *
     * 适用场景：联系人、音乐播放器
     * 最佳动画：节奏感应脉冲，适合音乐和互动元素
     *
     * @param icon 图标
     * @param title 标题文本
     * @param subtitle 副标题文本
     * @param iconSize 图标大小（默认 48sp）
     * @param titleSize 标题字体大小（默认 18sp）
     * @param subtitleSize 副标题字体大小（默认 14sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun IconTitleSubtitle(
        icon: String,
        title: String,
        subtitle: String,
        iconSize: TextUnit = 48.sp,
        titleSize: TextUnit = 18.sp,
        subtitleSize: TextUnit = 14.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
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
            Text(
                text = subtitle,
                fontSize = subtitleSize,
                fontWeight = FontWeight.ExtraLight,
                color = color.copy(alpha = 0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    /**
     * 预设4：大数字计数器 ⭐ 固定动画：COUNTER 数字滚动
     *
     * 适用场景：温度、步数、数据指标
     * 最佳动画：数字滚动效果，突出数据变化
     *
     * @param value 数值
     * @param unit 单位（可选，如 "°"、"步"）
     * @param label 标签（可选，如 "温度"、"今日步数"）
     * @param valueSize 数值字体大小（默认 64sp）
     * @param unitSize 单位字体大小（默认 32sp）
     * @param labelSize 标签字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun Counter(
        value: String,
        unit: String = "",
        label: String = "",
        valueSize: TextUnit = 64.sp,
        unitSize: TextUnit = 32.sp,
        labelSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        // 固定使用 COUNTER 动画
        // 尝试将字符串转换为整数，如果失败则使用默认值
        val numericValue = value.toIntOrNull() ?: 0

        CounterContent(
            targetValue = numericValue
        ) { currentValue ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentValue.toString(),
                        fontSize = valueSize,
                        fontWeight = FontWeight.Thin,
                        color = color
                    )
                    if (unit.isNotEmpty()) {
                        Text(
                            text = unit,
                            fontSize = unitSize,
                            fontWeight = FontWeight.Thin,
                            color = color,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
                if (label.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = label,
                        fontSize = labelSize,
                        fontWeight = FontWeight.Light,
                        color = color.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }

    /**
     * 预设5：两行列表
     *
     * 适用场景：简单待办、通知预览
     *
     * @param items 列表项（最多显示 2 项）
     * @param itemSize 列表项字体大小（默认 16sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun TwoRowList(
        items: List<String>,
        itemSize: TextUnit = 16.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            items.take(2).forEach { item ->
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

    /**
     * 预设6：2×2 图标网格
     *
     * 适用场景：功能菜单、快捷操作
     *
     * @param icons 图标列表（最多显示 4 个）
     * @param iconSize 图标大小（默认 32sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun IconGrid2x2(
        icons: List<String>,
        iconSize: TextUnit = 32.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            icons.chunked(2).take(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { icon ->
                        Text(
                            text = icon,
                            fontSize = iconSize,
                            color = color
                        )
                    }
                }
            }
        }
    }

    /**
     * 预设7：头部+主体
     *
     * 适用场景：卡片式信息展示
     *
     * @param header 头部文本
     * @param body 主体文本
     * @param headerSize 头部字体大小（默认 20sp）
     * @param bodySize 主体字体大小（默认 32sp）
     * @param color 颜色（默认白色）
     */
    @Composable
    fun HeaderBody(
        header: String,
        body: String,
        headerSize: TextUnit = 20.sp,
        bodySize: TextUnit = 32.sp,
        color: Color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = header,
                fontSize = headerSize,
                fontWeight = FontWeight.Light,
                color = color.copy(alpha = 0.8f)
            )
            Text(
                text = body,
                fontSize = bodySize,
                fontWeight = FontWeight.Thin,
                color = color,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    /**
     * 预设8：图片+覆盖文字
     *
     * 适用场景：相册、背景图瓷砖
     *
     * @param overlayText 覆盖文字
     * @param textSize 文字大小（默认 24sp）
     * @param color 文字颜色（默认白色）
     */
    @Composable
    fun ImageOverlay(
        overlayText: String,
        textSize: TextUnit = 24.sp,
        color: Color = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            // 这里可以放置图片背景
            // 使用 Coil 或其他图片加载库
            Text(
                text = overlayText,
                fontSize = textSize,
                fontWeight = FontWeight.Light,
                color = color,
                modifier = Modifier.padding(16.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
