package top.yaotutu.deskmate.presentation.ui.component.tiles.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.ui.component.base.BaseTile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSpec
import top.yaotutu.deskmate.presentation.ui.component.base.presets.WideTilePresets
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 新闻瓷砖 (4×2)
 *
 * 特性：
 * - 显示新闻标题和摘要
 * - 使用 MediaPlayer 预设获得横向滑动动画效果
 * - 适合展示新闻信息和媒体内容
 *
 * @param icon 新闻类型图标（如 "📰", "📺", "🌐"）
 * @param title 新闻标题
 * @param summary 新闻摘要或来源
 * @param time 发布时间（如 "2小时前"）
 * @param backgroundColor 背景颜色（默认 Metro 红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun NewsTile(
    icon: String,
    title: String,
    summary: String,
    time: String = "",
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        WideTilePresets.MediaPlayer(
            icon = icon,
            title = title,
            artist = summary,
            duration = time
        )
    }
}