package top.yaotutu.deskmate.presentation.component.tiles.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.data.model.TodoItem
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography
import top.yaotutu.deskmate.presentation.theme.MetroSpacing
import top.yaotutu.deskmate.presentation.theme.MetroPadding

/**
 * 高版待办瓷砖 (2×4)
 *
 * 特性：
 * - 纵向展示待办列表
 * - 简单测试版本，直接渲染内容
 * - 适合展示待办事项清单
 *
 * @param todoItems 待办事项列表
 * @param backgroundColor 背景颜色（默认 Metro 紫色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Todo4x2Tile(
    todoItems: List<TodoItem>,
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.tall(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        single {
            // 简化测试：直接填充整个容器
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MetroPadding.medium()),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(MetroSpacing.large())
                ) {
                    Text(
                        text = "待办事项 (2×4 测试)",
                        fontSize = MetroTypography.bodyLarge(),
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    todoItems.take(5).forEach { item ->
                        Text(
                            text = "• ${item.title}",
                            fontSize = MetroTypography.bodyMedium(),
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "共 ${todoItems.size} 项",
                        fontSize = MetroTypography.bodySmall(),
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}
