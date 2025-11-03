package top.yaotutu.deskmate.presentation.component.tiles.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.SmallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 小型新闻瓷砖 (1×1)
 *
 * 特性：
 * - 仅显示新闻图标
 * - 最小化设计
 * - 快速访问新闻
 *
 * @param icon 新闻图标（如 "📰"）
 * @param backgroundColor 背景颜色（默认 Metro 红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun News1x1Tile(
    icon: String = "📰",
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.small(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        SmallTilePresets.IconOnly(icon = icon)
    }
}
