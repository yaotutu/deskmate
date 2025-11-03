package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.CompactTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 紧凑天气瓷砖 (1×2)
 *
 * 特性：
 * - 显示温度和天气状况
 * - 紧凑横向布局
 * - 适合状态栏或紧凑区域
 *
 * @param temperature 当前温度（如 22）
 * @param condition 天气状况（如 "晴朗"）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Weather1x2Tile(
    temperature: Int,
    condition: String,
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(2, 1, backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        CompactTilePresets.ProgressBar(
            label = condition,
            progress = "${temperature}°"
        )
    }
}
