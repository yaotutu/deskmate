package top.yaotutu.deskmate.data.model

import kotlinx.serialization.Serializable

/**
 * 布局配置数据类（网格区域命名版本）
 *
 * 采用 CSS Grid Template Areas 风格的布局配置
 *
 * 示例配置：
 * ```json
 * {
 *   "columns": 6,
 *   "rows": 4,
 *   "areas": [
 *     "N N N N W W",
 *     "N N N N W W",
 *     "T T C C K K",
 *     "T T C C . ."
 *   ],
 *   "tiles": {
 *     "N": {"type": "news", "variant": "standard"},
 *     "W": {"type": "weather", "variant": "standard"},
 *     "C": {"type": "calendar", "variant": "standard"},
 *     "T": {"type": "todo", "variant": "list"},
 *     "K": {"type": "clock", "variant": "2x2"}
 *   }
 * }
 * ```
 *
 * @property columns 网格总列数（通常为 6）
 * @property rows 网格总行数（通常为 4）
 * @property areas 网格区域定义（每行是一个字符串，用空格分隔列）
 * @property tiles 瓷砖定义映射表（ID -> TileDefinition）
 *
 * @author Deskmate Team
 */
@Serializable
data class LayoutConfig(
    val columns: Int = 6,
    val rows: Int = 4,
    val areas: List<String>,
    val tiles: Map<String, TileDefinition>
) {
    init {
        require(columns > 0) { "columns 必须 > 0，当前值: $columns" }
        require(rows > 0) { "rows 必须 > 0，当前值: $rows" }
        require(areas.size == rows) { "areas 行数必须等于 rows，期望: $rows，实际: ${areas.size}" }

        // 验证每行的列数
        areas.forEachIndexed { index, row ->
            val cells = row.trim().split(Regex("\\s+"))
            require(cells.size == columns) {
                "areas[${index}] 列数必须等于 columns，期望: $columns，实际: ${cells.size}"
            }
        }

        // 验证 tiles 定义完整性（除了空白单元格 "."）
        val usedIds = areas.flatMap { it.trim().split(Regex("\\s+")) }
            .filter { it != "." }
            .distinct()

        usedIds.forEach { id ->
            require(tiles.containsKey(id)) {
                "areas 中使用的 ID '$id' 在 tiles 中未定义"
            }
        }
    }

    /**
     * 获取网格中指定位置的瓷砖 ID
     *
     * @param x 列位置（0-based）
     * @param y 行位置（0-based）
     * @return 瓷砖 ID，如果是空白单元格则返回 "."
     */
    fun getTileIdAt(x: Int, y: Int): String {
        require(x in 0 until columns) { "x 必须在 [0, $columns) 范围内，当前值: $x" }
        require(y in 0 until rows) { "y 必须在 [0, $rows) 范围内，当前值: $y" }

        val cells = areas[y].trim().split(Regex("\\s+"))
        return cells[x]
    }

    /**
     * 检查配置是否为空（没有任何瓷砖定义）
     */
    fun isEmpty(): Boolean = tiles.isEmpty()
}
