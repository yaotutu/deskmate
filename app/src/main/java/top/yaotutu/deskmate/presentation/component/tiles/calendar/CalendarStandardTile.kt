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
 * - 显示日期和星期信息
 * - 使用 TitleSubtitle 预设获得翻转动画效果
 * - 正面显示日期，背面显示星期
 *
 * @param date 日期信息（如 "1月31日"）
 * @param weekday 星期信息（如 "星期五"）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun CalendarStandardTile(
    date: String,
    weekday: String,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        MediumTilePresets.TitleSubtitle(
            title = date,
            subtitle = weekday
        )
    }
}