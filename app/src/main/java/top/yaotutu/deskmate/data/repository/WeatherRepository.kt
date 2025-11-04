package top.yaotutu.deskmate.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import top.yaotutu.deskmate.data.model.DailyForecast
import top.yaotutu.deskmate.data.model.WeatherData
import top.yaotutu.deskmate.data.remote.datasource.WeatherDataSource
import top.yaotutu.deskmate.data.remote.datasource.WeatherDataSourceException

/**
 * 天气数据仓库
 *
 * 职责：
 * - 统一管理天气数据获取
 * - 缓存管理（内存缓存，避免频繁API调用）
 * - 错误降级（API失败时使用缓存数据）
 * - 供应商管理（支持多供应商切换，当前仅和风天气）
 * - 数据有效期管理
 *
 * @param dataSource 天气数据源（可注入不同供应商）
 * @param cacheExpirationMinutes 缓存有效期（分钟），默认30分钟
 */
class WeatherRepository(
    private val dataSource: WeatherDataSource,
    private val cacheExpirationMinutes: Int = 30
) {

    // 当前天气数据缓存
    private var cachedWeatherData: WeatherData? = null
    private var weatherCacheTime: Long = 0L

    // 预报数据缓存
    private var cachedForecast: List<DailyForecast> = emptyList()
    private var forecastCacheTime: Long = 0L

    // 天气数据状态流（供ViewModel订阅）
    private val _weatherDataFlow = MutableStateFlow(WeatherData.default())
    val weatherDataFlow: StateFlow<WeatherData> = _weatherDataFlow.asStateFlow()

    // 预报数据状态流
    private val _forecastFlow = MutableStateFlow<List<DailyForecast>>(emptyList())
    val forecastFlow: StateFlow<List<DailyForecast>> = _forecastFlow.asStateFlow()

    // 加载状态
    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: StateFlow<Boolean> = _isLoadingFlow.asStateFlow()

    // 错误信息
    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow: StateFlow<String?> = _errorFlow.asStateFlow()

    /**
     * 获取当前天气（带缓存）
     *
     * @param location 位置标识
     * @param forceRefresh 强制刷新（忽略缓存）
     * @return WeatherData 天气数据（失败时返回缓存或默认数据）
     */
    suspend fun getWeatherData(
        location: String = "",
        forceRefresh: Boolean = false
    ): WeatherData {
        // 检查缓存是否有效
        if (!forceRefresh && isCacheValid(weatherCacheTime)) {
            Timber.d("天气数据 - 使用缓存: ${cachedWeatherData?.location}")
            cachedWeatherData?.let {
                _weatherDataFlow.value = it
                return it
            }
        }

        // 开始加载
        _isLoadingFlow.value = true
        _errorFlow.value = null

        // 从数据源获取
        val result = dataSource.getCurrentWeather(location)

        _isLoadingFlow.value = false

        return result.fold(
            onSuccess = { weatherData ->
                // 更新缓存
                cachedWeatherData = weatherData
                weatherCacheTime = System.currentTimeMillis()
                _weatherDataFlow.value = weatherData
                Timber.d("天气数据 - 获取成功: ${weatherData.location}, ${weatherData.temperature}°C, ${weatherData.condition}")
                weatherData
            },
            onFailure = { exception ->
                // 错误处理
                val errorMessage = when (exception) {
                    is WeatherDataSourceException.NetworkError -> "网络连接失败"
                    is WeatherDataSourceException.AuthenticationError -> "API认证失败"
                    is WeatherDataSourceException.RateLimitError -> "请求过于频繁，请稍后再试"
                    is WeatherDataSourceException.DataNotFoundError -> "未找到该位置的天气数据"
                    else -> "获取天气失败: ${exception.message}"
                }

                _errorFlow.value = errorMessage
                Timber.w(exception, "天气数据 - 获取失败: $errorMessage")

                // 降级处理：使用缓存或默认数据
                val fallbackData = cachedWeatherData ?: WeatherData.default()
                _weatherDataFlow.value = fallbackData
                fallbackData
            }
        )
    }

    /**
     * 获取天气预报（带缓存）
     *
     * @param location 位置标识
     * @param days 预报天数（默认7天）
     * @param forceRefresh 强制刷新
     * @return List<DailyForecast> 预报列表
     */
    suspend fun getForecast(
        location: String = "",
        days: Int = 7,
        forceRefresh: Boolean = false
    ): List<DailyForecast> {
        // 检查缓存（预报缓存时间更长，可用2小时）
        if (!forceRefresh && isCacheValid(forecastCacheTime, 120)) {
            Timber.d("天气预报 - 使用缓存: ${cachedForecast.size}条")
            if (cachedForecast.isNotEmpty()) {
                _forecastFlow.value = cachedForecast
                return cachedForecast
            }
        }

        // 开始加载
        _isLoadingFlow.value = true

        val result = dataSource.getForecast(location, days)

        _isLoadingFlow.value = false

        return result.fold(
            onSuccess = { forecastList ->
                // 更新缓存
                cachedForecast = forecastList
                forecastCacheTime = System.currentTimeMillis()
                _forecastFlow.value = forecastList
                Timber.d("天气预报 - 获取成功: ${forecastList.size}天")
                forecastList
            },
            onFailure = { exception ->
                Timber.w(exception, "天气预报 - 获取失败")
                _errorFlow.value = "获取预报失败"

                // 降级：使用缓存
                _forecastFlow.value = cachedForecast
                cachedForecast
            }
        )
    }

    /**
     * 刷新所有天气数据
     */
    suspend fun refresh(location: String = "") {
        Timber.d("天气数据 - 强制刷新")
        getWeatherData(location, forceRefresh = true)
        getForecast(location, forceRefresh = true)
    }

    /**
     * 清除缓存
     */
    fun clearCache() {
        cachedWeatherData = null
        cachedForecast = emptyList()
        weatherCacheTime = 0L
        forecastCacheTime = 0L
        Timber.d("天气数据 - 缓存已清除")
    }

    /**
     * 获取数据源提供商名称
     */
    fun getProviderName(): String = dataSource.getProviderName()

    /**
     * 检查缓存是否有效
     *
     * @param cacheTime 缓存时间戳
     * @param expirationMinutes 有效期（分钟），默认使用配置的缓存有效期
     */
    private fun isCacheValid(
        cacheTime: Long,
        expirationMinutes: Int = cacheExpirationMinutes
    ): Boolean {
        if (cacheTime == 0L) return false
        val currentTime = System.currentTimeMillis()
        val cacheAge = (currentTime - cacheTime) / 1000 / 60  // 分钟
        return cacheAge < expirationMinutes
    }
}
