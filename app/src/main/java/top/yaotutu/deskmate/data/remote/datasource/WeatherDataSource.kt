package top.yaotutu.deskmate.data.remote.datasource

import top.yaotutu.deskmate.data.model.DailyForecast
import top.yaotutu.deskmate.data.model.WeatherData

/**
 * 天气数据源抽象接口
 *
 * 所有天气供应商（和风天气、OpenWeather、AccuWeather等）必须实现此接口。
 * 业务层只依赖此抽象接口，不关心具体实现。
 *
 * 设计原则：
 * - 统一的数据模型（WeatherData）
 * - 统一的错误处理（Result<T>）
 * - 支持多供应商切换
 */
interface WeatherDataSource {

    /**
     * 获取当前实时天气
     *
     * @param location 位置标识（可以是城市ID、经纬度、城市名称等，由实现类定义）
     * @return Result<WeatherData> 成功返回天气数据，失败返回异常
     */
    suspend fun getCurrentWeather(location: String): Result<WeatherData>

    /**
     * 获取天气预报
     *
     * @param location 位置标识
     * @param days 预报天数（3/7/10/15等，由实现类支持）
     * @return Result<List<DailyForecast>> 成功返回预报列表，失败返回异常
     */
    suspend fun getForecast(location: String, days: Int = 7): Result<List<DailyForecast>>

    /**
     * 获取供应商名称（用于日志和调试）
     */
    fun getProviderName(): String

    /**
     * 检查供应商是否可用（可选实现）
     * 用于多供应商降级场景
     */
    suspend fun isAvailable(): Boolean = true
}

/**
 * 天气数据源异常
 */
sealed class WeatherDataSourceException(message: String, cause: Throwable? = null) : Exception(message, cause) {

    /**
     * 网络错误（无网络、超时等）
     */
    class NetworkError(message: String, cause: Throwable? = null) : WeatherDataSourceException(message, cause)

    /**
     * API认证错误（无效的API Key、权限不足等）
     */
    class AuthenticationError(message: String) : WeatherDataSourceException(message)

    /**
     * 数据不存在或无效位置
     */
    class DataNotFoundError(message: String) : WeatherDataSourceException(message)

    /**
     * API限流（超过调用次数限制）
     */
    class RateLimitError(message: String) : WeatherDataSourceException(message)

    /**
     * 服务器错误
     */
    class ServerError(message: String) : WeatherDataSourceException(message)

    /**
     * 数据解析错误
     */
    class ParseError(message: String, cause: Throwable? = null) : WeatherDataSourceException(message, cause)

    /**
     * 未知错误
     */
    class UnknownError(message: String, cause: Throwable? = null) : WeatherDataSourceException(message, cause)
}
