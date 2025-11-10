package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.data.model.WeatherIconMapper
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 标准天气瓷砖 (2×2)
 *
 * 特性：
 * - 大数字温度显示，更加醒目
 * - 支持数字滚动动画
 * - 显示位置或天气状况
 *
 * @param temperature 当前温度（如 22）
 * @param condition 天气状况（如 "晴朗"）
 * @param iconCode 天气图标代码（用于显示Emoji）
 * @param location 位置名称（如 "北京"）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Weather2x2Tile(
    temperature: Int,
    condition: String,
    iconCode: String = "100",
    location: String = "",
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        MediumTilePresets.Counter(
            value = temperature.toString(),
            unit = "°C",
            label = location.ifEmpty { condition }
        )
    }
}
