package top.yaotutu.deskmate.presentation.component.tiles.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.WideTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 宽版待办瓷砖 (2×4)
 *
 * 特性：
 * - 横向展示待办统计
 * - 使用 ThreeColumns 预设展示多项指标
 * - 适合展示任务分类统计
 *
 * @param metrics 待办指标列表（标签, 数值, 单位）
 * @param backgroundColor 背景颜色（默认 Metro 紫色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Todo2x4Tile(
    metrics: List<Triple<String, String, String>>,
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        WideTilePresets.ThreeColumns(items = metrics)
    }
}
