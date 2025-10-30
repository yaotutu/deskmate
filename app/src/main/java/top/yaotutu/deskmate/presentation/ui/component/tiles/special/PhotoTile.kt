package top.yaotutu.deskmate.presentation.ui.component.tiles.special

import androidx.compose.foundation.layout.*
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
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 照片瓷砖 (2×2)
 *
 * @param imageUrl 图片URL（暂时用占位符代替）
 * @param caption 标题
 * @param backgroundColor 背景颜色
 */
@Composable
fun PhotoTile(
    imageUrl: String = "",
    caption: String = "照片",
    backgroundColor: Color = MetroTileColors.Photo,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.MEDIUM,
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📷",
                    fontSize = 48.sp
                )
                Text(
                    text = caption,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
        }
    }
}
