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
 * - 显示完整天气信息（温度、状况、图标）
 * - 使用 Metro 标准尺寸
 * - 适合主屏幕展示
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
        MediumTilePresets.IconTitleSubtitle(
            icon = WeatherIconMapper.getEmoji(iconCode),
            title = "${temperature}°C",
            subtitle = location.ifEmpty { condition }
        )
    }
}
