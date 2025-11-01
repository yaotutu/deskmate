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
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.LocalBaseCellSize
import top.yaotutu.deskmate.presentation.component.base.LocalDynamicGap
import top.yaotutu.deskmate.presentation.component.base.Tile
import top.yaotutu.deskmate.presentation.component.base.TileSize
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 联系人瓷砖 (1×1 或 2×2)
 *
 * @param name 联系人姓名
 * @param avatar 头像（暂时用首字母代替）
 * @param size 瓷砖尺寸
 * @param backgroundColor 背景颜色
 */
@Composable
fun ContactTile(
    name: String = "联系人",
    avatar: String = name.take(1),
    size: TileSize = TileSize.SMALL,
    backgroundColor: Color = MetroTileColors.Contact,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = size,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (size == TileSize.SMALL) 4.dp else 8.dp)
            ) {
                // 头像圆形
                Box(
                    modifier = Modifier
                        .size(if (size == TileSize.SMALL) 32.dp else 48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = avatar,
                        fontSize = if (size == TileSize.SMALL) 18.sp else 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // 姓名（仅在2×2及以上显示）
                if (size != TileSize.SMALL) {
                    Text(
                        text = name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }
        }
    }
}
