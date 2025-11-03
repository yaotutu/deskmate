package top.yaotutu.deskmate.presentation.component.tiles.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 标准日历瓷砖 (2×2)
 *
 * 特性：
 * - 显示日期数字、月份和星期
 * - 使用 LargeNumber 预设突出日期
 * - 翻转动画切换月份和星期
 *
 * @param dayNumber 日期数字（如 "31"）
 * @param monthName 月份（如 "1月"）
 * @param weekday 星期（如 "星期五"）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun CalendarStandardTile(
    dayNumber: String,
    monthName: String,
    weekday: String = monthName,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        MediumTilePresets.LargeNumber(
            number = dayNumber,
            label = monthName,
            backLabel = weekday
        )
    }
}