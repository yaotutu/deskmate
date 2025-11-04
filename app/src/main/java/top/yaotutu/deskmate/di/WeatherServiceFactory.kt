package top.yaotutu.deskmate.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import top.yaotutu.deskmate.data.remote.api.QWeatherApiService
import top.yaotutu.deskmate.data.remote.datasource.QWeatherDataSource
import top.yaotutu.deskmate.data.repository.WeatherRepository
import java.util.concurrent.TimeUnit

/**
 * 天气服务工厂
 *
 * 负责创建和管理天气相关的依赖（Retrofit、DataSource、Repository等）
 * 简化版依赖注入，避免引入Hilt/Koin等重量级框架
 */
object WeatherServiceFactory {

    // 和风天气API配置
    private const val API_KEY = "1e9b05ba32154c9fa547bc148820517a"
    private const val DEFAULT_LOCATION = "101110101"  // 西安

    // JSON配置（忽略未知字段，宽松模式）
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    // 单例：OkHttpClient
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    // 单例：Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(QWeatherApiService.DEFAULT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // 单例：QWeatherApiService
    private val qWeatherApiService: QWeatherApiService by lazy {
        retrofit.create(QWeatherApiService::class.java)
    }

    // 单例：QWeatherDataSource
    private val qWeatherDataSource: QWeatherDataSource by lazy {
        QWeatherDataSource(
            apiService = qWeatherApiService,
            apiKey = API_KEY,
            defaultLocation = DEFAULT_LOCATION
        )
    }

    // 单例：WeatherRepository
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(
            dataSource = qWeatherDataSource,
            cacheExpirationMinutes = 30
        )
    }

    /**
     * 创建日志拦截器（仅Debug模式启用）
     */
    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // 生产环境可改为NONE
        }
    }

    /**
     * 获取WeatherRepository实例
     */
    fun provideWeatherRepository(): WeatherRepository {
        return weatherRepository
    }
}
