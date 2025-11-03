package top.yaotutu.deskmate.presentation.component.tiles.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.LargeTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 大型待办瓷砖 (4×4)
 *
 * 特性：
 * - 展示完整的待办仪表盘
 * - 包含多项任务指标和分类统计
 * - 使用 Dashboard 预设获得网格布局
 *
 * @param metrics 待办指标列表（标签, 数值, 单位）
 * @param backgroundColor 背景颜色（默认 Metro 紫色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Todo4x4Tile(
    metrics: List<Triple<String, String, String>>,
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.large(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        LargeTilePresets.Dashboard(
            title = "任务统计",
            metrics = metrics
        )
    }
}
