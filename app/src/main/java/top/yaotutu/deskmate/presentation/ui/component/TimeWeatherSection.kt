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
            style = MaterialTheme.typography.displayLarge,
            color = Color.Black,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 日期
        Text(
            text = date,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        // 农历
        Text(
            text = lunarDate,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 天气
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 使用文本表示太阳图标
            Text(
                text = "☀",
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 56.sp),
                color = Color(0xFFFFB900)
            )
            Text(
                text = " $temperature°C",
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black
            )
        }
    }
}
