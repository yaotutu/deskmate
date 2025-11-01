package top.yaotutu.deskmate.presentation.component.tiles.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.CompactTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 紧凑待办瓷砖 (2×1)
 *
 * 特性：
 * - 显示任务进度和完成状态
 * - 使用 ProgressBar 预设获得淡入淡出动画效果
 * - 适合展示任务完成进度
 *
 * @param label 任务标签（如 "今日任务"）
 * @param progress 进度信息（如 "3/5 完成" 或 "60%"）
 * @param backgroundColor 背景颜色（默认 Metro 紫色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun TodoCompactTile(
    label: String,
    progress: String,
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        CompactTilePresets.ProgressBar(
            label = label,
            progress = progress
        )
    }
}