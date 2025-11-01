package top.yaotutu.deskmate.presentation.component.tiles.special

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.presets.MediumTilePresets
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 照片瓷砖 (2×2)
 *
 * 特性：
 * - 显示照片图标和标题
 * - 使用 MediumTilePresets.IconTitle 预设
 *
 * @param imageUrl 图片URL（暂时用占位符代替）
 * @param caption 标题（默认 "照片"）
 * @param backgroundColor 背景颜色（默认 Metro 品红色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun PhotoTile(
    imageUrl: String = "",
    caption: String = "照片",
    backgroundColor: Color = MetroTileColors.Photo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        MediumTilePresets.IconTitle(
            icon = "📷",
            title = caption
        )
    }
}
