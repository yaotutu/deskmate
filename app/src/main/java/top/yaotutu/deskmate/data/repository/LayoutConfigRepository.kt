package top.yaotutu.deskmate.data.repository

import android.content.Context
import android.util.Log
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import top.yaotutu.deskmate.data.model.ConfigErrorType
import top.yaotutu.deskmate.data.model.ConfigLoadResult
import top.yaotutu.deskmate.data.model.LayoutConfig
import top.yaotutu.deskmate.data.model.TileConfig
import top.yaotutu.deskmate.data.model.TileDefinition
import java.io.FileNotFoundException
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
     * 从 assets 目录加载布局配置（新版本 - 返回详细结果）
     *
     * @param fileName 配置文件名（默认为 dashboard_layout.json）
     * @return ConfigLoadResult.Success 或 ConfigLoadResult.Error
     */
    fun loadLayoutConfigWithResult(fileName: String = "dashboard_layout.json"): ConfigLoadResult {
        return try {
            Log.d("LayoutConfig", "尝试加载配置文件: $fileName")
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            Log.d("LayoutConfig", "成功读取配置文件，长度: ${jsonString.length}")

            val config = json.decodeFromString<LayoutConfig>(jsonString)
            Log.d("LayoutConfig", "成功解析配置，瓷砖数量: ${config.tiles.size}")

            // 验证配置有效性
            if (config.tiles.isEmpty()) {
                Log.w("LayoutConfig", "警告：配置文件中瓷砖列表为空")
            }

            ConfigLoadResult.Success(
                config = config,
                source = fileName
            )
        } catch (e: FileNotFoundException) {
            Log.e("LayoutConfig", "文件不存在: $fileName", e)
            ConfigLoadResult.Error(
                fileName = fileName,
                errorType = ConfigErrorType.FILE_NOT_FOUND,
                errorMessage = "找不到配置文件：$fileName",
                fallbackConfig = getSafeDefaultLayoutConfig()
            )
        } catch (e: SerializationException) {
            Log.e("LayoutConfig", "JSON 解析失败: $fileName", e)
            ConfigLoadResult.Error(
                fileName = fileName,
                errorType = ConfigErrorType.PARSE_ERROR,
                errorMessage = "配置文件格式错误：${e.message ?: "未知解析错误"}",
                fallbackConfig = getSafeDefaultLayoutConfig()
            )
        } catch (e: IOException) {
            Log.e("LayoutConfig", "IO 异常: $fileName", e)
            ConfigLoadResult.Error(
                fileName = fileName,
                errorType = ConfigErrorType.IO_ERROR,
                errorMessage = "文件读取失败：${e.message ?: "未知 IO 错误"}",
                fallbackConfig = getSafeDefaultLayoutConfig()
            )
        } catch (e: Exception) {
            Log.e("LayoutConfig", "未知错误: $fileName", e)
            ConfigLoadResult.Error(
                fileName = fileName,
                errorType = ConfigErrorType.UNKNOWN,
                errorMessage = "加载失败：${e.message ?: "未知错误"}",
                fallbackConfig = getSafeDefaultLayoutConfig()
            )
        }
    }

    /**
     * 从 assets 目录加载布局配置（旧版本 - 向后兼容）
     *
     * @param fileName 配置文件名（默认为 dashboard_layout.json）
     * @return 解析后的 LayoutConfig 对象，失败时返回 null
     */
    @Deprecated(
        message = "使用 loadLayoutConfigWithResult() 获取详细错误信息",
        replaceWith = ReplaceWith("loadLayoutConfigWithResult(fileName)"),
        level = DeprecationLevel.WARNING
    )
    fun loadLayoutConfig(fileName: String = "dashboard_layout.json"): LayoutConfig? {
        return when (val result = loadLayoutConfigWithResult(fileName)) {
            is ConfigLoadResult.Success -> result.config
            is ConfigLoadResult.Error -> result.fallbackConfig
        }
    }

    /**
     * 获取默认布局配置（用于配置文件加载失败时的兜底）
     * ⚠️ 注意：返回空瓷砖映射会导致黑屏
     */
    @Deprecated(
        message = "使用 getSafeDefaultLayoutConfig() 获取有内容的默认配置",
        replaceWith = ReplaceWith("getSafeDefaultLayoutConfig()"),
        level = DeprecationLevel.WARNING
    )
    fun getDefaultLayoutConfig(): LayoutConfig {
        Log.w("LayoutConfig", "使用默认配置（空瓷砖映射）")
        return LayoutConfig(
            columns = 6,
            rows = 4,
            areas = listOf(
                ". . . . . .",
                ". . . . . .",
                ". . . . . .",
                ". . . . . ."
            ),
            tiles = emptyMap()
        )
    }

    /**
     * 获取安全的默认布局配置（包含示例瓷砖，避免黑屏）
     */
    fun getSafeDefaultLayoutConfig(): LayoutConfig {
        Log.w("LayoutConfig", "使用安全默认配置（包含示例时钟瓷砖）")
        return LayoutConfig(
            columns = 6,
            rows = 4,
            areas = listOf(
                "K K . . . .",
                "K K . . . .",
                ". . . . . .",
                ". . . . . ."
            ),
            tiles = mapOf(
                "K" to TileDefinition(
                    type = "clock",
                    variant = "2x2"
                )
            )
        )
    }
}
