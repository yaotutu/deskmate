package top.yaotutu.deskmate.data.model

/**
 * 网格区域位置数据类
 *
 * 定义瓷砖在网格中的位置和尺寸
 *
 * @property id 瓷砖ID（对应areas中的字母标识）
 * @property x 起始列位置（0-based）
 * @property y 起始行位置（0-based）
 * @property width 占用列数
 * @property height 占用行数
 * @property tileDefinition 瓷砖定义（类型和变体）
 *
 * @author Deskmate Team
 */
data class AreaPosition(
    val id: String,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val tileDefinition: TileDefinition
) {
    init {
        require(x >= 0) { "x 必须 >= 0，当前值: $x" }
        require(y >= 0) { "y 必须 >= 0，当前值: $y" }
        require(width > 0) { "width 必须 > 0，当前值: $width" }
        require(height > 0) { "height 必须 > 0，当前值: $height" }
        require(id.isNotBlank()) { "id 不能为空" }
    }

    /**
     * 检查该区域是否包含指定的网格坐标
     */
    fun contains(gridX: Int, gridY: Int): Boolean {
        return gridX in x until (x + width) && gridY in y until (y + height)
    }

    /**
     * 获取区域右下角坐标（不包含）
     */
    fun getEndX(): Int = x + width
    fun getEndY(): Int = y + height
}

/**
 * 瓷砖定义
 *
 * 包含瓷砖的类型和变体信息，用于在 TileFactory 中创建瓷砖
 *
 * @property type 瓷砖类型（如 "clock", "weather"）
 * @property variant 瓷砖变体（如 "2x2", "4x4"）
 *
 * @author Deskmate Team
 */
@kotlinx.serialization.Serializable
data class TileDefinition(
    val type: String,
    val variant: String
) {
    init {
        require(type.isNotBlank()) { "type 不能为空" }
        require(variant.isNotBlank()) { "variant 不能为空" }
    }
}
