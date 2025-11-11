package top.yaotutu.deskmate.presentation.component.tiles.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.CompactTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 紧凑新闻瓷砖 (1×2)
 *
 * 特性：
 * - 显示新闻标题（简短版）
 * - 紧凑横向布局
 * - 适合头条快速浏览
 *
 * @param title 新闻标题
 * @param time 发布时间（如 "2小时前"）
 * @param backgroundColor 背景颜色（默认 Metro 红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun News1x2Tile(
    title: String,
    time: String,
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(2, 1, backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        with(CompactTilePresets) {
            ProgressBar(
                label = title,
                progress = time
            )
        }
    }
}
