package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 标准天气瓷砖 (2×2)
 *
 * 特性：
 * - 显示当前温度和天气状况
 * - 使用 Counter 预设获得数字滚动动画效果
 * - 适合温度变化展示
 *
 * @param temperature 当前温度（如 "25"）
 * @param condition 天气状况（如 "晴朗"）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun WeatherStandardTile(
    temperature: Int,
    condition: String,
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
            unit = "°",
            label = condition,
            valueSize = 80.sp,
            unitSize = 40.sp,
            labelSize = 18.sp
        )
    }
}