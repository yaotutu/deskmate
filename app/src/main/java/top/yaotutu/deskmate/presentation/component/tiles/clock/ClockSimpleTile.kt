package top.yaotutu.deskmate.presentation.component.tiles.clock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.SmallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 简洁时钟瓷砖 (1×1)
 *
 * 特性：
 * - 极简设计，只显示时间
 * - 超大字号，便于快速查看
 * - 适用于空间紧张的布局
 * - 使用 SmallTilePresets.SingleLabel 预设
 *
 * @param time 当前时间（如 "09:47"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun ClockSimpleTile(
    time: String,
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.small(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        SmallTilePresets.SingleLabel(text = time)
    }
}
