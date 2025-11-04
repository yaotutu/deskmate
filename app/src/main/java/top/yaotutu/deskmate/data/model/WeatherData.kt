package top.yaotutu.deskmate.data.model

/**
 * 统一的天气数据模型
 *
 * 由各个天气供应商的数据转换为此统一格式，
 * 业务层只依赖此模型，不关心具体供应商实现。
 */
data class WeatherData(
    val temperature: Int,              // 当前温度 (°C)
    val condition: String,             // 天气状况（晴、多云、雨等）
    val iconCode: String,              // 天气图标代码（用于显示图标）
    val feelsLike: Int,                // 体感温度 (°C)
    val humidity: Int,                 // 湿度 (%)
    val windSpeed: Int,                // 风速 (km/h)
    val windDirection: String,         // 风向（北、东北等）
    val pressure: Int = 0,             // 气压 (hPa)
    val visibility: Int = 0,           // 能见度 (km)
    val updateTime: String,            // 更新时间
    val location: String = "",         // 位置名称
    val forecast: List<DailyForecast> = emptyList()  // 预报数据
) {
    companion object {
        /**
         * 创建默认的天气数据（用于降级或初始状态）
         */
        fun default() = WeatherData(
            temperature = 22,
            condition = "晴",
            iconCode = "100",
            feelsLike = 22,
            humidity = 50,
            windSpeed = 5,
            windDirection = "北风",
            updateTime = "暂无数据",
            location = "北京"
        )
    }
}

/**
 * 每日天气预报数据
 */
data class DailyForecast(
    val date: String,                  // 日期 (yyyy-MM-dd)
    val maxTemp: Int,                  // 最高温度 (°C)
    val minTemp: Int,                  // 最低温度 (°C)
    val dayCondition: String,          // 白天天气状况
    val nightCondition: String,        // 夜间天气状况
    val dayIconCode: String,           // 白天图标代码
    val nightIconCode: String,         // 夜间图标代码
    val humidity: Int = 0,             // 平均湿度 (%)
    val windDirection: String = "",    // 风向
    val windSpeed: Int = 0,            // 风速 (km/h)
    val precipitation: Double = 0.0    // 降水量 (mm)
)

/**
 * 天气图标代码映射为Emoji
 */
object WeatherIconMapper {
    /**
     * 和风天气图标代码 -> Emoji 映射
     */
    fun getEmoji(iconCode: String): String {
        return when (iconCode) {
            "100" -> "☀️"  // 晴
            "101" -> "🌤️"  // 多云
            "102" -> "☁️"  // 少云
            "103" -> "⛅"  // 晴间多云
            "104" -> "☁️"  // 阴
            "150" -> "🌙"  // 晴（夜间）
            "151" -> "🌙"  // 多云（夜间）
            "300", "301" -> "🌦️"  // 阵雨
            "302", "303" -> "⛈️"  // 雷阵雨
            "304" -> "⛈️"  // 雷阵雨伴有冰雹
            "305", "306", "307" -> "🌧️"  // 小雨、中雨、大雨
            "308", "309", "310", "311", "312", "313" -> "🌧️"  // 暴雨及更强
            "314", "315", "316", "317", "318" -> "🌨️"  // 小雪、中雪、大雪、暴雪
            "399" -> "🌧️"  // 雨
            "400", "401", "402", "403", "404", "405", "406", "407" -> "❄️"  // 雪
            "408", "409", "410" -> "🌨️"  // 雨雪、冻雨、雨夹雪
            "499" -> "❄️"  // 雪
            "500", "501", "502", "503", "504", "507", "508" -> "🌫️"  // 雾、霾
            "509", "510", "511", "512", "513" -> "🌫️"  // 浓雾、强浓雾、霾、沙尘、扬沙
            "514", "515" -> "🌪️"  // 沙尘暴、强沙尘暴
            "900" -> "🌡️"  // 热
            "901" -> "🥶"  // 冷
            else -> "🌤️"  // 默认
        }
    }
}
