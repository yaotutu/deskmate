package top.yaotutu.deskmate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import top.yaotutu.deskmate.data.model.NewsItem
import top.yaotutu.deskmate.data.model.Notification
import top.yaotutu.deskmate.data.model.TodoItem
import java.util.Calendar

data class DashboardUiState(
    val currentTime: String = "",
    val currentDate: String = "",
    val lunarDate: String = "",
    val temperature: Int = 22,
    val notifications: List<Notification> = emptyList(),
    val newsItems: List<NewsItem> = emptyList(),
    val todoItems: List<TodoItem> = emptyList(),
    val currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH),
    val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val currentDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
)

class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")

        _uiState.value = _uiState.value.copy(
            currentTime = String.format("%02d:%02d", hour, minute),
            currentDate = "${weekDays[dayOfWeek - 1]}, ${month}月 ${dayOfMonth}",
            lunarDate = "农历八月廿二",
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
}
