package top.yaotutu.deskmate.data.model

data class TodoItem(
    val id: String,
    val title: String,
    val isCompleted: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
