package top.yaotutu.deskmate.data.model

data class NewsItem(
    val id: String,
    val title: String,
    val timestamp: Long = System.currentTimeMillis()
)
