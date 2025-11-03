package top.yaotutu.deskmate.presentation.component.tiles.clock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 时钟瓷砖 2×2 - 标准版
 *
 * 特性：
 * - 显示时间、日期和星期
 * - 翻转动画切换日期和星期
 * - 使用 MediumTilePresets.TitleSubtitle 预设
 *
 * @param time 当前时间（如 "09:47"）
 * @param date 日期（如 "10月 28日"）
 * @param weekday 星期（如 "星期一"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Clock2x2Tile(
    time: String,
    date: String,
    weekday: String = date,
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        MediumTilePresets.TitleSubtitle(
            title = time,
            subtitle = date,
            backSubtitle = weekday
        )
    }
}
