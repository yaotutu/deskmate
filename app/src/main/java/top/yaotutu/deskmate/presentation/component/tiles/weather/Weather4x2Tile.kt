package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.data.model.DailyForecast
import top.yaotutu.deskmate.data.model.WeatherIconMapper
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.WideTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 详细版天气瓷砖 (2×4) - 周视图预报
 *
 * 特性：
 * - 横向展示多天天气预报
 * - 使用 WideTilePresets.HorizontalStats
 * - 显示温度、图标、日期
 *
 * @param forecasts 天气预报列表（最多显示4天）
 * @param currentTemp 当前温度
 * @param currentCondition 当前天气状况
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Weather4x2Tile(
    forecasts: List<DailyForecast>,
    currentTemp: Int = 22,
    currentCondition: String = "晴",
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        // 转换预报数据为Timeline格式（时间/日期 + 天气描述）
        val timelineItems = forecasts.take(3).map { forecast ->
            val icon = WeatherIconMapper.getEmoji(forecast.dayIconCode)
            val date = forecast.date.substring(5)  // 提取 MM-dd
            date to "$icon ${forecast.maxTemp}°/${forecast.minTemp}°"
        }

        with(WideTilePresets) {
            if (timelineItems.isNotEmpty()) {
                Timeline(
                    items = timelineItems
                )
            } else {
                // 无预报数据时显示当前天气
                IconTextSide(
                    icon = "🌤️",
                    title = currentCondition,
                    subtitle = "${currentTemp}°C"
                )
            }
        }
    }
}
