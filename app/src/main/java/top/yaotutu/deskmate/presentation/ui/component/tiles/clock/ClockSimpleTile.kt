package top.yaotutu.deskmate.presentation.ui.component.tiles.clock

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.component.base.BaseTile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSpec
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 简洁时钟瓷砖 (1×1)
 *
 * 特性：
 * - 极简设计，只显示时间
 * - 超大字号，便于快速查看
 * - 适用于空间紧张的布局
 *
 * @param time 当前时间（如 "09:47"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 * @param modifier 修饰符
 */
@Composable
fun ClockSimpleTile(
    time: String,
    backgroundColor: Color = MetroTileColors.Time,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.small(backgroundColor),
        modifier = modifier
    ) {
        Text(
            text = time,
            fontSize = 36.sp,
            fontWeight = FontWeight.Thin,
            color = Color.White,
            lineHeight = 36.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
