package top.yaotutu.deskmate.presentation.component.layout

import android.util.Log
import top.yaotutu.deskmate.data.model.AreaPosition
import top.yaotutu.deskmate.data.model.LayoutConfig
import top.yaotutu.deskmate.data.model.TileDefinition

/**
 * 网格区域解析器
 *
 * 负责将 LayoutConfig 中的 areas 数组解析为 AreaPosition 列表
 *
 * 核心算法：
 * 1. 构建 2D 网格
 * 2. 扫描识别连续区域
 * 3. 计算每个区域的边界框
 * 4. 验证区域是否为矩形
 *
 * @author Deskmate Team
 */
object GridAreaParser {

    private const val TAG = "GridAreaParser"

    /**
     * 解析错误类型
     */
    sealed class ParseError(val message: String) {
        data class InvalidFormat(val details: String) : ParseError("网格格式错误: $details")
        data class NotRectangular(val id: String) : ParseError("区域 '$id' 不是矩形")
        data class TileNotDefined(val id: String) : ParseError("瓷砖 '$id' 未定义")
        data class SizeMismatch(val id: String, val expected: String, val actual: String) :
            ParseError("瓷砖 '$id' 尺寸不匹配: 期望 $expected, 实际 $actual")
    }

    /**
     * 解析结果
     */
    sealed class ParseResult {
        data class Success(val positions: List<AreaPosition>) : ParseResult()
        data class Failure(val errors: List<ParseError>) : ParseResult()
    }

    /**
     * 解析布局配置
     *
     * @param config 布局配置
     * @return 解析结果（成功返回 AreaPosition 列表，失败返回错误列表）
     */
    fun parse(config: LayoutConfig): ParseResult {
        Log.d(TAG, "开始解析布局配置: ${config.columns}×${config.rows}")

        val errors = mutableListOf<ParseError>()

        try {
            // Step 1: 构建 2D 网格
            val grid = buildGrid(config)

            // Step 2: 识别所有唯一的瓷砖 ID（排除空白单元格）
            val tileIds = grid.flatten().filter { it != "." }.distinct()
            Log.d(TAG, "找到 ${tileIds.size} 个唯一瓷砖 ID: $tileIds")

            // Step 3: 为每个瓷砖 ID 计算区域位置
            val positions = mutableListOf<AreaPosition>()

            tileIds.forEach { id ->
                val tileDefinition = config.tiles[id]
                if (tileDefinition == null) {
                    errors.add(ParseError.TileNotDefined(id))
                    return@forEach
                }

                // 查找该 ID 的边界框
                val boundingBox = findBoundingBox(grid, id)
                if (boundingBox == null) {
                    errors.add(ParseError.InvalidFormat("无法找到 ID '$id' 的边界框"))
                    return@forEach
                }

                // 验证区域是否为矩形
                if (!isRectangular(grid, id, boundingBox)) {
                    errors.add(ParseError.NotRectangular(id))
                    return@forEach
                }

                // 创建 AreaPosition
                val (x, y, width, height) = boundingBox
                val position = AreaPosition(
                    id = id,
                    x = x,
                    y = y,
                    width = width,
                    height = height,
                    tileDefinition = tileDefinition
                )

                positions.add(position)
                Log.d(TAG, "解析瓷砖: $id -> ($x, $y) ${width}×${height}")
            }

            return if (errors.isEmpty()) {
                Log.d(TAG, "解析成功: ${positions.size} 个瓷砖")
                ParseResult.Success(positions)
            } else {
                Log.e(TAG, "解析失败: ${errors.size} 个错误")
                ParseResult.Failure(errors)
            }

        } catch (e: Exception) {
            Log.e(TAG, "解析异常", e)
            errors.add(ParseError.InvalidFormat(e.message ?: "未知错误"))
            return ParseResult.Failure(errors)
        }
    }

    /**
     * 构建 2D 网格
     *
     * @param config 布局配置
     * @return 2D 字符串数组（grid[y][x]）
     */
    private fun buildGrid(config: LayoutConfig): List<List<String>> {
        return config.areas.map { row ->
            row.trim().split(Regex("\\s+"))
        }
    }

    /**
     * 查找指定 ID 的边界框
     *
     * @param grid 2D 网格
     * @param id 瓷砖 ID
     * @return (x, y, width, height) 或 null
     */
    private fun findBoundingBox(grid: List<List<String>>, id: String): BoundingBox? {
        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE

        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                if (cell == id) {
                    minX = minOf(minX, x)
                    minY = minOf(minY, y)
                    maxX = maxOf(maxX, x)
                    maxY = maxOf(maxY, y)
                }
            }
        }

        return if (minX <= maxX && minY <= maxY) {
            BoundingBox(minX, minY, maxX - minX + 1, maxY - minY + 1)
        } else {
            null
        }
    }

    /**
     * 验证指定区域是否为矩形
     *
     * @param grid 2D 网格
     * @param id 瓷砖 ID
     * @param boundingBox 边界框
     * @return true 如果是完整的矩形
     */
    private fun isRectangular(
        grid: List<List<String>>,
        id: String,
        boundingBox: BoundingBox
    ): Boolean {
        val (x, y, width, height) = boundingBox

        // 检查边界框内的每个单元格是否都是该 ID
        for (row in y until (y + height)) {
            for (col in x until (x + width)) {
                if (grid[row][col] != id) {
                    Log.w(TAG, "区域 '$id' 不是矩形: ($col, $row) 处的值是 '${grid[row][col]}'")
                    return false
                }
            }
        }

        return true
    }

    /**
     * 边界框数据类
     */
    private data class BoundingBox(
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int
    )
}
