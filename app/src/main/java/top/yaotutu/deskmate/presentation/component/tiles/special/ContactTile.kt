package top.yaotutu.deskmate.presentation.component.tiles.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography

/**
 * 联系人瓷砖 (可变尺寸) - 使用 BaseTile 架构
 *
 * 特性：
 * - 圆形头像设计
 * - 响应式布局（根据尺寸自动调整头像和文字大小）
 * - 1×1 时仅显示头像，2×2 及以上显示头像+姓名
 *
 * @param name 联系人姓名
 * @param avatar 头像（暂时用首字母代替）
 * @param columns 瓷砖列数
 * @param rows 瓷砖行数
 * @param backgroundColor 背景颜色
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun ContactTile(
    name: String = "联系人",
    avatar: String = name.take(1),
    columns: Int = 1,
    rows: Int = 1,
    backgroundColor: Color = MetroTileColors.Contact,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isSmall = columns == 1 && rows == 1

    BaseTile(
        spec = TileSpec(columns, rows, backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (isSmall) 4.dp else 8.dp)
            ) {
                // 头像圆形
                Box(
                    modifier = Modifier
                        .size(if (isSmall) 32.dp else 48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = avatar,
                        fontSize = if (isSmall) MetroTypography.bodyMedium() else MetroTypography.bodyLarge(),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // 姓名（仅在2×2及以上显示）
                if (!isSmall) {
                    Text(
                        text = name,
                        fontSize = MetroTypography.bodySmall(),
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }
        }
    }
}
