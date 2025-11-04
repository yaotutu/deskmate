package top.yaotutu.deskmate.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import top.yaotutu.deskmate.data.model.api.QWeatherForecastResponse
import top.yaotutu.deskmate.data.model.api.QWeatherNowResponse

/**
 * 和风天气API服务接口
 *
 * 官方文档：https://dev.qweather.com/docs/
 *
 * 基础URL：https://devapi.qweather.com/
 * （或使用 https://api.qweather.com/ for 付费版本）
 */
interface QWeatherApiService {

    /**
     * 获取实时天气
     *
     * 接口：GET /v7/weather/now
     * 文档：https://dev.qweather.com/docs/api/weather/weather-now/
     *
     * @param location 位置标识（LocationID、经纬度"lon,lat"、Adcode、IP等）
     * @param key API Key（和风天气分配的密钥）
     * @param lang 语言（可选，默认zh，支持zh、en等）
     * @param unit 单位制（可选，m=公制、i=英制，默认m）
     * @return QWeatherNowResponse 实时天气响应
     */
    @GET("v7/weather/now")
    suspend fun getCurrentWeather(
        @Query("location") location: String,
        @Query("key") key: String,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWeatherNowResponse

    /**
     * 获取3天天气预报
     *
     * 接口：GET /v7/weather/3d
     * 文档：https://dev.qweather.com/docs/api/weather/weather-daily-forecast/
     */
    @GET("v7/weather/3d")
    suspend fun get3DayForecast(
        @Query("location") location: String,
        @Query("key") key: String,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWeatherForecastResponse

    /**
     * 获取7天天气预报
     */
    @GET("v7/weather/7d")
    suspend fun get7DayForecast(
        @Query("location") location: String,
        @Query("key") key: String,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWeatherForecastResponse

    /**
     * 获取10天天气预报
     */
    @GET("v7/weather/10d")
    suspend fun get10DayForecast(
        @Query("location") location: String,
        @Query("key") key: String,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWeatherForecastResponse

    /**
     * 获取15天天气预报
     */
    @GET("v7/weather/15d")
    suspend fun get15DayForecast(
        @Query("location") location: String,
        @Query("key") key: String,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWeatherForecastResponse

    companion object {
        /**
         * 和风天气免费版基础URL（开发版）
         */
        const val DEV_BASE_URL = "https://devapi.qweather.com/"

        /**
         * 和风天气付费版基础URL（正式版）
         */
        const val PROD_BASE_URL = "https://api.qweather.com/"

        /**
         * 默认使用开发版URL
         */
        const val DEFAULT_BASE_URL = DEV_BASE_URL
    }
}
