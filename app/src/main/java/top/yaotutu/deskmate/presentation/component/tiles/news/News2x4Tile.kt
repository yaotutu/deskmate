package top.yaotutu.deskmate.presentation.component.tiles.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.TallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 高版新闻瓷砖 (4×2)
 *
 * 特性：
 * - 垂直展示新闻列表
 * - 使用 VerticalList 预设
 * - 适合展示多条新闻标题
 *
 * @param headlines 新闻标题列表
 * @param backgroundColor 背景颜色（默认 Metro 红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun News2x4Tile(
    headlines: List<String>,
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.tall(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        TallTilePresets.VerticalList(items = headlines)
    }
}
