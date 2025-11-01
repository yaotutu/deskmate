package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.WideTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 详细天气瓷砖 (4×2)
 *
 * 特性：
 * - 显示完整的天气信息（温度、湿度、风速等）
 * - 使用 MediaPlayer 预设获得横向滑动动画效果
 * - 适合展示天气详情和预报信息
 *
 * @param icon 天气图标（如 "☀️", "⛅", "🌧️"）
 * @param title 主要天气信息（如 "晴朗 25°"）
 * @param details 详细信息（如 "湿度 65% 风速 12km/h"）
 * @param forecast 预报信息（如 "明日最高 28°"）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun WeatherDetailedTile(
    icon: String,
    title: String,
    details: String,
    forecast: String = "",
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        WideTilePresets.MediaPlayer(
            icon = icon,
            title = title,
            artist = details,
            duration = forecast
        )
    }
}