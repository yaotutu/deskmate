package top.yaotutu.deskmate.presentation.component.tiles.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.FlipContent
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 时钟瓷砖 4×2 - 详细版
 *
 * 特性：
 * - 翻转动画效果
 * - 正面显示时间
 * - 背面显示日期和农历
 * - 最丰富的信息展示
 *
 * @param time 当前时间（如 "09:47"）
 * @param date 日期（如 "星期一, 10月 28日"）
 * @param lunarDate 农历（如 "农历九月廿八"）
 * @param backgroundColor 背景颜色（默认 Metro 蓝）
 * @param modifier 修饰符
 */
@Composable
fun Clock4x2Tile(
    time: String,
    date: String,
    lunarDate: String,
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        FlipContent(
            front = {
                // 正面：时间
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = time,
                        fontSize = 96.sp,
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                        lineHeight = 96.sp
                    )
                }
            },
            back = {
                // 背面：日期 + 农历
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = date,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        text = lunarDate,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        )
    }
}
