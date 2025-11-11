package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.SmallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 小型天气瓷砖 (1×1)
 *
 * 特性：
 * - 仅显示天气图标
 * - 最小化设计，节省空间
 * - 适合仪表板快速浏览
 *
 * @param icon 天气图标（如 "☀️", "🌧️"）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Weather1x1Tile(
    icon: String,
    backgroundColor: Color = MetroTileColors.Weather,
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
