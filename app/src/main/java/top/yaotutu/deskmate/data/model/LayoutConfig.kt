package top.yaotutu.deskmate.data.model

import kotlinx.serialization.Serializable

/**
 * 布局配置数据类（网格区域命名版本）
 *
 * 采用 CSS Grid Template Areas 风格的布局配置
 *
 * **2025-01-06 重构**：
 * - 移除冗余的 columns 和 rows 字段，改为从 areas 自动推断
 * - 行数 = areas.size
 * - 列数 = areas 第一行的列数
 *
 * 示例配置：
 * ```json
 * {
 *   "areas": [
 *     "N N N N W W",
 *     "N N N N W W",
 *     "T T C C K K",
 *     "T T C C . ."
 *   ],
 *   "tiles": {
 *     "N": {"type": "news", "variant": "2x4"},
 *     "W": {"type": "weather", "variant": "2x2"},
 *     "C": {"type": "calendar", "variant": "2x2"},
 *     "T": {"type": "todo", "variant": "2x2"},
 *     "K": {"type": "clock", "variant": "2x2"}
 *   }
 * }
 * ```
 *
 * @property areas 网格区域定义（每行是一个字符串，用空格分隔列）
 * @property tiles 瓷砖定义映射表（ID -> TileDefinition）
 *
 * @author Deskmate Team
 */
@Serializable
data class LayoutConfig(
    val areas: List<String>,
    val tiles: Map<String, TileDefinition>
) {
    /**
     * 网格总行数（从 areas 自动推断）
     */
    val rows: Int get() = areas.size

    /**
     * 网格总列数（从 areas 第一行自动推断）
     */
    val columns: Int get() = areas.firstOrNull()?.trim()?.split(Regex("\\s+"))?.size ?: 0

    init {
        require(areas.isNotEmpty()) { "areas 不能为空" }

        // 验证每行的列数一致
        val expectedColumns = columns
        areas.forEachIndexed { index, row ->
            val cells = row.trim().split(Regex("\\s+"))
            require(cells.size == expectedColumns) {
                "areas[${index}] 列数不一致，期望: $expectedColumns，实际: ${cells.size}"
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
