package top.yaotutu.deskmate.data.model

import kotlinx.serialization.Serializable

/**
 * 瓷砖配置数据类
 *
 * @property type 瓷砖类型（"clock", "weather", "calendar", "todo", "news"）
 * @property variant 变体名称（如 "simple", "standard", "detailed"）
 * @property rows 占用的行数（网格单元数）
 * @property columns 占用的列数（网格单元数）
 */
@Serializable
data class TileConfig(
    val type: String,
    val variant: String,
    val rows: Int,
    val columns: Int
)

/**
 * 瓷砖类型枚举（用于类型安全）
 */
enum class TileType(val typeName: String) {
    CLOCK("clock"),
    WEATHER("weather"),
    CALENDAR("calendar"),
    TODO("todo"),
    NEWS("news");

    companion object {
        fun fromString(type: String): TileType? {
            return entries.find { it.typeName == type }
        }
    }
}
