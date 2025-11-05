package top.yaotutu.deskmate.presentation.component.layout

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.data.model.AreaPosition
import top.yaotutu.deskmate.data.model.LayoutConfig
import top.yaotutu.deskmate.data.model.TileConfig
import top.yaotutu.deskmate.presentation.component.base.TileGrid
import top.yaotutu.deskmate.presentation.component.tiles.common.ErrorTile
import top.yaotutu.deskmate.presentation.component.tiles.common.TileErrorType

/**
 * 网格区域布局引擎
 *
 * 采用绝对定位的方式渲染瓷砖，根据解析后的 AreaPosition 计算每个瓷砖的位置和尺寸
 *
 * 核心理念：
 * - 使用 Box + offset 实现绝对定位
 * - 根据 (x, y) 和网格参数计算偏移量
 * - 支持错误处理和降级显示
 *
 * @param config 布局配置
 * @param baseCellSize 基础单元格尺寸
 * @param dynamicGap 动态间距
 * @param modifier 修饰符
 * @param tileContent 瓷砖内容渲染函数（接收 TileConfig 和 index）
 *
 * @author Deskmate Team
 */
@Composable
fun GridAreaLayout(
    config: LayoutConfig,
    baseCellSize: Dp,
    dynamicGap: Dp,
    modifier: Modifier = Modifier,
    tileContent: @Composable (TileConfig, Int) -> Unit
) {
    val TAG = "GridAreaLayout"

    // 解析布局配置
    val parseResult = remember(config) {
        GridAreaParser.parse(config)
    }

    when (parseResult) {
        is GridAreaParser.ParseResult.Success -> {
            Log.d(TAG, "布局解析成功: ${parseResult.positions.size} 个瓷砖")

            // 计算内容尺寸（不包含外部 gap，由外层容器的 padding 负责）
            // 宽度 = baseCellSize * columns + gap * (columns - 1)
            val contentWidth = baseCellSize * config.columns + dynamicGap * (config.columns - 1)
            // 高度 = baseCellSize * rows + gap * (rows - 1)
            val contentHeight = baseCellSize * config.rows + dynamicGap * (config.rows - 1)

            // 渲染瓷砖
            Box(modifier = modifier.width(contentWidth).height(contentHeight)) {
                parseResult.positions.forEachIndexed { index, areaPosition ->
                    RenderTile(
                        areaPosition = areaPosition,
                        baseCellSize = baseCellSize,
                        dynamicGap = dynamicGap,
                        index = index,
                        tileContent = tileContent
                    )
                }
            }
        }

        is GridAreaParser.ParseResult.Failure -> {
            Log.e(TAG, "布局解析失败: ${parseResult.errors.size} 个错误")
            parseResult.errors.forEach { error ->
                Log.e(TAG, "  - ${error.message}")
            }

            // 显示错误瓷砖
            Box(modifier = modifier.fillMaxSize()) {
                ErrorTile(
                    columns = 2,
                    rows = 2,
                    errorType = TileErrorType.CONFIG_ERROR,
                    message = "布局配置解析失败",
                    details = mapOf(
                        "错误数量" to "${parseResult.errors.size}",
                        "第一个错误" to (parseResult.errors.firstOrNull()?.message ?: "未知")
                    )
                )
            }
        }
    }
}

/**
 * 渲染单个瓷砖
 *
 * @param areaPosition 区域位置信息
 * @param baseCellSize 基础单元格尺寸
 * @param dynamicGap 动态间距
 * @param index 瓷砖索引（用于动画）
 * @param tileContent 瓷砖内容渲染函数
 */
@Composable
private fun RenderTile(
    areaPosition: AreaPosition,
    baseCellSize: Dp,
    dynamicGap: Dp,
    index: Int,
    tileContent: @Composable (TileConfig, Int) -> Unit
) {
    // 计算瓷砖的偏移量
    // 公式: offset = baseCellSize * position + gap * position
    // 例如: 第 2 列（x=2）的偏移 = baseCellSize*2 + gap*2
    // 注：外层容器管理上下左右边距，瓷砖只关心内部间距
    val offsetX = baseCellSize * areaPosition.x + dynamicGap * areaPosition.x
    val offsetY = baseCellSize * areaPosition.y + dynamicGap * areaPosition.y

    // 计算瓷砖的尺寸
    val tileWidth = TileGrid.calculateTileWidth(
        baseCellSize = baseCellSize,
        gridColumns = areaPosition.width,
        dynamicGap = dynamicGap
    )
    val tileHeight = TileGrid.calculateTileHeight(
        baseCellSize = baseCellSize,
        gridRows = areaPosition.height,
        dynamicGap = dynamicGap
    )

    // 转换为 TileConfig 格式（兼容现有的 TileFactory）
    val tileConfig = TileConfig(
        type = areaPosition.tileDefinition.type,
        variant = areaPosition.tileDefinition.variant,
        columns = areaPosition.width,
        rows = areaPosition.height
    )

    // 使用 Box + offset 实现绝对定位
    Box(
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .width(tileWidth)
            .height(tileHeight)
    ) {
        tileContent(tileConfig, index)
    }
}

/**
 * 计算网格布局所需的总尺寸
 *
 * @param config 布局配置
 * @param baseCellSize 基础单元格尺寸
 * @param dynamicGap 动态间距
 * @return Pair(totalWidth, totalHeight)
 */
fun calculateGridSize(
    config: LayoutConfig,
    baseCellSize: Dp,
    dynamicGap: Dp
): Pair<Dp, Dp> {
    // 总宽度 = baseCellSize * columns + gap * (columns + 1)
    val totalWidth = baseCellSize * config.columns + dynamicGap * (config.columns + 1)

    // 总高度 = baseCellSize * rows + gap * (rows + 1)
    val totalHeight = baseCellSize * config.rows + dynamicGap * (config.rows + 1)

    return totalWidth to totalHeight
}
