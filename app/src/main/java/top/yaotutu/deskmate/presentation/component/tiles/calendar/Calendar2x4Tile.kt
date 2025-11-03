package top.yaotutu.deskmate.presentation.component.tiles.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.WideTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 宽版日历瓷砖 (2×4)
 *
 * 特性：
 * - 横向展示时间线
 * - 使用 Timeline 预设
 * - 适合展示当天日程时间轴
 *
 * @param timeline 时间线列表（时间 to 事件）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Calendar2x4Tile(
    timeline: List<Pair<String, String>>,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        WideTilePresets.Timeline(items = timeline)
    }
}
