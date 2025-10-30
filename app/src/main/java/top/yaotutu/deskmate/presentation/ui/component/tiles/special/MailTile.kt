package top.yaotutu.deskmate.presentation.ui.component.tiles.special

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
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.component.base.LocalBaseCellSize
import top.yaotutu.deskmate.presentation.ui.component.base.LocalDynamicGap
import top.yaotutu.deskmate.presentation.ui.component.base.Tile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSize
import top.yaotutu.deskmate.presentation.ui.component.enhancement.TileWithBadge
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 邮件瓷砖 (2×2)
 *
 * @param unreadCount 未读邮件数
 * @param latestSubject 最新邮件主题
 * @param backgroundColor 背景颜色
 */
@Composable
fun MailTile(
    unreadCount: Int = 0,
    latestSubject: String = "邮件",
    backgroundColor: Color = MetroTileColors.Mail,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    TileWithBadge(
        badgeCount = unreadCount,
        modifier = modifier
    ) {
        Tile(
            size = TileSize.MEDIUM,
            backgroundColor = backgroundColor,
            baseCellSize = baseCellSize,
            dynamicGap = dynamicGap
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "无新邮件",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
