package top.yaotutu.deskmate.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.ui.component.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时钟瓷砖对比演示页面
 *
 * 展示原版和增强版时钟瓷砖的区别
 */
@Composable
fun ClockComparisonScreen(
    modifier: Modifier = Modifier
) {
    // 实时更新的时间
    var currentHour by remember { mutableStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) }
    var currentMinute by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MINUTE)) }

    LaunchedEffect(Unit) {
        while (true) {
            val calendar = Calendar.getInstance()
            currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            currentMinute = calendar.get(Calendar.MINUTE)
            delay(1000)  // 每秒更新
        }
    }

    // 格式化日期
    val dateFormat = SimpleDateFormat("EEEE, MMMM d日", Locale.CHINA)
    val currentDate = dateFormat.format(Date())
    val lunarDate = "农历九月廿五"  // 示例农历

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(8.dp)
    ) {
        TileGridContainer(modifier = Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns, _ ->
            ProvideTileGrid(baseCellSize = baseCellSize, dynamicGap = dynamicGap, columns = columns) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dynamicGap)
                ) {
                    // 标题
                    Text(
                        text = "时钟瓷砖对比",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // 原版时钟
                    Column {
                        Text(
                            text = "原版 (居中对齐, 固定字号)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        ClockTile(
                            time = String.format("%02d:%02d", currentHour, currentMinute),
                            date = currentDate,
                            lunarDate = lunarDate,
                            backgroundColor = Color(0xFF0078D7)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 增强版时钟 - 24小时制（左对齐）
                    Column {
                        Text(
                            text = "增强版 - 24小时制 (左对齐, 响应式字号)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        EnhancedClockTile(
                            hour = currentHour,
                            minute = currentMinute,
                            use24Hour = true,
                            showBlinkingColon = false,
                            date = currentDate,
                            lunarDate = lunarDate,
                            alignment = Alignment.CenterStart,  // 左对齐
                            backgroundColor = Color(0xFF00A300)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 增强版时钟 - 12小时制 + AM/PM
                    Column {
                        Text(
                            text = "增强版 - 12小时制 + AM/PM (左对齐, 无前导零)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        EnhancedClockTile(
                            hour = currentHour,
                            minute = currentMinute,
                            use24Hour = false,  // 12小时制
                            showBlinkingColon = false,
                            date = currentDate,
                            lunarDate = lunarDate,
                            alignment = Alignment.CenterStart,
                            backgroundColor = Color(0xFFFF8C00)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 增强版时钟 - 闪烁冒号
                    Column {
                        Text(
                            text = "增强版 - 闪烁冒号 (每秒闪烁)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        EnhancedClockTile(
                            hour = currentHour,
                            minute = currentMinute,
                            use24Hour = true,
                            showBlinkingColon = true,  // 闪烁冒号
                            date = currentDate,
                            lunarDate = lunarDate,
                            alignment = Alignment.CenterStart,
                            backgroundColor = Color(0xFFAA00FF)
                        )
                    }
                }
            }
        }
    }
}
