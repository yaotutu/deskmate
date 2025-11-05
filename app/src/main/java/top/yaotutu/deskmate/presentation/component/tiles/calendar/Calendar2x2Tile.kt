package top.yaotutu.deskmate.presentation.component.tiles.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 标准日历瓷砖 (2×2) - 使用 BaseTile 架构
 *
 * 特性：
 * - 支持自动 FLIP 动画（通过 TileSpec）
 * - 显示日期和农历信息
 * - 适合主屏幕日历展示
 *
 * @param dayNumber 日期数字（如 "17"）
 * @param monthName 月份名称（如 "十一月"）
 * @param lunarDayName 农历日期或节日（如 "农历十月初七" 或 "中秋节"）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Calendar2x2Tile(
    dayNumber: String,
    monthName: String,
    lunarDayName: String = "",
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        MediumTilePresets.IconTitleSubtitle(
            icon = "📅",
            title = "$dayNumber 日",
            subtitle = lunarDayName.ifEmpty { monthName }
        )
    }
}
