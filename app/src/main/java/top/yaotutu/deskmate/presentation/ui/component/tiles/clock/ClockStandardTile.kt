package top.yaotutu.deskmate.presentation.ui.component.tiles.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.component.BaseTile
import top.yaotutu.deskmate.presentation.ui.component.TileSpec
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 标准时钟瓷砖 (2×2)
 *
 * 特性：
 * - 显示时间和日期
 * - 静态显示，无动画
 * - 平衡的信息展示
 *
 * @param time 当前时间（如 "09:47"）
 * @param date 日期（如 "星期一, 10月 28日"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 * @param modifier 修饰符
 */
@Composable
fun ClockStandardTile(
    time: String,
    date: String,
    backgroundColor: Color = MetroTileColors.Time,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time,
                fontSize = 56.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White,
                lineHeight = 56.sp
            )
            Text(
                text = date,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
