package top.yaotutu.deskmate.presentation.component.tiles.clock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.CompactTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 时钟瓷砖 1×2 - 紧凑版
 *
 * 特性：
 * - 横条布局，高度只有一个单元格
 * - 适合作为快捷信息条
 * - 时间和日期横向排列
 * - 使用 CompactTilePresets.TimeDateCompact 预设
 *
 * @param time 当前时间（如 "13:58"）
 * @param date 简短日期（如 "10/28"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Clock1x2Tile(
    time: String,
    date: String,
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(2, 1, backgroundColor),  // 自定义尺寸 2×1
        onClick = onClick,
        modifier = modifier
    ) {
        CompactTilePresets.TimeDateCompact(
            time = time,
            date = date
        )
    }
}
