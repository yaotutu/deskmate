package top.yaotutu.deskmate.presentation.ui.component.tiles.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.ui.component.base.BaseTile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSpec
import top.yaotutu.deskmate.presentation.ui.component.base.presets.LargeTilePresets
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 大型日历瓷砖 (4×4)
 *
 * 特性：
 * - 显示完整的日历信息（日期、星期、农历、节日等）
 * - 使用 Dashboard 预设获得旋转动画效果
 * - 适合展示丰富的日历信息
 *
 * @param title 日历标题（如 "2025年1月"）
 * @param metrics 日历指标列表（Triple<标签, 数值, 单位>）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun CalendarLargeTile(
    title: String,
    metrics: List<Triple<String, String, String>>,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.large(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        LargeTilePresets.Dashboard(
            title = title,
            metrics = metrics
        )
    }
}