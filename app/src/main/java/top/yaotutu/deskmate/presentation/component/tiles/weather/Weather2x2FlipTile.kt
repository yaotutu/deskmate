package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.data.model.WeatherData
import top.yaotutu.deskmate.presentation.component.base.AnimationType
import top.yaotutu.deskmate.presentation.component.common.QWeatherIcon
import top.yaotutu.deskmate.presentation.component.common.WeatherMetricIcon
import top.yaotutu.deskmate.presentation.component.common.WeatherMetricType
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.FlipContent
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography
import top.yaotutu.deskmate.presentation.theme.MetroPadding

/**
 * 翻转天气瓷砖 (2×2) - 西安专属
 *
 * 特性：
 * - 自动翻转动画（每6秒翻转一次）
 * - 正面：大号天气图标 + 温度 + 天气状况
 * - 背面：详细天气信息（6项指标：湿度、风速、气压、能见度、体感、风向）
 * - 完全自定义布局，不使用preset
 * - 适配西安（城市ID: 101110101）
 * - 字体与Metro预设保持一致
 *
 * @param weatherData 天气数据（从ViewModel的uiState获取）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Weather2x2FlipTile(
    weatherData: WeatherData,
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.FLIP),  // ⭐ 关键：指定FLIP动画类型
        onClick = onClick,
        modifier = modifier
    ) {
        FlipContent(  // ⭐ 使用FlipContent而不是FlipTileAnimation
            front = {
                // 正面：当前天气概览
                WeatherFrontContent(weatherData)
            },
            back = {
                // 背面：详细天气信息
                WeatherBackContent(weatherData)
            }
        )
    }
}

/**
 * 正面内容：天气图标 + 温度 + 状况
 * 字体与Metro预设保持一致：72sp主标题 + 18sp副标题
 */
@Composable
private fun WeatherFrontContent(weatherData: WeatherData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MetroPadding.medium()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 天气图标（使用和风天气官方SVG图标）
        QWeatherIcon(
            iconCode = weatherData.iconCode,
            size = 72.dp,
            contentDescription = weatherData.condition,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 温度（主要信息 - 使用72sp与preset一致）
        Text(
            text = "${weatherData.temperature}°",
            fontSize = MetroTypography.displayLarge(),
            fontWeight = FontWeight.Thin,  // 使用Thin保持一致
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // 天气状况（副标题 - 使用18sp与preset一致）
        Text(
            text = weatherData.condition,
            fontSize = MetroTypography.bodyMedium(),
            fontWeight = FontWeight.Light,  // 使用Light保持一致
            color = Color.White
        )
    }
}

/**
 * 背面内容：详细天气信息（3×2网格布局）
 * 字体与Metro预设保持一致
 */
@Composable
private fun WeatherBackContent(weatherData: WeatherData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MetroPadding.medium()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 标题（使用18sp与preset一致）
        Text(
            text = "详细信息",
            fontSize = MetroTypography.bodyLarge(),
            fontWeight = FontWeight.Light,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // 第一行：湿度 + 风速
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailItem(
                iconType = WeatherMetricType.HUMIDITY,
                label = "湿度",
                value = "${weatherData.humidity}%"
            )
            DetailItem(
                iconType = WeatherMetricType.WIND_SPEED,
                label = "风速",
                value = "${weatherData.windSpeed}"
            )
        }

        // 第二行：风向 + 体感
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailItem(
                iconType = WeatherMetricType.WIND_DIRECTION,
                label = "风向",
                value = weatherData.windDirection
            )
            DetailItem(
                iconType = WeatherMetricType.FEELS_LIKE,
                label = "体感",
                value = "${weatherData.feelsLike}°"
            )
        }

        // 第三行：气压 + 能见度
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailItem(
                iconType = WeatherMetricType.PRESSURE,
                label = "气压",
                value = "${weatherData.pressure}"
            )
            DetailItem(
                iconType = WeatherMetricType.VISIBILITY,
                label = "能见度",
                value = "${weatherData.visibility}km"
            )
        }
    }
}

/**
 * 详细信息项（Material Icon + 数值 + 标签）
 * 字体与Metro预设保持一致
 */
@Composable
private fun DetailItem(
    iconType: WeatherMetricType,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        // Material Icon 图标
        WeatherMetricIcon(
            type = iconType,
            modifier = Modifier
                .size(28.dp)
                .padding(bottom = 4.dp),
            tint = Color.White
        )

        // 数值（主要）
        Text(
            text = value,
            fontSize = MetroTypography.bodyMedium(),
            fontWeight = FontWeight.Thin,
            color = Color.White,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        // 标签（次要）
        Text(
            text = label,
            fontSize = MetroTypography.labelSmall(),
            fontWeight = FontWeight.Light,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}
