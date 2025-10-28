# Deskmate 架构设计文档

## 📐 总体架构

Deskmate 采用经典的 MVVM（Model-View-ViewModel）架构模式，并在此基础上实现了三层瓷砖组件系统。

---

## 🏛 MVVM 架构

```
┌─────────────────────────────────────────┐
│              View Layer                 │
│        (Jetpack Compose)                │
│  - Screen Composables                   │
│  - UI Components                        │
└──────────────┬──────────────────────────┘
               │ observes StateFlow/State
               ↓
┌─────────────────────────────────────────┐
│           ViewModel Layer               │
│  - UI State Management                  │
│  - Business Logic                       │
│  - Coroutines Orchestration             │
└──────────────┬──────────────────────────┘
               │ calls repository
               ↓
┌─────────────────────────────────────────┐
│            Model Layer                  │
│  - Repository                           │
│  - Data Sources (Local/Remote)          │
│  - Data Models                          │
└─────────────────────────────────────────┘
```

### 数据流向

1. **View → ViewModel**: 用户操作触发 ViewModel 方法
2. **ViewModel → Repository**: ViewModel 调用 Repository 获取数据
3. **Repository → DataSource**: Repository 从本地或远程数据源获取数据
4. **DataSource → Repository**: 数据源返回数据
5. **Repository → ViewModel**: Repository 返回处理后的数据
6. **ViewModel → View**: ViewModel 更新 StateFlow，View 自动重组

---

## 🧩 三层瓷砖组件系统

这是 Deskmate 的核心创新，将瓷砖组件分为三个层次：

```
┌─────────────────────────────────────────┐
│         Layer 3: Page Layer             │
│        (业务层 - 页面级组件)              │
│                                         │
│  - DashboardScreen.kt                   │
│  - 其他 Screen Composables              │
│  - 负责：业务逻辑、数据绑定              │
└──────────────┬──────────────────────────┘
               │ uses
               ↓
┌─────────────────────────────────────────┐
│      Layer 2: Component Layer           │
│      (高级 API - 预设组件库) ⭐          │
│                                         │
│  - TileComponents.kt                    │
│  - ClockTile, WeatherTile, etc.         │
│  - 负责：数据驱动、开箱即用              │
└──────────────┬──────────────────────────┘
               │ uses
               ↓
┌─────────────────────────────────────────┐
│       Layer 1: Foundation Layer         │
│      (底层 API - 基础瓷砖)               │
│                                         │
│  - TileCard.kt (瓷砖容器)               │
│  - TileAnimation.kt (动画效果)          │
│  - TileGrid.kt (网格系统)               │
│  - 负责：布局计算、动画实现              │
└─────────────────────────────────────────┘
```

### Layer 1: Foundation Layer（基础层）

**职责**：提供底层的瓷砖容器、网格系统和动画效果。

#### TileGrid.kt - 网格系统
```kotlin
object TileGrid {
    const val TOTAL_COLUMNS = 6
    const val TILE_GAP = 8.dp
    const val CONTAINER_PADDING = 16.dp

    fun calculateTileWidth(cellWidth: Dp, gridColumns: Int): Dp
    fun calculateTileHeight(cellHeight: Dp, gridRows: Int): Dp
}
```

**特点**：
- 6 列网格系统
- 自动计算单元格尺寸
- 固定间距和内边距

#### TileCard.kt - 瓷砖容器
```kotlin
// 5 种固定尺寸的瓷砖容器
@Composable fun SquareTile()        // 2×2 正方形
@Composable fun MediumWideTile()    // 4×2 横条
@Composable fun TallTile()          // 2×4 竖长条
@Composable fun LargeTile()         // 4×4 大方块
@Composable fun FullWideTile()      // 6×1 全宽横条
```

**特点**：
- 固定尺寸，自动计算宽高
- 统一的圆角和背景色
- 统一的内边距（16dp）

#### TileAnimation.kt - 动画效果
```kotlin
@Composable fun FlipTileAnimation()   // 3D 翻转动画
@Composable fun PulseTileAnimation()  // 脉冲缩放动画
@Composable fun SlideTileAnimation()  // 滑动轮播动画
```

**特点**：
- 可配置的动画参数
- 自动循环播放
- 平滑的过渡效果

---

### Layer 2: Component Layer（组件层）⭐

**职责**：提供高级的、开箱即用的瓷砖组件，开发者只需传递数据。

#### 核心设计理念

```kotlin
// ❌ 使用前（底层 API）- 需要 30+ 行代码
MediumWideTile(
    backgroundColor = Color(0xFF0078D7),
    cellWidth = cellWidth,
    cellHeight = cellHeight
) {
    FlipTileAnimation(
        frontContent = { /* 复杂的布局代码 */ },
        backContent = { /* 复杂的布局代码 */ }
    )
}

// ✅ 使用后（高级 API）- 只需 4 行代码
ClockTile(
    time = "10:12",
    date = "星期一, 10月 27日",
    lunarDate = "农历八月廿一"
)
```

#### CompositionLocal 依赖注入

```kotlin
val LocalCellWidth = compositionLocalOf<Dp> { error("CellWidth not provided") }
val LocalCellHeight = compositionLocalOf<Dp> { error("CellHeight not provided") }

@Composable
fun ProvideTileGrid(
    cellWidth: Dp,
    cellHeight: Dp,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCellWidth provides cellWidth,
        LocalCellHeight provides cellHeight
    ) {
        content()
    }
}
```

**优势**：
- 自动传递网格参数，无需手动传递
- 组件内部自动获取 `cellWidth` 和 `cellHeight`
- 简化组件调用

#### 预设组件库

| 组件 | 尺寸 | 动画 | 用途 |
|-----|------|------|------|
| ClockTile | 4×2 | 翻转 | 时钟显示 |
| WeatherTile | 2×2 | 脉冲 | 天气信息 |
| CalendarTile | 2×2 | 无 | 日历日期 |
| TodoTile | 2×4 | 无 | 待办列表 |
| NewsTile | 4×4 | 滑动 | 新闻轮播 |

#### 自定义组件支持

```kotlin
@Composable
fun CustomSquareTile(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
)
```

**提供 4 种自定义瓷砖**：
- CustomSquareTile (2×2)
- CustomMediumWideTile (4×2)
- CustomTallTile (2×4)
- CustomLargeTile (4×4)

---

### Layer 3: Page Layer（页面层）

**职责**：组合瓷砖组件，实现具体的业务页面。

#### DashboardScreen.kt

```kotlin
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier.fillMaxSize().background(Color(0xFF1E1E1E)).padding(8.dp)) {
        TileGridContainer(Modifier.fillMaxSize()) { cellWidth, cellHeight ->
            ProvideTileGrid(cellWidth, cellHeight) {
                // 组合各种瓷砖组件
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ClockTile(...)
                        WeatherTile(...)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        CalendarTile(...)
                        TodoTile(...)
                        NewsTile(...)
                    }
                }
            }
        }
    }
}
```

**特点**：
- 只关注数据和布局组合
- 不关心尺寸计算和动画实现
- 代码简洁清晰

---

## 🎨 Metro 设计系统

### 设计原则

1. **内容优先**（Content, not Chrome）
   - 大字体、粗线条
   - 去除不必要的装饰

2. **简洁扁平**（Flat Design）
   - 纯色背景
   - 无阴影、无渐变
   - 2dp 圆角

3. **流畅动画**（Smooth Animations）
   - 3D 翻转
   - 呼吸脉冲
   - 平滑滑动

### 字体规范

| 场景 | 字号 | 字重 | 用途 |
|-----|------|------|------|
| 超大数字 | 96sp | Thin | 时间显示 |
| 大数字 | 88sp | Thin | 日历日期 |
| 图标 | 72sp | - | 天气图标 |
| 温度 | 56sp | Thin | 温度数值 |
| 标题 | 28sp | Light | 新闻标题 |
| 正文 | 18-20sp | ExtraLight | 日期、待办 |

**原则**：
- 主要内容使用 `FontWeight.Thin`
- 次要内容使用 `FontWeight.Light` 或 `FontWeight.ExtraLight`
- 不使用 `FontWeight.Bold` 或更粗的字体

### 配色规范

```kotlin
object MetroColors {
    val Blue = Color(0xFF0078D7)      // 时钟、主要操作
    val Orange = Color(0xFFFF8C00)    // 天气、警告
    val Green = Color(0xFF00A300)     // 日历、成功
    val Purple = Color(0xFFAA00FF)    // 待办、强调
    val Red = Color(0xFFE51400)       // 新闻、重要信息
    val Teal = Color(0xFF00ABA9)      // 可选配色
    val Lime = Color(0xFF8CBF26)      // 可选配色
    val Pink = Color(0xFFE3008C)      // 可选配色
}
```

**原则**：
- 使用高饱和度的纯色
- 避免渐变和半透明（除文字）
- 深色背景 `#1E1E1E`

### 间距规范

```kotlin
object MetroSpacing {
    val TileGap = 8.dp          // 瓷砖间距
    val TilePadding = 16.dp     // 瓷砖内边距
    val ScreenPadding = 8.dp    // 屏幕边距
}
```

---

## 🔄 状态管理

### ViewModel + StateFlow 模式

```kotlin
class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        // 初始化数据
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // 异步加载数据
            _uiState.update { it.copy(currentTime = getCurrentTime()) }
        }
    }
}

data class DashboardUiState(
    val currentTime: String = "00:00",
    val currentDate: String = "",
    val temperature: Int = 0,
    // ...
)
```

### 数据流向

```
User Action → ViewModel Method → Repository
                ↓
        Update _uiState
                ↓
        uiState emits
                ↓
        View collectAsState
                ↓
        View Recompose
```

---

## 🚀 扩展性设计

### 添加新的瓷砖组件

#### 步骤 1: 确定组件需求
- 确定瓷砖尺寸（2×2, 4×2, 2×4, 4×4）
- 确定是否需要动画
- 确定显示的数据类型

#### 步骤 2: 在 TileComponents.kt 添加组件
```kotlin
@Composable
fun MusicTile(
    songName: String,
    artist: String,
    backgroundColor: Color = Color(0xFF00ABA9),
    modifier: Modifier = Modifier
) {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTile(backgroundColor, cellWidth, cellHeight, modifier) {
        // 自定义布局
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(songName, fontSize = 24.sp, fontWeight = FontWeight.Light)
            Text(artist, fontSize = 18.sp, fontWeight = FontWeight.ExtraLight)
        }
    }
}
```

#### 步骤 3: 在 Screen 中使用
```kotlin
MusicTile(
    songName = uiState.currentSong,
    artist = uiState.currentArtist
)
```

### 添加新的动画效果

在 `TileAnimation.kt` 中添加新的动画组件：

```kotlin
@Composable
fun RotateTileAnimation(
    content: @Composable () -> Unit,
    rotationSpeed: Long = 2000,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(rotationSpeed.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = modifier.rotate(rotation)) {
        content()
    }
}
```

---

## 📦 依赖管理

### Version Catalog

项目使用 Gradle Version Catalog 统一管理依赖：

```toml
# gradle/libs.versions.toml
[versions]
kotlin = "2.0.21"
compose = "2024.11.00"

[libraries]
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "compose" }
androidx-lifecycle-viewmodel = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version = "2.8.7" }
```

### 核心依赖

- **Jetpack Compose**: 声明式 UI 框架
- **Material3**: UI 组件库
- **ViewModel**: 状态管理
- **Coroutines + Flow**: 异步编程
- **Navigation Compose**: 页面导航

---

## 🎯 最佳实践

### 1. 组件开发
- ✅ 使用高级组件库（Layer 2）
- ✅ 只传递数据，不关心布局
- ❌ 避免直接使用底层 API（Layer 1）

### 2. 状态管理
- ✅ 使用 StateFlow 暴露状态
- ✅ 使用 viewModelScope 处理协程
- ❌ 避免在 Composable 中直接发起异步操作

### 3. 布局设计
- ✅ 使用 TileGridContainer + ProvideTileGrid
- ✅ 使用 Row + Column 组合瓷砖
- ✅ 使用 Arrangement.spacedBy(8.dp) 设置间距
- ❌ 避免嵌套过深的布局

### 4. Metro 设计
- ✅ 使用 Thin/ExtraLight 字重
- ✅ 使用高饱和度纯色
- ✅ 使用大字号和宽松间距
- ❌ 避免阴影、渐变、边框

---

## 📊 性能优化

### Compose 优化
1. **稳定参数**：确保传递给 Composable 的参数是稳定的（Stable/Immutable）
2. **记忆化**：使用 `remember` 缓存计算结果
3. **派生状态**：使用 `derivedStateOf` 避免不必要的重组
4. **跳过重组**：使用 `@Stable` 或 `@Immutable` 注解

### 动画优化
1. **硬件加速**：所有动画默认使用硬件加速
2. **帧率控制**：动画间隔不低于 16ms（60fps）
3. **资源释放**：使用 `LaunchedEffect` 管理动画生命周期

---

## 🧪 测试策略

### 单元测试
- ViewModel 逻辑测试
- Repository 数据测试
- 工具函数测试

### UI 测试
- Compose 组件测试
- 页面交互测试
- 导航流程测试

---

**架构简洁、扩展灵活、性能优异！** 🎯
