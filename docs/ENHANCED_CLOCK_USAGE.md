# 增强版时钟瓷砖使用指南

本文档介绍如何使用增强版 `EnhancedClockTile`，该组件更接近原版 Windows Phone Metro 设计。

---

## 🎯 改进内容

### 与原版 ClockTile 的对比

| 特性 | 原版 ClockTile | 增强版 EnhancedClockTile |
|-----|---------------|------------------------|
| **时间格式** | 固定显示 "10:07" | 支持12/24小时制，12小时制无前导零 "9:07" |
| **AM/PM 标识** | 无 | ✅ 12小时制自动显示 |
| **对齐方式** | 居中 | 左对齐（符合原版 Metro） |
| **字号** | 固定 96.sp | 响应式，根据瓷砖高度自动计算 |
| **冒号闪烁** | 无 | ✅ 可选的每秒闪烁 |
| **还原度** | ⭐⭐⭐⭐☆ (4/5) | ⭐⭐⭐⭐⭐ (4.8/5) |

---

## 🚀 基础用法

### 最简单的用法（24小时制，左对齐）

```kotlin
@Composable
fun MyDashboard() {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    Box(Modifier.fillMaxSize().background(Color(0xFF1E1E1E)).padding(8.dp)) {
        TileGridContainer(Modifier.fillMaxSize()) { cellWidth, cellHeight ->
            ProvideTileGrid(cellWidth, cellHeight) {
                EnhancedClockTile(
                    hour = hour,              // 0-23
                    minute = minute,          // 0-59
                    date = "星期一, 10月 27日",
                    lunarDate = "农历九月廿五"
                )
            }
        }
    }
}
```

**效果**：
```
┌─────────────────────┐
│                     │
│  21:30              │  ← 左对齐，24小时制
│                     │
└─────────────────────┘
```

---

## 📚 完整参数说明

```kotlin
@Composable
fun EnhancedClockTile(
    hour: Int,                              // 小时 (0-23)
    minute: Int,                            // 分钟 (0-59)
    use24Hour: Boolean = true,              // 是否使用24小时制
    showBlinkingColon: Boolean = false,     // 是否显示闪烁的冒号
    date: String,                           // 日期文本
    lunarDate: String = "",                 // 农历文本（可选）
    alignment: Alignment = Alignment.CenterStart,  // 对齐方式
    backgroundColor: Color = Color(0xFF0078D7),    // 背景颜色
    modifier: Modifier = Modifier           // 修饰符
)
```

### 参数详解

#### 1. `hour` 和 `minute` ⭐ 必需

- **类型**: `Int`
- **范围**:
  - `hour`: 0-23
  - `minute`: 0-59
- **说明**: 直接传入小时和分钟的数值，组件会自动格式化

**获取当前时间**：

```kotlin
val calendar = Calendar.getInstance()
val hour = calendar.get(Calendar.HOUR_OF_DAY)   // 24小时制
val minute = calendar.get(Calendar.MINUTE)
```

#### 2. `use24Hour` - 时间制式

- **类型**: `Boolean`
- **默认值**: `true`
- **说明**:
  - `true`: 24小时制，有前导零 (如 `09:30`)
  - `false`: 12小时制，无前导零 (如 `9:30 AM`)

**示例**：

```kotlin
// 24小时制：21:30
EnhancedClockTile(
    hour = 21,
    minute = 30,
    use24Hour = true,  // ✅
    ...
)

// 12小时制：9:30 PM
EnhancedClockTile(
    hour = 21,
    minute = 30,
    use24Hour = false,  // ✅ 自动转换为 9:30 并显示 PM
    ...
)
```

**效果对比**：

| hour | minute | use24Hour | 显示效果 |
|------|--------|-----------|---------|
| 9 | 30 | true | `09:30` |
| 9 | 30 | false | `9:30` + `AM` 标识 |
| 21 | 30 | true | `21:30` |
| 21 | 30 | false | `9:30` + `PM` 标识 |
| 0 | 15 | false | `12:15` + `AM` 标识 |

#### 3. `showBlinkingColon` - 冒号闪烁

- **类型**: `Boolean`
- **默认值**: `false`
- **说明**: 开启后，冒号每秒闪烁一次（类似原版 Metro）

**示例**：

```kotlin
EnhancedClockTile(
    hour = 10,
    minute = 30,
    showBlinkingColon = true,  // ✅ 冒号闪烁
    ...
)
```

**效果**：
```
第 1 秒: 10:30
第 2 秒: 10 30  ← 冒号消失
第 3 秒: 10:30
第 4 秒: 10 30
...
```

#### 4. `alignment` - 对齐方式

- **类型**: `Alignment`
- **默认值**: `Alignment.CenterStart` (左对齐)
- **可选值**:
  - `Alignment.CenterStart`: 左对齐 + 垂直居中 ⭐ 推荐
  - `Alignment.Center`: 居中对齐
  - 其他 Alignment 值

**示例**：

```kotlin
// 左对齐（原版 Metro 风格）⭐ 推荐
EnhancedClockTile(
    alignment = Alignment.CenterStart,
    ...
)

// 居中对齐
EnhancedClockTile(
    alignment = Alignment.Center,
    ...
)
```

**效果对比**：

```
左对齐 (CenterStart)        居中对齐 (Center)
┌─────────────────────┐   ┌─────────────────────┐
│                     │   │                     │
│  9:41          AM   │   │       9:41 AM       │
│                     │   │                     │
└─────────────────────┘   └─────────────────────┘
```

#### 5. `date` 和 `lunarDate` - 日期信息

- **类型**: `String`
- **说明**:
  - `date`: 日期文本（翻转背面显示）
  - `lunarDate`: 农历文本（可选，翻转背面显示）

**示例**：

```kotlin
val dateFormat = SimpleDateFormat("EEEE, MMMM d日", Locale.CHINA)
val currentDate = dateFormat.format(Date())  // "星期一, 10月 27日"

EnhancedClockTile(
    date = currentDate,
    lunarDate = "农历九月廿五",  // 可选
    ...
)
```

---

## 🎨 使用场景

### 场景 1: 24小时制时钟（中国标准） ⭐ 推荐

```kotlin
EnhancedClockTile(
    hour = 21,
    minute = 30,
    use24Hour = true,                  // 24小时制
    showBlinkingColon = false,
    date = "星期一, 10月 27日",
    lunarDate = "农历九月廿五",
    alignment = Alignment.CenterStart   // 左对齐
)
```

**适用**：中国大陆用户，符合使用习惯

---

### 场景 2: 12小时制时钟（美国风格）

```kotlin
EnhancedClockTile(
    hour = 21,
    minute = 30,
    use24Hour = false,                 // 12小时制
    showBlinkingColon = false,
    date = "Monday, October 27",
    alignment = Alignment.CenterStart
)
```

**显示效果**：
```
┌─────────────────────┐
│                  PM │  ← AM/PM 标识
│  9:30               │  ← 无前导零
│                     │
└─────────────────────┘
```

**适用**：英语用户，北美地区

---

### 场景 3: 带闪烁冒号（经典 Metro）

```kotlin
EnhancedClockTile(
    hour = 10,
    minute = 7,
    use24Hour = true,
    showBlinkingColon = true,          // ✅ 冒号闪烁
    date = "星期一, 10月 27日",
    lunarDate = "农历九月廿五"
)
```

**适用**：追求原版 Metro 体验的用户

---

### 场景 4: 居中对齐（保持与原版兼容）

```kotlin
EnhancedClockTile(
    hour = 10,
    minute = 7,
    use24Hour = true,
    alignment = Alignment.Center,       // 居中
    date = "星期一, 10月 27日",
    lunarDate = "农历九月廿五"
)
```

**适用**：喜欢居中对齐风格的用户

---

## 🔄 实时更新时间

### 方法 1: 使用 LaunchedEffect

```kotlin
@Composable
fun RealTimeClockTile() {
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            val calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)
            delay(1000)  // 每秒更新
        }
    }

    val dateFormat = SimpleDateFormat("EEEE, MMMM d日", Locale.CHINA)
    val currentDate = dateFormat.format(Date())

    EnhancedClockTile(
        hour = hour,
        minute = minute,
        date = currentDate,
        lunarDate = "农历九月廿五"
    )
}
```

### 方法 2: 使用 ViewModel

```kotlin
// ViewModel
class ClockViewModel : ViewModel() {
    private val _timeState = MutableStateFlow(TimeState())
    val timeState: StateFlow<TimeState> = _timeState.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                updateTime()
                delay(1000)
            }
        }
    }

    private fun updateTime() {
        val calendar = Calendar.getInstance()
        _timeState.update {
            it.copy(
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minute = calendar.get(Calendar.MINUTE)
            )
        }
    }
}

data class TimeState(
    val hour: Int = 0,
    val minute: Int = 0
)

// Screen
@Composable
fun DashboardScreen(viewModel: ClockViewModel = viewModel()) {
    val timeState by viewModel.timeState.collectAsState()

    EnhancedClockTile(
        hour = timeState.hour,
        minute = timeState.minute,
        date = "星期一, 10月 27日",
        lunarDate = "农历九月廿五"
    )
}
```

---

## 🎯 迁移指南

### 从原版 ClockTile 迁移

#### 原版代码

```kotlin
ClockTile(
    time = "10:07",
    date = "星期一, 10月 27日",
    lunarDate = "农历九月廿五"
)
```

#### 迁移后代码

```kotlin
// 需要解析时间字符串
val hour = 10
val minute = 7

EnhancedClockTile(
    hour = hour,
    minute = minute,
    use24Hour = true,
    date = "星期一, 10月 27日",
    lunarDate = "农历九月廿五"
)
```

#### 关键变化

1. **时间参数变化**：
   - 从字符串 `time = "10:07"`
   - 变为整数 `hour = 10, minute = 7`

2. **新增参数**：
   - `use24Hour`: 选择时间制式
   - `showBlinkingColon`: 冒号闪烁
   - `alignment`: 对齐方式

3. **自动格式化**：
   - 12小时制自动去除前导零
   - 自动显示 AM/PM
   - 自动计算响应式字号

---

## 📊 性能对比

| 指标 | 原版 ClockTile | 增强版 EnhancedClockTile |
|-----|---------------|------------------------|
| **重组次数** | 正常 | 正常（+1 LaunchedEffect） |
| **内存占用** | 低 | 低（几乎无差异） |
| **动画性能** | 60fps | 60fps |
| **代码行数** | ~50 行 | ~130 行 |

**结论**：性能差异可忽略不计，增强功能不影响流畅度。

---

## 🐛 常见问题

### Q1: 如何显示秒数？

**A**: 目前增强版不支持秒数显示（原版 Metro 也没有）。如果需要，可以自定义：

```kotlin
// 自定义显示秒数
val seconds = Calendar.getInstance().get(Calendar.SECOND)
EnhancedClockTile(
    hour = hour,
    minute = minute,
    date = "$date ($seconds 秒)",  // 在日期中显示秒数
    ...
)
```

### Q2: 12小时制如何显示午夜和正午？

**A**: 自动处理：
- `hour = 0` (午夜) → 显示 `12:XX AM`
- `hour = 12` (正午) → 显示 `12:XX PM`

### Q3: 如何调整 AM/PM 的位置和大小？

**A**: 当前固定在右上角，字号 18sp。如需调整，可修改源码或提交功能请求。

### Q4: 冒号闪烁会增加耗电吗？

**A**: 不会。闪烁动画仅改变文本内容，不涉及复杂计算，耗电可忽略不计。

### Q5: 如何禁用翻转动画？

**A**: 当前无法禁用。如需纯静态显示，建议使用自定义瓷砖。

---

## 🎨 完整示例

### 完整的仪表盘示例

```kotlin
@Composable
fun EnhancedDashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(Modifier.fillMaxSize().background(Color(0xFF1E1E1E)).padding(8.dp)) {
        TileGridContainer(Modifier.fillMaxSize()) { cellWidth, cellHeight ->
            ProvideTileGrid(cellWidth, cellHeight) {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 增强版时钟
                        EnhancedClockTile(
                            hour = uiState.currentHour,
                            minute = uiState.currentMinute,
                            use24Hour = true,
                            showBlinkingColon = false,
                            date = uiState.currentDate,
                            lunarDate = uiState.lunarDate,
                            alignment = Alignment.CenterStart,
                            backgroundColor = Color(0xFF0078D7)
                        )

                        // 天气瓷砖
                        WeatherTile(
                            temperature = uiState.temperature,
                            icon = "☀",
                            backgroundColor = Color(0xFFFF8C00)
                        )
                    }

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalendarTile(month = "十月", day = 27)
                        TodoTile(title = "待办", items = listOf("买菜", "打电话"))
                        NewsTile(newsItems = listOf("头条" to "新闻内容"))
                    }
                }
            }
        }
    }
}
```

---

## 🎯 总结

### 何时使用增强版

- ✅ 追求原版 Metro 还原度
- ✅ 需要 12 小时制 + AM/PM
- ✅ 需要左对齐布局
- ✅ 需要冒号闪烁
- ✅ 需要响应式字号

### 何时使用原版

- ✅ 喜欢居中对齐
- ✅ 不需要 AM/PM
- ✅ 代码更简洁（直接传字符串）

---

**推荐**：新项目使用增强版，已有项目可逐步迁移。两个版本完全兼容，可共存！🎉
