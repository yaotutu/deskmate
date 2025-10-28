package top.yaotutu.deskmate.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 瓷砖尺寸定义（基于格子倍数）
 *
 * 新的尺寸系统基于相对格子数量，而不是固定尺寸
 * 例如：MEDIUM(2, 2) 表示占用 2列×2行 的格子
 *
 * @param columns 占用的列数
 * @param rows 占用的行数
 */
enum class TileSize(val columns: Int, val rows: Int) {
    /** 小方块：1×1 格子 */
    SMALL(1, 1),

    /** 中方块：2×2 格子 */
    MEDIUM(2, 2),

    /** 短横条：3×1 格子 */
    WIDE_SHORT(3, 1),

    /** 中横条：4×2 格子（时钟瓷砖常用） */
    WIDE_MEDIUM(4, 2),

    /** 竖长条：2×3 格子 */
    TALL(2, 3),

    /** 大方块：3×3 格子 */
    LARGE(3, 3),

    /** 超大方块：4×4 格子 */
    EXTRA_LARGE(4, 4);

    companion object {
        /**
         * 创建全宽瓷砖（占满一行）
         * 由于列数是动态的，无法用枚举表示，需要运行时计算
         */
        fun fullWidth(columns: Int): TileSize {
            // 返回一个临时的 TileSize，仅用于全宽场景
            return WIDE_MEDIUM.copy(columns = columns, rows = 1)
        }

        private fun TileSize.copy(columns: Int = this.columns, rows: Int = this.rows): TileSize {
            // 注意：这是一个简化实现，实际中可能需要更复杂的处理
            return when {
                columns == 1 && rows == 1 -> SMALL
                columns == 2 && rows == 2 -> MEDIUM
                columns == 3 && rows == 1 -> WIDE_SHORT
                columns == 4 && rows == 2 -> WIDE_MEDIUM
                columns == 2 && rows == 3 -> TALL
                columns == 3 && rows == 3 -> LARGE
                columns == 4 && rows == 4 -> EXTRA_LARGE
                else -> MEDIUM // 默认回退
            }
        }
    }
}

/**
 * 统一的瓷砖容器组件（新版）
 *
 * 基于新的网格系统，使用 TileSize 枚举定义尺寸
 *
 * @param size 瓷砖尺寸（TileSize 枚举）
 * @param backgroundColor 背景颜色
 * @param baseCellSize 基础格子尺寸
 * @param dynamicGap 动态间距
 * @param onClick 点击回调
 * @param clickEffect 点击动效类型
 * @param contentPadding 内容内边距（null 时根据瓷砖尺寸自动选择）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun Tile(
    size: TileSize,
    backgroundColor: Color,
    baseCellSize: Dp,
    dynamicGap: Dp,
    onClick: () -> Unit = {},
    clickEffect: TileClickEffect = TileClickEffect.PRESS_SCALE,
    contentPadding: Dp? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // 计算瓷砖实际尺寸
    val tileWidth = TileGrid.calculateTileWidth(baseCellSize, size.columns, dynamicGap)
    val tileHeight = TileGrid.calculateTileHeight(baseCellSize, size.rows, dynamicGap)

    // 根据瓷砖尺寸自动选择内边距
    val padding = contentPadding ?: when (size) {
        TileSize.SMALL, TileSize.WIDE_SHORT -> 12.dp           // 小瓷砖：紧凑内边距
        TileSize.MEDIUM, TileSize.WIDE_MEDIUM, TileSize.TALL -> 16.dp  // 中等瓷砖：标准内边距
        TileSize.LARGE, TileSize.EXTRA_LARGE -> 20.dp         // 大瓷砖：宽松内边距
    }

    TileWithInteraction(
        effect = clickEffect,
        onTap = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .width(tileWidth)
                .height(tileHeight)
                .clip(RectangleShape)  // Metro 风格：直角瓷砖
                .background(backgroundColor)
                .padding(padding)
        ) {
            content()
        }
    }
}

/**
 * 瓷砖容器 - 支持任意尺寸（用于变体系统）
 *
 * 与上面的 Tile 函数类似，但支持直接传递列数和行数
 *
 * @param columns 瓷砖占用的列数
 * @param rows 瓷砖占用的行数
 * @param backgroundColor 背景颜色
 * @param baseCellSize 基础格子尺寸
 * @param dynamicGap 动态间距
 * @param onClick 点击回调
 * @param clickEffect 点击效果
 * @param contentPadding 内容内边距（null 时根据瓷砖尺寸自动选择）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun Tile(
    columns: Int,
    rows: Int,
    backgroundColor: Color,
    baseCellSize: Dp,
    dynamicGap: Dp,
    onClick: () -> Unit = {},
    clickEffect: TileClickEffect = TileClickEffect.PRESS_SCALE,
    contentPadding: Dp? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // 计算瓷砖实际尺寸
    val tileWidth = TileGrid.calculateTileWidth(baseCellSize, columns, dynamicGap)
    val tileHeight = TileGrid.calculateTileHeight(baseCellSize, rows, dynamicGap)

    // 根据瓷砖尺寸自动选择内边距
    val totalCells = columns * rows
    val padding = contentPadding ?: when {
        totalCells <= 2 -> 12.dp      // 小瓷砖：紧凑内边距
        totalCells <= 8 -> 16.dp      // 中等瓷砖：标准内边距
        else -> 20.dp                 // 大瓷砖：宽松内边距
    }

    TileWithInteraction(
        effect = clickEffect,
        onTap = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .width(tileWidth)
                .height(tileHeight)
                .clip(RectangleShape)  // Metro 风格：直角瓷砖
                .background(backgroundColor)
                .padding(padding)
        ) {
            content()
        }
    }
}
