package top.yaotutu.deskmate.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 和风天气API响应结构
 *
 * 文档：https://dev.qweather.com/docs/
 */

/**
 * 实时天气API响应
 * 接口：GET /v7/weather/now
 */
@Serializable
data class QWeatherNowResponse(
    @SerialName("code") val code: String,           // 状态码 (200=成功)
    @SerialName("updateTime") val updateTime: String,  // API最后更新时间
    @SerialName("fxLink") val fxLink: String,       // 响应式页面链接
    @SerialName("now") val now: QWeatherNow         // 当前天气数据
)

/**
 * 当前天气数据
 */
@Serializable
data class QWeatherNow(
    @SerialName("obsTime") val obsTime: String,     // 观测时间 (ISO8601)
    @SerialName("temp") val temp: String,           // 温度 (°C)
    @SerialName("feelsLike") val feelsLike: String, // 体感温度 (°C)
    @SerialName("icon") val icon: String,           // 天气图标代码
    @SerialName("text") val text: String,           // 天气状况（晴、多云等）
    @SerialName("wind360") val wind360: String,     // 风向 (360度)
    @SerialName("windDir") val windDir: String,     // 风向（北、东北等）
    @SerialName("windScale") val windScale: String, // 风力等级 (0-12)
    @SerialName("windSpeed") val windSpeed: String, // 风速 (km/h)
    @SerialName("humidity") val humidity: String,   // 相对湿度 (%)
    @SerialName("precip") val precip: String,       // 过去1小时降水量 (mm)
    @SerialName("pressure") val pressure: String,   // 气压 (hPa)
    @SerialName("vis") val vis: String,             // 能见度 (km)
    @SerialName("cloud") val cloud: String? = null, // 云量 (%)
    @SerialName("dew") val dew: String? = null      // 露点温度 (°C)
)

/**
 * 天气预报API响应
 * 接口：GET /v7/weather/3d, /v7/weather/7d, /v7/weather/10d 等
 */
@Serializable
data class QWeatherForecastResponse(
    @SerialName("code") val code: String,           // 状态码 (200=成功)
    @SerialName("updateTime") val updateTime: String,  // API最后更新时间
    @SerialName("fxLink") val fxLink: String,       // 响应式页面链接
    @SerialName("daily") val daily: List<QWeatherDaily>  // 预报数据列表
)

/**
 * 每日天气预报数据
 */
@Serializable
data class QWeatherDaily(
    @SerialName("fxDate") val fxDate: String,       // 预报日期 (yyyy-MM-dd)
    @SerialName("sunrise") val sunrise: String,     // 日出时间 (HH:mm)
    @SerialName("sunset") val sunset: String,       // 日落时间 (HH:mm)
    @SerialName("moonrise") val moonrise: String,   // 月升时间 (HH:mm)
    @SerialName("moonset") val moonset: String,     // 月落时间 (HH:mm)
    @SerialName("moonPhase") val moonPhase: String, // 月相名称
    @SerialName("moonPhaseIcon") val moonPhaseIcon: String,  // 月相图标代码
    @SerialName("tempMax") val tempMax: String,     // 最高温度 (°C)
    @SerialName("tempMin") val tempMin: String,     // 最低温度 (°C)
    @SerialName("iconDay") val iconDay: String,     // 白天天气图标代码
    @SerialName("textDay") val textDay: String,     // 白天天气状况
    @SerialName("iconNight") val iconNight: String, // 夜间天气图标代码
    @SerialName("textNight") val textNight: String, // 夜间天气状况
    @SerialName("wind360Day") val wind360Day: String,      // 白天风向 (360度)
    @SerialName("windDirDay") val windDirDay: String,      // 白天风向（北、东北等）
    @SerialName("windScaleDay") val windScaleDay: String,  // 白天风力等级 (0-12)
    @SerialName("windSpeedDay") val windSpeedDay: String,  // 白天风速 (km/h)
    @SerialName("wind360Night") val wind360Night: String,      // 夜间风向 (360度)
    @SerialName("windDirNight") val windDirNight: String,      // 夜间风向
    @SerialName("windScaleNight") val windScaleNight: String,  // 夜间风力等级
    @SerialName("windSpeedNight") val windSpeedNight: String,  // 夜间风速
    @SerialName("humidity") val humidity: String,   // 相对湿度 (%)
    @SerialName("precip") val precip: String,       // 降水量 (mm)
    @SerialName("pressure") val pressure: String,   // 气压 (hPa)
    @SerialName("vis") val vis: String,             // 能见度 (km)
    @SerialName("cloud") val cloud: String? = null, // 云量 (%)
    @SerialName("uvIndex") val uvIndex: String      // 紫外线强度指数
)

/**
 * API错误响应
 */
@Serializable
data class QWeatherErrorResponse(
    @SerialName("code") val code: String,           // 错误码
    @SerialName("refer") val refer: QWeatherRefer? = null  // 参考信息
)

@Serializable
data class QWeatherRefer(
    @SerialName("sources") val sources: List<String> = emptyList(),
    @SerialName("license") val license: List<String> = emptyList()
)

/**
 * 和风天气状态码说明
 */
object QWeatherStatusCode {
    const val SUCCESS = "200"           // 请求成功
    const val NO_DATA = "204"           // 无数据
    const val INVALID_PARAM = "400"     // 请求错误（参数错误或缺少必选参数）
    const val UNAUTHORIZED = "401"      // 认证失败（无效的API KEY）
    const val FORBIDDEN = "402"         // 超过访问次数或余额不足
    const val NOT_FOUND = "404"         // 查询的数据不存在
    const val TOO_MANY_REQUESTS = "429" // 超过限定的QPM
    const val SERVER_ERROR = "500"      // 服务器错误

    fun isSuccess(code: String) = code == SUCCESS
}
