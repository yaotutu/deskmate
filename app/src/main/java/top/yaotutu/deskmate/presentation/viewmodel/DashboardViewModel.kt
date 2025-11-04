package top.yaotutu.deskmate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import top.yaotutu.deskmate.data.model.DailyForecast
import top.yaotutu.deskmate.data.model.NewsItem
import top.yaotutu.deskmate.data.model.Notification
import top.yaotutu.deskmate.data.model.TodoItem
import top.yaotutu.deskmate.data.model.WeatherData
import top.yaotutu.deskmate.data.repository.WeatherRepository
import top.yaotutu.deskmate.di.WeatherServiceFactory
import top.yaotutu.deskmate.util.LunarCalendar
import java.util.Calendar

data class DashboardUiState(
    val currentTime: String = "",
    val currentDate: String = "",
    val currentWeekday: String = "",
    val currentDayNumber: String = "",
    val currentMonthName: String = "",
    val lunarDate: String = "",
    val lunarDayName: String = "",
    val lunarMonthName: String = "",
    val lunarYearGanZhi: String = "",
    val lunarMonthGanZhi: String = "",
    val lunarDayGanZhi: String = "",
    val lunarAnimal: String = "",
    val lunarFullDate: String = "",
    val lunarSolarTerm: String? = null,
    val lunarFestival: String? = null,
    val lunarConstellation: String = "",
    val lunarDayLucky: String = "",
    val lunarDayAvoid: String = "",

    // 天气数据（完整版）⭐ 新增
    val weatherData: WeatherData = WeatherData.default(),
    val weatherForecast: List<DailyForecast> = emptyList(),
    val isWeatherLoading: Boolean = false,
    val weatherError: String? = null,

    // 兼容旧代码：保留temperature字段（从weatherData派生）
    @Deprecated("使用weatherData.temperature代替", ReplaceWith("weatherData.temperature"))
    val temperature: Int = 22,

    val notifications: List<Notification> = emptyList(),
    val newsItems: List<NewsItem> = emptyList(),
    val todoItems: List<TodoItem> = emptyList(),
    val currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH),
    val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val currentDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
)

class DashboardViewModel(
    private val weatherRepository: WeatherRepository = WeatherServiceFactory.provideWeatherRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
        loadWeatherData()
        startPeriodicWeatherUpdate()
    }

    private fun loadInitialData() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val weekday = weekDays[dayOfWeek - 1]

        // 计算农历
        val lunarInfo = LunarCalendar.solarToLunar(calendar)

        _uiState.value = _uiState.value.copy(
            currentTime = String.format("%02d:%02d", hour, minute),
            currentDate = "${weekday}, ${month}月 ${dayOfMonth}日",
            currentWeekday = weekday,
            currentDayNumber = dayOfMonth.toString(),
            currentMonthName = "${month}月",
            lunarDate = lunarInfo.toShortString(),
            lunarDayName = lunarInfo.dayName,
            lunarMonthName = lunarInfo.monthName + "月",
            lunarYearGanZhi = lunarInfo.yearGanZhi + "年",
            lunarMonthGanZhi = lunarInfo.monthGanZhi,
            lunarDayGanZhi = lunarInfo.dayGanZhi,
            lunarAnimal = lunarInfo.animal,
            lunarFullDate = lunarInfo.toFullString(),
            lunarSolarTerm = lunarInfo.solarTerm,
            lunarFestival = lunarInfo.festival,
            lunarConstellation = lunarInfo.constellation,
            lunarDayLucky = lunarInfo.dayLucky,
            lunarDayAvoid = lunarInfo.dayAvoid,
            notifications = listOf(
                Notification(
                    id = "1",
                    sender = "John",
                    message = "在路上了,很快就到!"
                ),
                Notification(
                    id = "2",
                    sender = "Lisa Chen",
                    message = "下午2点的营销会议"
                )
            ),
            newsItems = listOf(
                NewsItem(id = "1", title = "科技公司发布突破性AI模型"),
                NewsItem(id = "2", title = "全球股市因经济数据波动"),
                NewsItem(id = "3", title = "气候变化峰会达成新协议"),
                NewsItem(id = "4", title = "最新太空探索任务取得重大发现")
            ),
            todoItems = listOf(
                TodoItem(id = "1", title = "上午10点团队会议", isCompleted = false),
                TodoItem(id = "2", title = "回复重要邮件", isCompleted = false),
                TodoItem(id = "3", title = "准备下午的演示文稿", isCompleted = true),
                TodoItem(id = "4", title = "晚上6点健身", isCompleted = false)
            )
        )
    }

    fun toggleTodoItem(todoId: String) {
        val updatedTodos = _uiState.value.todoItems.map { todo ->
            if (todo.id == todoId) {
                todo.copy(isCompleted = !todo.isCompleted)
            } else {
                todo
            }
        }
        _uiState.value = _uiState.value.copy(todoItems = updatedTodos)
    }

    /**
     * 加载天气数据（实时天气 + 预报）
     */
    private fun loadWeatherData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isWeatherLoading = true, weatherError = null)

                // 并发加载实时天气和预报
                val weatherData = weatherRepository.getWeatherData()
                val forecast = weatherRepository.getForecast(days = 7)

                _uiState.value = _uiState.value.copy(
                    weatherData = weatherData,
                    weatherForecast = forecast,
                    temperature = weatherData.temperature,  // 更新兼容字段
                    isWeatherLoading = false
                )

                Timber.d("天气数据加载成功: ${weatherData.temperature}°C, ${weatherData.condition}")
            } catch (e: Exception) {
                Timber.e(e, "天气数据加载失败")
                _uiState.value = _uiState.value.copy(
                    weatherError = "天气数据加载失败",
                    isWeatherLoading = false
                )
            }
        }
    }

    /**
     * 刷新天气数据（强制更新）
     */
    fun refreshWeather() {
        viewModelScope.launch {
            Timber.d("手动刷新天气数据")
            weatherRepository.clearCache()
            loadWeatherData()
        }
    }

    /**
     * 启动定时更新天气（每30分钟）
     */
    private fun startPeriodicWeatherUpdate() {
        viewModelScope.launch {
            while (true) {
                delay(30 * 60 * 1000L)  // 30分钟
                Timber.d("定时更新天气数据")
                loadWeatherData()
            }
        }
    }

    /**
     * 获取天气数据源提供商名称
     */
    fun getWeatherProviderName(): String {
        return weatherRepository.getProviderName()
    }
}
