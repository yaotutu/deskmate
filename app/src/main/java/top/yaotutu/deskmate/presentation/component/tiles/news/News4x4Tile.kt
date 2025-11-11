package top.yaotutu.deskmate.presentation.component.tiles.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.LargeTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 大型新闻瓷砖 (4×4)
 *
 * 特性：
 * - 展示完整的新闻仪表盘
 * - 包含多个新闻分类统计
 * - 使用 Dashboard 预设获得网格布局
 *
 * @param metrics 新闻指标列表（分类, 数量, 单位）
 * @param backgroundColor 背景颜色（默认 Metro 红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun News4x4Tile(
    metrics: List<Triple<String, String, String>>,
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.large(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        with(LargeTilePresets) {
            Dashboard(
                title = "新闻分类",
                metrics = metrics
            )
        }
    }
}
