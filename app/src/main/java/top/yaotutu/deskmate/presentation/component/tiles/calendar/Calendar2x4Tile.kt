package top.yaotutu.deskmate.presentation.component.tiles.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.TallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 高版日历瓷砖 (4×2)
 *
 * 特性：
 * - 垂直展示日程列表
 * - 使用 VerticalList 预设
 * - 适合展示近期日程安排
 *
 * @param events 日程列表（如 ["会议 10:00", "午餐 12:00"]）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Calendar2x4Tile(
    events: List<String>,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.tall(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        TallTilePresets.VerticalList(items = events)
    }
}
