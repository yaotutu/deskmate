package top.yaotutu.deskmate.presentation.component.tiles.special

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 音乐瓷砖 (2×2)
 *
 * 特性：
 * - 显示播放状态图标
 * - 歌曲名和艺术家信息
 * - 使用 MediumTilePresets.IconTitleSubtitle 预设
 *
 * @param songName 歌曲名（默认 "歌曲"）
 * @param artist 艺术家（默认 "艺术家"）
 * @param isPlaying 是否正在播放（默认 false）
 * @param backgroundColor 背景颜色（默认 Metro 橙色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun MusicTile(
    songName: String = "歌曲",
    artist: String = "艺术家",
    isPlaying: Boolean = false,
    backgroundColor: Color = MetroTileColors.Music,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        with(MediumTilePresets) {
            IconTitleSubtitle(
                icon = if (isPlaying) "⏸" else "▶",
                title = songName,
                subtitle = artist
            )
        }
    }
}
