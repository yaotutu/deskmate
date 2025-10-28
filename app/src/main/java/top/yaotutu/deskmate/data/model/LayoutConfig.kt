package top.yaotutu.deskmate.data.model

import kotlinx.serialization.Serializable

/**
 * 布局配置数据类
 *
 * @property tiles 瓷砖配置列表（按顺序从上到下、从左到右排列）
 */
@Serializable
data class LayoutConfig(
    val tiles: List<TileConfig>
)
