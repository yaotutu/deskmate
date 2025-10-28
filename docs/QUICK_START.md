# 快速开始指南 🚀

本指南将帮助你在 5 分钟内开始使用 Deskmate 的瓷砖组件库。

---

## 📦 第一步：理解项目结构

Deskmate 采用三层架构：

```
基础层（底层 API）
   ↓
组件层（高级 API）← 你主要使用这一层
   ↓
页面层（业务逻辑）
```

---

## 🎯 第二步：创建你的第一个瓷砖页面

### 1. 创建 ViewModel（数据层）

```kotlin
// presentation/viewmodel/MyViewModel.kt
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()
}

data class MyUiState(
    val currentTime: String = "10:12",
    val currentDate: String = "星期一, 10月 27日",
    val temperature: Int = 22
)
```

### 2. 创建 Screen（UI 层）

```kotlin
// presentation/ui/screen/MyScreen.kt
@Composable
fun MyScreen(
    viewModel: MyViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))  // 深色背景
            .padding(8.dp)
    ) {
        TileGridContainer(modifier = Modifier.fillMaxSize()) { cellWidth, cellHeight ->
            ProvideTileGrid(cellWidth, cellHeight) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // 时钟瓷砖
                        ClockTile(
                            time = uiState.currentTime,
                            date = uiState.currentDate,
                            lunarDate = "农历八月廿一"
                        )

                        // 天气瓷砖
                        WeatherTile(temperature = uiState.temperature)
                    }
                }
            }
        }
    }
}
```

### 3. 在 MainActivity 中使用

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeskmateTheme {
                MyScreen()
            }
        }
    }
}
```

---

## 🧩 第三步：添加更多瓷砖

### 添加日历瓷砖

```kotlin
CalendarTile(
    month = "十月",
    day = 27
)
```

### 添加待办瓷砖

```kotlin
TodoTile(
    title = "待办",
    items = listOf(
        "买菜",
        "打电话给水管工",
        "完成设计稿"
    )
)
```

### 添加新闻瓷砖

```kotlin
NewsTile(
    newsItems = listOf(
        "头条" to "科技板块飙升\n全球市场反弹",
        "国际" to "可再生能源\n技术新突破",
        "体育" to "本地球队晋级决赛"
    )
)
```

---

## 🎨 第四步：自定义颜色

所有瓷砖都支持自定义背景颜色：

```kotlin
ClockTile(
    time = "10:12",
    date = "星期一, 10月 27日",
    lunarDate = "农历八月廿一",
    backgroundColor = Color(0xFF9C27B0)  // 自定义紫色
)

WeatherTile(
    temperature = 22,
    backgroundColor = Color(0xFF2196F3)  // 自定义蓝色
)
```

### Metro 配色推荐

```kotlin
val MetroColors = object {
    val Blue = Color(0xFF0078D7)
    val Orange = Color(0xFFFF8C00)
    val Green = Color(0xFF00A300)
    val Purple = Color(0xFFAA00FF)
    val Red = Color(0xFFE51400)
    val Teal = Color(0xFF00ABA9)
    val Lime = Color(0xFF8CBF26)
    val Pink = Color(0xFFE3008C)
}
```

---

## 🔄 第五步：添加动态数据

### 更新时间

```kotlin
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    init {
        // 每秒更新时间
        viewModelScope.launch {
            while (true) {
                val now = LocalDateTime.now()
                _uiState.update {
                    it.copy(
                        currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm"))
                    )
                }
                delay(1000)
            }
        }
    }
}
```

### 从网络获取天气

```kotlin
class MyViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            try {
                val weather = weatherRepository.getCurrentWeather()
                _uiState.update { it.copy(temperature = weather.temp) }
            } catch (e: Exception) {
                // 处理错误
            }
        }
    }
}
```

---

## 📱 完整示例

### 一个完整的仪表盘页面

```kotlin
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(8.dp)
    ) {
        TileGridContainer(modifier = Modifier.fillMaxSize()) { cellWidth, cellHeight ->
            ProvideTileGrid(cellWidth, cellHeight) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 第一行：时间 + 天气
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ClockTile(
                            time = uiState.currentTime,
                            date = uiState.currentDate,
                            lunarDate = uiState.lunarDate
                        )

                        WeatherTile(
                            temperature = uiState.temperature,
                            icon = uiState.weatherIcon
                        )
                    }

                    // 第二行：日历 + 待办 + 新闻
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalendarTile(
                            month = uiState.currentMonth,
                            day = uiState.currentDay
                        )

                        TodoTile(
                            items = uiState.todoItems.map { it.title }
                        )

                        NewsTile(
                            newsItems = uiState.newsItems.map {
                                it.title to it.content
                            }
                        )
                    }
                }
            }
        }
    }
}
```

---

## ✨ 进阶功能

### 自定义瓷砖

如果预设组件不满足需求，使用自定义瓷砖：

```kotlin
CustomSquareTile(
    backgroundColor = Color(0xFF9C27B0)
) {
    // 完全自定义的内容
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "我的收藏",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Light
        )
        Text(
            text = "128 个项目",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}
```

---

## 🎯 布局规则

### 网格系统
- 总列数：6
- 瓷砖间距：8dp
- 内边距：16dp
- 单元格：自动计算

### 瓷砖尺寸
| 组件 | 尺寸 | 说明 |
|-----|------|------|
| ClockTile | 4×2 | 时钟瓷砖 |
| WeatherTile | 2×2 | 天气瓷砖 |
| CalendarTile | 2×2 | 日历瓷砖 |
| TodoTile | 2×4 | 待办瓷砖（竖长条） |
| NewsTile | 4×4 | 新闻瓷砖（大方块） |

### 布局示例

```
第一行（高度 2 单元格）：
┌─────────────┬─────┐
│   ClockTile │Weather│
│    (4×2)    │(2×2)│
└─────────────┴─────┘

第二行（高度 4 单元格）：
┌─────┬─────┬─────────┐
│Cal- │Todo │  News   │
│endar│ Tile│  Tile   │
│(2×2)│(2×4)│ (4×4)   │
│     │     │         │
└─────┴─────┴─────────┘
```

---

## ❓ 常见问题

### Q: 如何禁用动画？
```kotlin
WeatherTile(
    temperature = 22,
    enableAnimation = false  // 禁用脉冲动画
)
```

### Q: 如何修改动画间隔？
```kotlin
NewsTile(
    newsItems = listOf(...),
    slideIntervalMillis = 5000  // 5秒切换一次
)
```

### Q: 如何获取点击事件？
```kotlin
CustomSquareTile(
    backgroundColor = Color(0xFF9C27B0),
    modifier = Modifier.clickable { /* 处理点击 */ }
) {
    // 内容
}
```

---

## 📚 下一步

- 查看 [组件库完整文档](./TILE_COMPONENTS.md)
- 了解 [架构设计](./ARCHITECTURE.md)
- 阅读 [开发指南](./DEVELOPMENT.md)

---

**5 分钟上手，终身受益！** 🎉
