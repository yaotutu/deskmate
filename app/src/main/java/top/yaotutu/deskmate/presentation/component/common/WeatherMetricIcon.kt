package top.yaotutu.deskmate.presentation.component.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * 天气指标图标枚举
 *
 * 使用 Material Icons 显示各种气象指标
 */
enum class WeatherMetricType {
    HUMIDITY,       // 湿度
    WIND_SPEED,     // 风速
    WIND_DIRECTION, // 风向
    PRESSURE,       // 气压
    VISIBILITY,     // 能见度
    FEELS_LIKE      // 体感温度
}

/**
 * 天气指标图标组件
 *
 * 根据指标类型显示对应的 Material Icon
 *
 * @param type 指标类型
 * @param modifier 修饰符
 * @param tint 图标颜色（默认白色）
 */
@Composable
fun WeatherMetricIcon(
    type: WeatherMetricType,
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    val icon: ImageVector = when (type) {
        WeatherMetricType.HUMIDITY -> Icons.Filled.WaterDrop        // 水滴 - 湿度
        WeatherMetricType.WIND_SPEED -> Icons.Filled.Air            // 风 - 风速
        WeatherMetricType.WIND_DIRECTION -> Icons.Filled.Explore    // 指南针 - 风向
        WeatherMetricType.PRESSURE -> Icons.Filled.Compress         // 压缩 - 气压
        WeatherMetricType.VISIBILITY -> Icons.Filled.Visibility     // 眼睛 - 能见度
        WeatherMetricType.FEELS_LIKE -> Icons.Filled.Thermostat    // 温度计 - 体感温度
    }

    Icon(
        imageVector = icon,
        contentDescription = type.name,
        modifier = modifier,
        tint = tint
    )
}
