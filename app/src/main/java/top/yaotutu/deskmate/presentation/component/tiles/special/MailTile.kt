package top.yaotutu.deskmate.presentation.component.tiles.special

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.common.MetroBadge
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography

/**
 * 邮件瓷砖 (可变尺寸，默认 2×2) - 使用 BaseTile 架构
 *
 * 特性：
 * - 显示未读邮件数量
 * - 右上角角标提示（有未读时显示）
 * - 灰色 Metro 风格
 *
 * @param unreadCount 未读邮件数
 * @param columns 瓷砖列数（默认 2）
 * @param rows 瓷砖行数（默认 2）
 * @param backgroundColor 背景颜色
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun MailTile(
    unreadCount: Int = 0,
    columns: Int = 2,
    rows: Int = 2,
    backgroundColor: Color = MetroTileColors.Mail,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(columns, rows, backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // 主内容
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "邮件",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                if (unreadCount > 0) {
                    Text(
                        text = "$unreadCount 封未读",
                        fontSize = MetroTypography.bodyMedium(),
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "无新邮件",
                        fontSize = MetroTypography.bodyMedium(),
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }

            // 角标显示在右上角
            if (unreadCount > 0) {
                MetroBadge(
                    count = unreadCount,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                )
            }
        }
    }
}
