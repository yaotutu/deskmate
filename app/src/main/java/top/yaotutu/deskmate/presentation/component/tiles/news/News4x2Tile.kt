package top.yaotutu.deskmate.presentation.component.tiles.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.WideTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 详细新闻瓷砖 (2×4) - 使用 BaseTile 架构
 *
 * 特性：
 * - 支持自动 SLIDE 动画（通过 TileSpec.wideMedium）
 * - 横向展示新闻详细信息
 * - 使用 IconTextSide 预设展示图标、标题和摘要
 * - 适合展示完整新闻内容
 *
 * @param icon 新闻图标（如 "📰"）
 * @param title 新闻标题
 * @param summary 新闻摘要
 * @param time 发布时间（如 "2小时前"）
 * @param backgroundColor 背景颜色（默认 Metro 红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun News4x2Tile(
    icon: String = "📰",
    title: String,
    summary: String,
    time: String = "",
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        with(WideTilePresets) {
            IconTextSide(
                icon = icon,
                title = title,
                subtitle = if (time.isNotEmpty()) "$summary · $time" else summary
            )
        }
    }
}
