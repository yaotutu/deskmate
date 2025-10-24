package top.yaotutu.deskmate.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.yaotutu.deskmate.R
import top.yaotutu.deskmate.presentation.ui.component.CalendarSection
import top.yaotutu.deskmate.presentation.ui.component.NewsSection
import top.yaotutu.deskmate.presentation.ui.component.NotificationCard
import top.yaotutu.deskmate.presentation.ui.component.TimeWeatherSection
import top.yaotutu.deskmate.presentation.ui.component.TodoSection
import top.yaotutu.deskmate.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(24.dp)
    ) {
        // 主要内容区域 - 三栏布局
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
                // 左侧栏 - 时间、天气、通知
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TimeWeatherSection(
                        time = uiState.currentTime,
                        date = uiState.currentDate,
                        lunarDate = uiState.lunarDate,
                        temperature = uiState.temperature
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // 通知标题
                    Text(
                        text = "通知",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 通知列表
                    uiState.notifications.forEach { notification ->
                        NotificationCard(notification = notification)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // 中间栏 - 图片和新闻
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // 图片
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // 这里使用占位符,实际应该加载图片
                        Text(
                            text = "风景图片",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // 新闻
                    NewsSection(newsItems = uiState.newsItems)
                }

                // 右侧栏 - 待办和日历
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TodoSection(
                        todoItems = uiState.todoItems,
                        onTodoToggle = { todoId ->
                            viewModel.toggleTodoItem(todoId)
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    CalendarSection(
                        year = uiState.currentYear,
                        month = uiState.currentMonth,
                        currentDay = uiState.currentDay
                    )
                }
            }
    }
}
