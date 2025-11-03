package top.yaotutu.deskmate.presentation.component.tiles.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.LargeTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 大型天气瓷砖 (4×4)
 *
 * 特性：
 * - 展示完整的天气仪表盘
 * - 包含温度、湿度、风速等多项指标
 * - 使用 Dashboard 预设获得网格布局
 *
 * @param metrics 天气指标列表（标签, 数值, 单位）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Weather4x4Tile(
    metrics: List<Triple<String, String, String>>,
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.large(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        LargeTilePresets.Dashboard(
            title = "天气概况",
            metrics = metrics
        )
    }
}
