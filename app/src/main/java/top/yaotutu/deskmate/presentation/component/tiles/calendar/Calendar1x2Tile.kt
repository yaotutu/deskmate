package top.yaotutu.deskmate.presentation.component.tiles.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.CompactTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 紧凑日历瓷砖 (1×2)
 *
 * 特性：
 * - 显示日期和星期
 * - 紧凑横向布局
 * - 适合快速查看日期
 *
 * @param day 日期（如 "31"）
 * @param weekday 星期（如 "周五"）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Calendar1x2Tile(
    day: String,
    weekday: String,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(2, 1, backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        CompactTilePresets.ProgressBar(
            label = weekday,
            progress = day
        )
    }
}
