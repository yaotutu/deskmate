package top.yaotutu.deskmate.data.model

data class Notification(
    val id: String,
    val sender: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
