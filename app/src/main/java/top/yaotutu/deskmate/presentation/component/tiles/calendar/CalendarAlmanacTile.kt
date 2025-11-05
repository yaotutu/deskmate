package top.yaotutu.deskmate.presentation.component.tiles.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.FlipContent
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography

/**
 * 2×2 黄历瓷砖 - 紧凑的传统黄历显示
 *
 * 特性：
 * - 正面：日期数字、月份、农历/节日
 * - 背面：干支、星座、宜忌
 * - 翻转动画切换正反面
 * - 节日高亮显示
 *
 * @param dayNumber 日期数字（如 "3"）
 * @param monthName 月份（如 "11月"）
 * @param lunarInfo 农历或节日信息（如 "初三" 或 "春节"）
 * @param ganZhi 干支简写（如 "辛丑 丙寅 乙卯"）
 * @param constellation 星座（如 "天蝎座"）
 * @param luck 宜忌简写（如 "宜:祭祀 忌:动土"）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun CalendarAlmanacTile(
    dayNumber: String,
    monthName: String,
    lunarInfo: String,
    ganZhi: String,
    constellation: String,
    luck: String,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        FlipContent(
            front = {
                // 正面：日期 + 农历/节日
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dayNumber,
                        fontSize = MetroTypography.displayHuge(),
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                        lineHeight = MetroTypography.displayHuge()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = monthName,
                        fontSize = MetroTypography.bodyLarge(),
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = lunarInfo,
                        fontSize = MetroTypography.bodyMedium(),
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            },
            back = {
                // 背面：干支 + 星座 + 宜忌
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = ganZhi,
                        fontSize = MetroTypography.bodySmall(),
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        lineHeight = MetroTypography.bodyMedium()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = constellation,
                        fontSize = MetroTypography.bodySmall(),
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = luck,
                        fontSize = MetroTypography.labelSmall(),
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = MetroTypography.bodyMedium()
                    )
                }
            }
        )
    }
}
