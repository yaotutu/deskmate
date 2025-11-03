package top.yaotutu.deskmate.presentation.component.tiles.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.SmallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 小型日历瓷砖 (1×1)
 *
 * 特性：
 * - 仅显示日历图标
 * - 最小化设计
 * - 快速访问日历
 *
 * @param icon 日历图标（如 "📅"）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Calendar1x1Tile(
    icon: String = "📅",
    backgroundColor: Color = MetroTileColors.Calendar,
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
