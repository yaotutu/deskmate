package top.yaotutu.deskmate.presentation.component.tiles.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 标准待办瓷砖 (2×2)
 *
 * 特性：
 * - 显示待办数量和状态
 * - 使用 Counter 预设获得数字滚动动画
 * - 适合展示任务完成进度
 *
 * @param completedCount 已完成数量
 * @param totalCount 总任务数
 * @param backgroundColor 背景颜色（默认 Metro 紫色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Todo2x2Tile(
    completedCount: Int,
    totalCount: Int,
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        with(MediumTilePresets) {
            Counter(
                value = completedCount.toString(),
                unit = "/$totalCount",
                label = "任务完成"
            )
        }
    }
}
