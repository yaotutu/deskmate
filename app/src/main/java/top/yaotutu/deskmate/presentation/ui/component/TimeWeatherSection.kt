package top.yaotutu.deskmate.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.R

@Composable
fun TimeWeatherSection(
    time: String,
    date: String,
    lunarDate: String,
    temperature: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // 时间
        Text(
            text = time,
            fontSize = 96.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            lineHeight = 96.sp,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 日期
        Text(
            text = date,
            fontSize = 22.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )

        // 农历
        Text(
            text = lunarDate,
            fontSize = 20.sp,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 天气
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 使用文本表示太阳图标
            Text(
                text = "☀",
                fontSize = 56.sp,
                color = Color(0xFFFFB900)
            )
            Text(
                text = " $temperature°C",
                fontSize = 52.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}
