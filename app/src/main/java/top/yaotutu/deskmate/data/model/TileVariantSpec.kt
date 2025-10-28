package top.yaotutu.deskmate.data.model

import androidx.compose.runtime.Composable
import top.yaotutu.deskmate.presentation.viewmodel.DashboardUiState

/**
 * 瓷砖变体规格
 *
 * 定义一个瓷砖变体的所有属性：
 * - 支持的尺寸列表
 * - 默认尺寸
 * - 对应的视图组件
 *
 * @property type 瓷砖类型（如 "clock", "weather"）
 * @property variant 变体名称（如 "simple", "detailed"）
 * @property supportedSizes 支持的尺寸列表 [(columns, rows), ...]
 * @property defaultSize 默认尺寸 (columns, rows)
 * @property view 视图组件
 */
data class TileVariantSpec(
    val type: String,
    val variant: String,
    val supportedSizes: List<Pair<Int, Int>>,
    val defaultSize: Pair<Int, Int>,
    val view: @Composable (TileConfig, DashboardUiState) -> Unit
)

/**
 * 瓷砖变体注册中心
 *
 * 负责管理所有瓷砖变体的注册和查询
 */
object TileRegistry {
    private val variants = mutableMapOf<String, TileVariantSpec>()

    /**
     * 注册一个瓷砖变体
     */
    fun register(spec: TileVariantSpec) {
        val key = "${spec.type}:${spec.variant}"
        variants[key] = spec
    }

    /**
     * 获取指定的瓷砖变体规格
     *
     * @return 变体规格，如果不存在返回 null
     */
    fun get(type: String, variant: String): TileVariantSpec? {
        val key = "$type:$variant"
        return variants[key]
    }

    /**
     * 检查指定尺寸是否被变体支持
     *
     * @return true 如果支持，false 如果不支持或变体不存在
     */
    fun isSizeSupported(type: String, variant: String, columns: Int, rows: Int): Boolean {
        val spec = get(type, variant) ?: return false
        return spec.supportedSizes.contains(columns to rows)
    }

    /**
     * 获取变体支持的尺寸列表字符串（用于错误提示）
     *
     * @return 格式化的字符串，如 "1×1, 2×2"，如果变体不存在返回 "未知"
     */
    fun getSupportedSizesString(type: String, variant: String): String {
        val spec = get(type, variant) ?: return "未知"
        return spec.supportedSizes.joinToString(", ") { "${it.first}×${it.second}" }
    }

    /**
     * 清空所有注册的变体（主要用于测试）
     */
    fun clear() {
        variants.clear()
    }
}
