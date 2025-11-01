package top.yaotutu.deskmate.data.repository

import android.content.Context
import android.util.Log
import kotlinx.serialization.json.Json
import top.yaotutu.deskmate.data.model.LayoutConfig
import java.io.IOException

/**
 * 布局配置仓库
 * 负责从 assets 目录读取和解析 dashboard_layout.json 配置文件
 */
class LayoutConfigRepository(private val context: Context) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    /**
     * 从 assets 目录加载布局配置
     *
     * @param fileName 配置文件名（默认为 dashboard_layout.json）
     * @return 解析后的 LayoutConfig 对象，失败时返回 null
     */
    fun loadLayoutConfig(fileName: String = "dashboard_layout.json"): LayoutConfig? {
        return try {
            Log.d("LayoutConfig", "尝试加载配置文件: $fileName")
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            Log.d("LayoutConfig", "成功读取配置文件，长度: ${jsonString.length}")
            val config = json.decodeFromString<LayoutConfig>(jsonString)
            Log.d("LayoutConfig", "成功解析配置，瓷砖数量: ${config.tiles.size}")
            config
        } catch (e: IOException) {
            Log.e("LayoutConfig", "IO异常，无法读取配置文件: $fileName", e)
            null
        } catch (e: Exception) {
            Log.e("LayoutConfig", "解析配置文件失败: $fileName", e)
            null
        }
    }

    /**
     * 获取默认布局配置（用于配置文件加载失败时的兜底）
     */
    fun getDefaultLayoutConfig(): LayoutConfig {
        Log.w("LayoutConfig", "使用默认配置（空瓷砖列表）")
        return LayoutConfig(
            tiles = emptyList()
        )
    }
}
