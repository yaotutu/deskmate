package top.yaotutu.deskmate.presentation.ui.component.layout

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.data.model.TileConfig
import top.yaotutu.deskmate.presentation.ui.component.base.TileGrid

/**
 * 垂直优先布局引擎
 *
 * 核心理念：
 * - 瓷砖从上到下排列（垂直优先）
 * - 每列宽度 = 该列中最宽瓷砖的宽度
 * - 列高度达到屏幕高度后自动换列
 * - 支持横向滚动查看多列
 *
 * @author Deskmate Team
 */

/**
 * 列数据类 - 表示一列中的瓷砖
 */
data class ColumnData(
    val tiles: List<TileConfig> = emptyList(),
    val totalHeight: Dp = 0.dp,
    val maxWidth: Int = 0  // 该列中最宽瓷砖的列数
)

/**
 * 垂直优先布局算法 - 将瓷砖列表分配到多列
 *
 * @param tiles 瓷砖配置列表
 * @param maxHeight 每列的最大高度（通常为屏幕高度）
 * @param baseCellSize 基础格子尺寸
 * @param dynamicGap 动态间距
 * @return 分列后的数据列表
 */
fun calculateVerticalPriorityLayout(
    tiles: List<TileConfig>,
    maxHeight: Dp,
    baseCellSize: Dp,
    dynamicGap: Dp
): List<ColumnData> {
    val columns = mutableListOf<ColumnData>()
    var currentColumn = ColumnData()

    tiles.forEach { tile ->
        // 计算瓷砖高度
        val tileHeight = TileGrid.calculateTileHeight(
            baseCellSize = baseCellSize,
            gridRows = tile.rows,
            dynamicGap = dynamicGap
        )

        // 计算添加当前瓷砖后的总高度
        // 如果不是第一个瓷砖，需要加上间距
        val heightIncrement = if (currentColumn.tiles.isEmpty()) {
            tileHeight
        } else {
            tileHeight + dynamicGap
        }

        val newTotalHeight = currentColumn.totalHeight + heightIncrement

        // 判断是否需要换列
        // 规则：只检查高度，列宽允许动态增长（取该列中最宽瓷砖的宽度）
        val needNewColumn = currentColumn.tiles.isNotEmpty() &&
                           newTotalHeight > maxHeight

        if (needNewColumn) {
            // 当前列高度已满，保存当前列并开始新列
            columns.add(currentColumn)
            currentColumn = ColumnData(
                tiles = listOf(tile),
                totalHeight = tileHeight,
                maxWidth = tile.columns
            )
        } else {
            // 添加到当前列
            currentColumn = currentColumn.copy(
                tiles = currentColumn.tiles + tile,
                totalHeight = newTotalHeight,
                maxWidth = maxOf(currentColumn.maxWidth, tile.columns)
            )
        }
    }

    // 添加最后一列
    if (currentColumn.tiles.isNotEmpty()) {
        columns.add(currentColumn)
    }

    return columns
}

/**
 * 垂直优先布局容器 - Composable 实现
 *
 * 自动将瓷砖列表按垂直优先方式排列，支持横向滚动
 *
 * @param tiles 瓷砖配置列表
 * @param baseCellSize 基础格子尺寸
 * @param dynamicGap 动态间距
 * @param maxHeight 每列的最大高度
 * @param modifier 修饰符
 * @param tileContent 瓷砖内容渲染函数
 */
@Composable
fun VerticalPriorityLayout(
    tiles: List<TileConfig>,
    baseCellSize: Dp,
    dynamicGap: Dp,
    maxHeight: Dp,
    modifier: Modifier = Modifier,
    tileContent: @Composable (TileConfig, Int) -> Unit  // (config, index) -> Unit
) {
    // 计算分列布局
    val columns = calculateVerticalPriorityLayout(
        tiles = tiles,
        maxHeight = maxHeight,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap
    )

    // 横向滚动容器
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(dynamicGap)
    ) {
        // 左边距
        Spacer(modifier = Modifier.width(dynamicGap))

        // 遍历每一列
        var tileGlobalIndex = 0
        columns.forEach { columnData ->
            // 单列垂直布局
            Column(
                modifier = Modifier.width(
                    TileGrid.calculateTileWidth(
                        baseCellSize = baseCellSize,
                        gridColumns = columnData.maxWidth,
                        dynamicGap = dynamicGap
                    )
                ),
                verticalArrangement = Arrangement.spacedBy(dynamicGap)
            ) {
                // 遍历该列中的瓷砖
                columnData.tiles.forEach { tile ->
                    tileContent(tile, tileGlobalIndex)
                    tileGlobalIndex++
                }
            }
        }

        // 右边距
        Spacer(modifier = Modifier.width(dynamicGap))
    }
}
