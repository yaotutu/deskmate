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
 * 音乐瓷砖 (2×2)
 *
 * @param songName 歌曲名
 * @param artist 艺术家
 * @param isPlaying 是否正在播放
 * @param backgroundColor 背景颜色
 */
@Composable
fun MusicTile(
    songName: String = "歌曲",
    artist: String = "艺术家",
    isPlaying: Boolean = false,
    backgroundColor: Color = MetroTileColors.Music,
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
                    text = if (isPlaying) "⏸" else "▶",
                    fontSize = 48.sp,
                    color = Color.White
                )
                Text(
                    text = songName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = artist,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}
