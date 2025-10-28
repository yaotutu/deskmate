# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Deskmate 是一个基于 Kotlin + Jetpack Compose 的现代化 Android 桌面小部件应用，采用 **Windows Phone 风格的动态瓷砖设计**和 MVVM 架构模式。

### 核心特性

- 🎨 **Windows Phone Metro 设计语言** - 经典的扁平化动态瓷砖
- 🔄 **流畅动画效果** - 翻转、脉冲、滑动等 Metro 风格动画
- 🧩 **三层组件架构** - 基础层、组件层、页面层清晰分离
- 📊 **数据驱动开发** - 开发者只需关注数据，布局和动画自动处理
- ⚡ **代码量减少 90%** - 从 30+ 行代码简化到 4 行

## 核心技术栈

- **语言**: Kotlin 2.0.21 (JDK 11)
- **UI 框架**: Jetpack Compose + Material3
- **架构**: MVVM (Model-View-ViewModel)
- **异步**: Kotlin Coroutines + Flow
- **导航**: Navigation Compose
- **依赖管理**: Gradle Version Catalog (libs.versions.toml)

## 常用开发命令

### 构建与清理
```bash
# 清理构建产物
./gradlew clean

# 停止 Gradle daemon
./gradlew --stop

# 构建 Debug APK
./gradlew assembleDebug

# 构建并刷新依赖
./gradlew build --refresh-dependencies
```

### 测试
```bash
# 运行单元测试
./gradlew test

# 运行 instrumentation 测试
./gradlew connectedAndroidTest
```

### 其他
```bash
# 查看所有可用任务
./gradlew tasks

# 查看项目目录树
tree -I 'build|.gradle|.idea'
```

## 项目架构

### 三层瓷砖组件系统 ⭐ 核心架构

```
┌─────────────────────────────────────────┐
│  Layer 3: 页面层 (Page Layer)           │
│  - DashboardScreen.kt                   │
│  - 职责: 业务逻辑、数据绑定              │
└──────────────┬──────────────────────────┘
               │ uses
┌──────────────▼──────────────────────────┐
│  Layer 2: 组件层 (Component Layer) ⭐   │
│  - TileComponents.kt (高级 API)         │
│  - ClockTile, WeatherTile, etc.         │
│  - 职责: 数据驱动、开箱即用              │
└──────────────┬──────────────────────────┘
               │ uses
┌──────────────▼──────────────────────────┐
│  Layer 1: 基础层 (Foundation Layer)     │
│  - TileCard.kt (瓷砖容器)               │
│  - TileAnimation.kt (动画效果)          │
│  - TileGrid.kt (网格系统)               │
│  - 职责: 布局计算、动画实现              │
└─────────────────────────────────────────┘
```

### 项目目录结构

```
app/src/main/java/top/yaotutu/deskmate/
├── data/                           # 数据层
│   └── model/                     # 数据模型
│       ├── NewsItem.kt            # 新闻数据模型
│       ├── Notification.kt        # 通知数据模型
│       └── TodoItem.kt            # 待办事项数据模型
├── presentation/                   # 表现层
│   ├── ui/
│   │   ├── component/             # UI 组件 ⭐ 核心
│   │   │   ├── TileAnimation.kt   # 动画组件 (Flip, Pulse, Slide)
│   │   │   ├── TileCard.kt        # 基础瓷砖容器 (7种尺寸)
│   │   │   ├── TileComponents.kt  # 高级组件库 ⭐ 主要使用
│   │   │   ├── TileGrid.kt        # 网格系统 (6列自适应)
│   │   │   ├── TileInteraction.kt # 交互动效 (可选扩展)
│   │   │   └── MetroEnhancements.kt # 增强组件 (状态栏/角标/新瓷砖)
│   │   ├── screen/                # 页面级 Composable
│   │   │   ├── DashboardScreen.kt # 主页面
│   │   │   └── InteractionDemoScreen.kt # 交互演示页面
│   │   └── theme/                 # Material3 主题配置
│   │       ├── Color.kt           # Metro 配色定义
│   │       ├── Type.kt            # 字体配置
│   │       └── MetroTheme.kt      # 主题系统
│   └── viewmodel/                 # ViewModel 层
│       └── DashboardViewModel.kt  # UI 状态管理
└── MainActivity.kt                 # 应用入口

docs/                               # 📚 完整文档
├── README.md                      # 项目概述
├── QUICK_START.md                 # 5分钟快速开始
├── TILE_COMPONENTS.md             # 组件库API文档
├── ARCHITECTURE.md                # 架构设计详解
└── DEVELOPMENT.md                 # 开发指南
```

**注意**：项目目前为**纯展示模式**，专注于 Windows Phone 风格的动态瓷砖展示，不包含编辑功能。

### MVVM 数据流向

```
View (Screen) → ViewModel → Repository (未来) → DataSource (未来)
      ↑              ↓
      └─── StateFlow ──┘
```

1. **View 层**: Jetpack Compose 声明式 UI
2. **ViewModel 层**: StateFlow 管理 UI 状态
3. **Model 层**: 数据模型和业务逻辑（按需扩展）

## 可选依赖启用

项目中一些常用库默认被注释,按需启用:

### 启用网络请求 (Retrofit)
1. 在 `app/build.gradle.kts` 取消注释:
   - `implementation(libs.retrofit)`
   - `implementation(libs.retrofit.converter.kotlinx.serialization)`
   - `implementation(libs.okhttp)`
   - `implementation(libs.okhttp.logging.interceptor)`
   - `implementation(libs.kotlinx.serialization.json)`
2. 创建 API 接口: `data/remote/ApiService.kt`
3. 配置 Retrofit: `data/remote/RetrofitClient.kt`

### 启用数据库 (Room)
1. 在 `gradle/libs.versions.toml` 取消注释:
   - `# ksp = { id = "com.google.devtools.ksp", version = "2.0.21-1.0.28" }`
2. 在 `app/build.gradle.kts` 取消注释:
   - `// alias(libs.plugins.ksp)` (plugins 块中)
   - Room 相关依赖
3. 创建实体、DAO 和 Database 类

### 启用图片加载 (Coil)
在 `app/build.gradle.kts` 取消注释:
- `// implementation(libs.coil.compose)`

## 代码规范

- **包命名**: 全小写,无下划线 (如 `top.yaotutu.deskmate.data.model`)
- **类命名**: 大驼峰 PascalCase (如 `UserViewModel`)
- **函数/变量**: 小驼峰 camelCase (如 `loadUserData`)
- **Composable 函数**: 大驼峰 PascalCase (如 `UserScreen`)
- **常量**: 全大写 + 下划线 (如 `MAX_RETRY_COUNT`)
- 遵循 [Kotlin 官方编码规范](https://kotlinlang.org/docs/coding-conventions.html)

## 瓷砖组件使用指南 ⭐ 重点

### 核心理念：只关注数据，不关注布局

开发者应该**始终使用 Layer 2 高级组件库**（TileComponents.kt），而不是直接使用 Layer 1 底层 API。

### 正确使用方式

```kotlin
// ✅ 推荐：使用高级组件库 (TileComponents.kt)
@Composable
fun MyScreen(viewModel: MyViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1E1E1E)).padding(8.dp)) {
        TileGridContainer(Modifier.fillMaxSize()) { cellWidth, cellHeight ->
            ProvideTileGrid(cellWidth, cellHeight) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // 只需传递数据，一切自动处理
                        ClockTile(
                            time = uiState.currentTime,
                            date = uiState.currentDate,
                            lunarDate = uiState.lunarDate
                        )
                        WeatherTile(temperature = uiState.temperature)
                    }
                }
            }
        }
    }
}
```

### 错误使用方式

```kotlin
// ❌ 不推荐：直接使用底层 API (TileCard.kt)
MediumWideTile(
    backgroundColor = Color(0xFF0078D7),
    cellWidth = cellWidth,  // 需要手动传递
    cellHeight = cellHeight // 需要手动传递
) {
    FlipTileAnimation(  // 需要手动配置动画
        frontContent = { /* 30+ 行布局代码 */ },
        backContent = { /* 30+ 行布局代码 */ }
    )
}
```

### 可用的瓷砖组件

| 组件 | 尺寸 | 动画 | 用途 | 用法 |
|-----|------|------|------|------|
| **ClockTile** | 4×2 | 翻转 | 时钟显示 | `ClockTile(time, date, lunarDate)` |
| **WeatherTile** | 2×2 | 脉冲 | 天气信息 | `WeatherTile(temperature, icon)` |
| **CalendarTile** | 2×2 | 无 | 日历日期 | `CalendarTile(month, day)` |
| **TodoTile** | 2×4 | 无 | 待办列表 | `TodoTile(items)` |
| **NewsTile** | 4×4 | 滑动 | 新闻轮播 | `NewsTile(newsItems)` |

### 自定义内容瓷砖

如果预设组件不满足需求，使用自定义瓷砖：

```kotlin
CustomSquareTile(backgroundColor = Color(0xFF9C27B0)) {
    // 完全自定义的内容
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White)
        Text("自定义", color = Color.White)
    }
}
```

## Metro 设计规范 🎨 必须遵守

### 字体规范

```kotlin
// ✅ 推荐：使用 Thin/ExtraLight 字重
Text(
    text = "10:12",
    fontSize = 96.sp,
    fontWeight = FontWeight.Thin,  // 主要内容
    color = Color.White
)

Text(
    text = "星期一",
    fontSize = 20.sp,
    fontWeight = FontWeight.Light,  // 次要内容
    color = Color.White
)

// ❌ 禁止：使用粗字体
Text(fontWeight = FontWeight.Bold)  // Metro 风格不使用粗体
```

### 配色规范

```kotlin
// ✅ 使用 Metro 官方配色（高饱和度纯色）
object MetroColors {
    val Blue = Color(0xFF0078D7)      // 时钟
    val Orange = Color(0xFFFF8C00)    // 天气
    val Green = Color(0xFF00A300)     // 日历
    val Purple = Color(0xFFAA00FF)    // 待办
    val Red = Color(0xFFE51400)       // 新闻
}

// ❌ 禁止：低饱和度、渐变、阴影
Color(0xFF888888)  // 灰色不符合 Metro 风格
```

### 间距规范

```kotlin
// 瓷砖间距：8dp
Column(verticalArrangement = Arrangement.spacedBy(8.dp))

// 瓷砖内边距：16dp (已内置在 TileCard 中)
// 屏幕边距：8dp
Box(modifier = Modifier.padding(8.dp))
```

## ViewModel + StateFlow 模式

```kotlin
// presentation/viewmodel/UserViewModel.kt
class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = repository.getData()
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }
}

// presentation/ui/screen/UserScreen.kt
@Composable
fun UserScreen(viewModel: UserViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> LoadingIndicator()
        is UiState.Success -> SuccessContent(data)
        is UiState.Error -> ErrorMessage(error)
    }
}
```

## 项目配置

- **namespace**: `top.yaotutu.deskmate`
- **minSdk**: 24 (Android 7.0)
- **targetSdk**: 36
- **compileSdk**: 36
- **applicationId**: `top.yaotutu.deskmate`

## 日志

项目使用 Timber 进行日志记录。在 Application 类中初始化:
```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
```

## 开发最佳实践

### 1. 组件使用原则

- ✅ **始终使用高级组件库** (TileComponents.kt) 而不是底层 API
- ✅ **只传递数据**，让组件自动处理布局和动画
- ✅ **使用 ProvideTileGrid** 传递网格参数
- ❌ 不要直接使用 TileCard.kt 中的组件
- ❌ 不要手动计算尺寸和间距

### 2. 状态管理原则

```kotlin
// ✅ 推荐：暴露只读 StateFlow
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    // 使用 update 函数更新状态
    fun updateData() {
        _uiState.update { it.copy(newValue = "updated") }
    }
}

// ❌ 不推荐：直接暴露 MutableStateFlow
val uiState = MutableStateFlow(MyUiState())
```

### 3. Metro 设计原则

- ✅ 使用 **FontWeight.Thin/ExtraLight** 字体
- ✅ 使用 **高饱和度纯色** 背景
- ✅ 使用 **大字号**（主要内容 56sp+）
- ❌ 禁止使用粗体、渐变、阴影
- ❌ 禁止使用低饱和度灰色

### 4. 其他注意事项

1. **依赖管理**: 使用 Version Catalog 统一管理，不要硬编码版本号
2. **预览调试**: 使用 `@Preview` 注解提升开发效率
3. **日志记录**: 使用 Timber 而不是 `println` 或 `Log`
4. **协程作用域**: 在 ViewModel 中使用 `viewModelScope`，不要在 Composable 中直接启动协程

## 文档资源

详细文档请查看 `docs/` 目录：

- **[docs/README.md](./docs/README.md)** - 项目概述和快速开始
- **[docs/QUICK_START.md](./docs/QUICK_START.md)** - 5分钟快速开始指南
- **[docs/TILE_COMPONENTS.md](./docs/TILE_COMPONENTS.md)** - 组件库完整API文档
- **[docs/ARCHITECTURE.md](./docs/ARCHITECTURE.md)** - 架构设计详解
- **[docs/DEVELOPMENT.md](./docs/DEVELOPMENT.md)** - 开发指南和最佳实践

## 添加新功能的流程

### 添加新瓷砖组件

1. 在 `TileComponents.kt` 中添加新组件
2. 使用 CompositionLocal 获取网格参数
3. 选择合适的底层瓷砖容器（SquareTile, MediumWideTile, etc.）
4. 遵循 Metro 设计规范
5. 在 Screen 中使用新组件

示例：

```kotlin
// 1. 在 TileComponents.kt 中添加
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
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text("🎵", fontSize = 64.sp)
            Text(songName, fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
            Text(artist, fontSize = 16.sp, fontWeight = FontWeight.ExtraLight, color = Color.White.copy(0.9f))
        }
    }
}

// 2. 在 Screen 中使用
MusicTile(
    songName = uiState.currentSong,
    artist = uiState.currentArtist
)
```

## 常见问题

### Q: 瓷砖尺寸不正确？
**A**: 确保在 `TileGridContainer` 和 `ProvideTileGrid` 中正确使用组件

### Q: 动画不流畅？
**A**: 检查是否在主线程执行耗时操作，使用 `viewModelScope.launch` 处理异步任务

### Q: 如何调试 UI？
**A**: 使用 ADB 截图查看实际效果：
```bash
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png .
```

## 组件说明

### 核心组件（必需）

- **TileComponents.kt** - 高级组件库，包含所有预设瓷砖（ClockTile, WeatherTile等）
- **TileCard.kt** - 基础瓷砖容器，提供7种尺寸
- **TileGrid.kt** - 响应式网格系统，自动计算瓷砖尺寸
- **TileAnimation.kt** - 动画组件（翻转、脉冲、滑动）

### 扩展组件（可选）

- **TileInteraction.kt** - 交互动效（按压、弹跳、抖动等），可用于增强用户体验
- **MetroEnhancements.kt** - 增强功能（状态栏、角标、新瓷砖类型），可按需使用
- **MetroTheme.kt** - 主题系统（深色/浅色/高对比度），支持主题切换
- **InteractionDemoScreen.kt** - 交互效果演示页面，展示所有可用动效

### 数据模型

- **NewsItem.kt** - 新闻数据模型
- **Notification.kt** - 通知数据模型
- **TodoItem.kt** - 待办事项数据模型

所有组件遵循 **Windows Phone Metro 设计语言**，保持简洁、扁平、高饱和度的视觉风格。
