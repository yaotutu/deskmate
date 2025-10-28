package top.yaotutu.deskmate.data.repository

import android.content.Context
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
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            json.decodeFromString<LayoutConfig>(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 获取默认布局配置（用于配置文件加载失败时的兜底）
     */
    fun getDefaultLayoutConfig(): LayoutConfig {
        return LayoutConfig(
            tiles = emptyList()
        )
    }
}
