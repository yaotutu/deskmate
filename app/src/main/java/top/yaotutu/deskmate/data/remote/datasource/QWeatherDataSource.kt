package top.yaotutu.deskmate.data.remote.datasource

import timber.log.Timber
import top.yaotutu.deskmate.data.model.DailyForecast
import top.yaotutu.deskmate.data.model.WeatherData
import top.yaotutu.deskmate.data.model.api.QWeatherNow
import top.yaotutu.deskmate.data.model.api.QWeatherStatusCode
import top.yaotutu.deskmate.data.remote.api.QWeatherApiService

/**
 * 和风天气数据源实现
 *
 * 实现WeatherDataSource接口，提供和风天气API的具体调用逻辑。
 *
 * @param apiService Retrofit API服务
 * @param apiKey 和风天气API Key
 * @param defaultLocation 默认位置（城市ID，如 101010100=北京）
 */
class QWeatherDataSource(
    private val apiService: QWeatherApiService,
    private val apiKey: String,
    private val defaultLocation: String = "101110101"  // 默认西安
) : WeatherDataSource {

    override suspend fun getCurrentWeather(location: String): Result<WeatherData> {
        return try {
            val response = apiService.getCurrentWeather(
                location = location.ifEmpty { defaultLocation },
                key = apiKey
            )

            // 检查状态码
            if (!QWeatherStatusCode.isSuccess(response.code)) {
                return Result.failure(mapErrorCode(response.code))
            }

            // 转换为统一数据模型
            val weatherData = mapToWeatherData(response.now, location)
            Timber.d("和风天气 - 获取实时天气成功: $location")
            Result.success(weatherData)

        } catch (e: Exception) {
            Timber.e(e, "和风天气 - 获取实时天气失败")
            Result.failure(
                WeatherDataSourceException.NetworkError("网络请求失败", e)
            )
        }
    }

    override suspend fun getForecast(location: String, days: Int): Result<List<DailyForecast>> {
        return try {
            // 根据天数选择对应的API接口
            val response = when {
                days <= 3 -> apiService.get3DayForecast(
                    location = location.ifEmpty { defaultLocation },
                    key = apiKey
                )
                days <= 7 -> apiService.get7DayForecast(
                    location = location.ifEmpty { defaultLocation },
                    key = apiKey
                )
                days <= 10 -> apiService.get10DayForecast(
                    location = location.ifEmpty { defaultLocation },
                    key = apiKey
                )
                else -> apiService.get15DayForecast(
                    location = location.ifEmpty { defaultLocation },
                    key = apiKey
                )
            }

            // 检查状态码
            if (!QWeatherStatusCode.isSuccess(response.code)) {
                return Result.failure(mapErrorCode(response.code))
            }

            // 转换为统一数据模型
            val forecastList = response.daily.take(days).map { daily ->
                DailyForecast(
                    date = daily.fxDate,
                    maxTemp = daily.tempMax.toIntOrNull() ?: 0,
                    minTemp = daily.tempMin.toIntOrNull() ?: 0,
                    dayCondition = daily.textDay,
                    nightCondition = daily.textNight,
                    dayIconCode = daily.iconDay,
                    nightIconCode = daily.iconNight,
                    humidity = daily.humidity.toIntOrNull() ?: 0,
                    windDirection = daily.windDirDay,
                    windSpeed = daily.windSpeedDay.toIntOrNull() ?: 0,
                    precipitation = daily.precip.toDoubleOrNull() ?: 0.0
                )
            }

            Timber.d("和风天气 - 获取${days}天预报成功: $location, 共${forecastList.size}条")
            Result.success(forecastList)

        } catch (e: Exception) {
            Timber.e(e, "和风天气 - 获取预报失败")
            Result.failure(
                WeatherDataSourceException.NetworkError("网络请求失败", e)
            )
        }
    }

    override fun getProviderName(): String = "和风天气 (QWeather)"

    override suspend fun isAvailable(): Boolean {
        return try {
            val response = apiService.getCurrentWeather(
                location = defaultLocation,
                key = apiKey
            )
            QWeatherStatusCode.isSuccess(response.code)
        } catch (e: Exception) {
            Timber.w(e, "和风天气 - 可用性检查失败")
            false
        }
    }

    /**
     * 将和风API响应转换为统一的天气数据模型
     */
    private fun mapToWeatherData(now: QWeatherNow, location: String): WeatherData {
        return WeatherData(
            temperature = now.temp.toIntOrNull() ?: 0,
            condition = now.text,
            iconCode = now.icon,
            feelsLike = now.feelsLike.toIntOrNull() ?: 0,
            humidity = now.humidity.toIntOrNull() ?: 0,
            windSpeed = now.windSpeed.toIntOrNull() ?: 0,
            windDirection = now.windDir,
            pressure = now.pressure.toIntOrNull() ?: 0,
            visibility = now.vis.toIntOrNull() ?: 0,
            updateTime = now.obsTime,
            location = location
        )
    }

    /**
     * 映射和风天气错误码到统一异常
     */
    private fun mapErrorCode(code: String): WeatherDataSourceException {
        return when (code) {
            QWeatherStatusCode.NO_DATA -> WeatherDataSourceException.DataNotFoundError("无数据")
            QWeatherStatusCode.INVALID_PARAM -> WeatherDataSourceException.DataNotFoundError("参数错误或位置无效")
            QWeatherStatusCode.UNAUTHORIZED -> WeatherDataSourceException.AuthenticationError("API Key无效")
            QWeatherStatusCode.FORBIDDEN -> WeatherDataSourceException.RateLimitError("访问次数超限或余额不足")
            QWeatherStatusCode.NOT_FOUND -> WeatherDataSourceException.DataNotFoundError("数据不存在")
            QWeatherStatusCode.TOO_MANY_REQUESTS -> WeatherDataSourceException.RateLimitError("请求过于频繁")
            QWeatherStatusCode.SERVER_ERROR -> WeatherDataSourceException.ServerError("服务器错误")
            else -> WeatherDataSourceException.UnknownError("未知错误: $code")
        }
    }
}
