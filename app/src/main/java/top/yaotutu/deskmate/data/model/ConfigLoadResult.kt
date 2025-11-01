package top.yaotutu.deskmate.data.model

/**
 * 配置加载结果
 * 用于明确表示配置加载的成功/失败状态
 */
sealed class ConfigLoadResult {
    /**
     * 加载成功
     * @param config 加载的配置
     * @param source 配置来源（JSON文件名或"default"）
     */
    data class Success(
        val config: LayoutConfig,
        val source: String
    ) : ConfigLoadResult()

    /**
     * 加载失败
     * @param fileName 尝试加载的文件名
     * @param errorType 错误类型
     * @param errorMessage 错误详情
     * @param fallbackConfig 降级配置（可选）
     */
    data class Error(
        val fileName: String,
        val errorType: ConfigErrorType,
        val errorMessage: String,
        val fallbackConfig: LayoutConfig? = null
    ) : ConfigLoadResult()
}

/**
 * 配置错误类型
 */
enum class ConfigErrorType(val displayName: String) {
    FILE_NOT_FOUND("配置文件不存在"),
    PARSE_ERROR("JSON 解析失败"),
    INVALID_FORMAT("配置格式无效"),
    IO_ERROR("文件读取失败"),
    UNKNOWN("未知错误")
}
