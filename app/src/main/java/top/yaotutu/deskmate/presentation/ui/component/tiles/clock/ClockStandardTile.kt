package top.yaotutu.deskmate.presentation.ui.component.tiles.clock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.ui.component.base.BaseTile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSpec
import top.yaotutu.deskmate.presentation.ui.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 标准时钟瓷砖 (2×2)
 *
 * 特性：
 * - 显示时间和日期
 * - 静态显示，无动画
 * - 平衡的信息展示
 * - 使用 MediumTilePresets.TitleSubtitle 预设
 *
 * @param time 当前时间（如 "09:47"）
 * @param date 日期（如 "星期一, 10月 28日"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun ClockStandardTile(
    time: String,
    date: String,
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
            subtitle = date
        )
    }
}
