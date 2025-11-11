package top.yaotutu.deskmate.presentation.component.tiles.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 标准新闻瓷砖 (2×2)
 *
 * 特性：
 * - 头部显示新闻标题，主体显示摘要
 * - 使用 HeaderBody 预设获得更好的文字层次
 * - 适合展示单条新闻要点
 *
 * @param icon 新闻图标（如 "📰"）
 * @param title 新闻标题
 * @param summary 新闻摘要
 * @param backgroundColor 背景颜色（默认 Metro 红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun News2x2Tile(
    icon: String,
    title: String,
    summary: String,
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        with(MediumTilePresets) {
            HeaderBody(
                header = title,
                body = summary
            )
        }
    }
}
