package top.yaotutu.deskmate.presentation.component.tiles.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.TallTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 待办列表瓷砖 (2×4)
 *
 * 特性：
 * - 显示待办事项列表
 * - 使用 VerticalList 预设获得纵向滑动动画效果
 * - 适合展示多个待办任务
 *
 * @param items 待办事项列表
 * @param backgroundColor 背景颜色（默认 Metro 紫色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun TodoListTile(
    items: List<String>,
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.tall(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        TallTilePresets.VerticalList(
            items = items
        )
    }
}