package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.TallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 高版天气瓷砖 (4×2)
 *
 * 特性：
 * - 垂直布局展示多天天气预报
 * - 使用 WeatherForecast 预设
 * - 适合展示一周天气趋势
 *
 * @param forecasts 天气预报列表（日期, 图标, 温度）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Weather4x2Tile(
    forecasts: List<Triple<String, String, String>>,
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.tall(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        TallTilePresets.WeatherForecast(forecasts = forecasts)
    }
}
