package top.yaotutu.deskmate.presentation.ui.component.enhancement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==================== Metro 状态栏 ====================

/**
 * Metro 风格状态栏
 *
 * 显示时间、电量、信号等信息
 *
 * @param time 当前时间
 * @param batteryLevel 电量百分比（0-100）
 * @param signalStrength 信号强度（0-4）
 * @param backgroundColor 背景颜色
 */
@Composable
fun MetroStatusBar(
    time: String,
    batteryLevel: Int = 100,
    signalStrength: Int = 4,
    backgroundColor: Color = Color(0xFF000000),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 时间
        Text(
            text = time,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )

        // 状态图标
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 信号强度
            SignalIcon(strength = signalStrength)

            // 电量
            BatteryIcon(level = batteryLevel)
        }
    }
}

@Composable
private fun SignalIcon(strength: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(((index + 1) * 3).dp)
                    .background(
                        if (index < strength) Color.White else Color.White.copy(alpha = 0.3f)
                    )
            )
        }
    }
}

@Composable
private fun BatteryIcon(level: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // 电池图标
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(10.dp)
                .background(Color.White.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(level.toFloat() / 100f)
                    .background(
                        when {
                            level > 50 -> Color.White
                            level > 20 -> Color.Yellow
                            else -> Color.Red
                        }
                    )
            )
        }

        // 电量百分比
        Text(
            text = "$level%",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraLight,
            color = Color.White,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
