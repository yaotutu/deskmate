package top.yaotutu.deskmate.presentation.component.tiles.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.SmallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 小型待办瓷砖 (1×1)
 *
 * 特性：
 * - 仅显示待办图标
 * - 最小化设计
 * - 快速访问待办列表
 *
 * @param icon 待办图标（如 "✓"）
 * @param backgroundColor 背景颜色（默认 Metro 紫色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Todo1x1Tile(
    icon: String = "✓",
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.small(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        with(SmallTilePresets) {
            IconOnly(icon = icon)
        }
    }
}
