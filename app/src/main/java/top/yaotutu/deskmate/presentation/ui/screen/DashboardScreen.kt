package top.yaotutu.deskmate.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.yaotutu.deskmate.presentation.ui.component.*
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
            .background(Color(0xFF000000))  // Metro 风格：纯黑背景
            .padding(8.dp)
    ) {
        // Windows Phone 动态瓷砖布局 - 单屏布局
        TileGridContainer(modifier = Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns ->
            // 使用 CompositionLocal 提供网格参数，简化组件使用
            ProvideTileGrid(baseCellSize = baseCellSize, dynamicGap = dynamicGap, columns = columns) {
                // 使用 Row + horizontalScroll 实现整体横向滚动
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dynamicGap)
                    ) {
                        // 第一行：时间 + 天气
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(dynamicGap)
                        ) {
                            // 时钟瓷砖 - 错峰动画索引 0
                            StaggerEnterAnimation(index = 0) {
                                ClockTile(
                                    time = uiState.currentTime,
                                    date = uiState.currentDate,
                                    lunarDate = uiState.lunarDate
                                )
                            }

                            // 天气瓷砖 - 错峰动画索引 1
                            StaggerEnterAnimation(index = 1) {
                                WeatherTile(
                                    temperature = uiState.temperature,
                                    icon = "☀"
                                )
                            }
                        }

                        // 第二行：日历 + 待办 + 新闻
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(dynamicGap)
                        ) {
                            // 日历瓷砖 - 错峰动画索引 2
                            StaggerEnterAnimation(index = 2) {
                                CalendarTile(
                                    month = "十月",
                                    day = uiState.currentDay
                                )
                            }

                            // 待办瓷砖 - 错峰动画索引 3
                            StaggerEnterAnimation(index = 3) {
                                TodoTile(
                                    title = "待办",
                                    items = listOf("买菜", "打电话给水管工")
                                )
                            }

                            // 新闻瓷砖 - 错峰动画索引 4
                            StaggerEnterAnimation(index = 4) {
                                NewsTile(
                                    newsItems = listOf(
                                        "头条" to "科技板块飙升\n全球市场反弹",
                                        "国际" to "可再生能源\n技术新突破"
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
