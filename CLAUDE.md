# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Deskmate 是一个基于 Kotlin + Jetpack Compose 的现代化 Android 桌面小部件应用，采用 **Windows Phone 风格的动态瓷砖设计**和 MVVM 架构模式。

### 核心特性

- 🎨 **Windows Phone Metro 设计语言** - 经典的扁平化动态瓷砖
- 🔄 **流畅动画效果** - 翻转、脉冲、滑动等 Metro 风格动画
- 🏭 **工厂模式 + 变体系统** - 灵活的组件注册和创建机制
- 📝 **配置驱动布局** - 通过 JSON 配置文件定义瓷砖布局
- 🎯 **多变体支持** - 每种瓷砖支持多个尺寸和样式变体
- 🧩 **自动布局引擎** - 垂直优先布局，自动计算瓷砖位置
- 📊 **数据驱动开发** - 开发者只需关注数据，布局和动画自动处理
- ⚡ **Repository 层** - 统一管理配置和数据加载

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

### 工厂模式 + 变体系统 ⭐ 核心架构

```
┌───────────────────────────────────────────────────┐
│  配置层 (Configuration Layer)                     │
│  - LayoutConfig.kt (布局配置)                     │
│  - TileConfig.kt (瓷砖配置)                       │
│  - LayoutConfigRepository.kt (配置加载)           │
│  - 职责: JSON 配置管理、默认配置                   │
└───────────────┬───────────────────────────────────┘
                │ loads
┌───────────────▼───────────────────────────────────┐
│  页面层 (Screen Layer)                            │
│  - DashboardScreen.kt (主页面)                    │
│  - InteractionDemoScreen.kt (交互演示)            │
│  - ClockComparisonScreen.kt (时钟对比)            │
│  - 职责: 加载配置、绑定 ViewModel、渲染布局        │
└───────────────┬───────────────────────────────────┘
                │ uses
┌───────────────▼───────────────────────────────────┐
│  布局引擎层 (Layout Engine Layer)                 │
│  - VerticalPriorityLayout.kt (垂直优先布局)       │
│  - TileGridContainer.kt (网格容器)                │
│  - 职责: 自动计算瓷砖位置、响应式布局               │
└───────────────┬───────────────────────────────────┘
                │ uses
┌───────────────▼───────────────────────────────────┐
│  工厂层 (Factory Layer) ⭐                        │
│  - TileFactory.kt (瓷砖工厂)                      │
│  - TileRegistry.kt (变体注册中心)                 │
│  - TileRegistryInit.kt (变体初始化)               │
│  - TileVariantSpec.kt (变体规格)                  │
│  - 职责: 根据配置创建瓷砖、变体管理、错误处理       │
└───────────────┬───────────────────────────────────┘
                │ creates
┌───────────────▼───────────────────────────────────┐
│  业务组件层 (Business Component Layer)            │
│  - tiles/clock/ (6个时钟变体)                     │
│  - tiles/common/ErrorTile.kt (错误瓷砖)           │
│  - legacy/TileComponents.kt (遗留组件)            │
│  - 职责: 具体瓷砖实现、数据绑定                    │
└───────────────┬───────────────────────────────────┘
                │ uses
┌───────────────▼───────────────────────────────────┐
│  基础层 (Foundation Layer)                        │
│  - BaseTile.kt (基础瓷砖)                         │
│  - TileCard.kt (瓷砖容器)                         │
│  - TileSpec.kt (瓷砖规格)                         │
│  - TileAnimation.kt (动画效果)                    │
│  - TileGrid.kt (网格系统)                         │
│  - 职责: 底层布局计算、动画实现、尺寸规范           │
└───────────────────────────────────────────────────┘
```

### 项目目录结构

```
app/src/main/java/top/yaotutu/deskmate/
├── data/                              # 数据层
│   ├── model/                        # 数据模型
│   │   ├── LayoutConfig.kt           # 布局配置数据类
│   │   ├── TileConfig.kt             # 瓷砖配置数据类 + TileType 枚举
│   │   ├── TileVariantSpec.kt        # 变体规格 + TileRegistry 注册中心
│   │   ├── NewsItem.kt               # 新闻数据模型
│   │   ├── Notification.kt           # 通知数据模型
│   │   └── TodoItem.kt               # 待办事项数据模型
│   └── repository/                   # Repository 层
│       └── LayoutConfigRepository.kt # 配置加载（JSON/默认）
├── navigation/                        # 导航配置
│   ├── NavGraph.kt                   # 导航图定义
│   └── Screen.kt                     # 路由配置
├── presentation/                      # 表现层
│   ├── ui/
│   │   ├── component/                # UI 组件 ⭐ 核心
│   │   │   ├── animation/           # 动画组件
│   │   │   │   └── TileAnimation.kt # Flip, Pulse, Slide 动画
│   │   │   ├── base/                # 基础组件层
│   │   │   │   ├── BaseTile.kt      # 基础瓷砖（统一容器）
│   │   │   │   ├── TileCard.kt      # 瓷砖卡片（7种尺寸）
│   │   │   │   ├── TileSpec.kt      # 瓷砖规格配置 + AnimationType
│   │   │   │   └── TileGrid.kt      # 网格系统（6列自适应）
│   │   │   ├── enhancement/         # 增强功能
│   │   │   │   └── MetroEnhancements.kt # 状态栏/角标
│   │   │   ├── factory/             # 工厂层 ⭐ 核心
│   │   │   │   ├── TileFactory.kt   # 瓷砖工厂（根据配置创建）
│   │   │   │   └── TileRegistryInit.kt # 变体注册初始化
│   │   │   ├── interaction/         # 交互动效
│   │   │   │   └── TileInteraction.kt # 按压/弹跳/抖动
│   │   │   ├── layout/              # 布局引擎
│   │   │   │   └── VerticalPriorityLayout.kt # 垂直优先布局
│   │   │   ├── legacy/              # 遗留组件（向后兼容）
│   │   │   │   └── TileComponents.kt # 旧的高级组件库
│   │   │   └── tiles/               # 业务瓷砖实现
│   │   │       ├── clock/           # 时钟瓷砖变体
│   │   │       │   ├── ClockSimpleTile.kt    # 简约 (1×1)
│   │   │       │   ├── ClockCompactTile.kt   # 紧凑 (2×1)
│   │   │       │   ├── ClockStandardTile.kt  # 标准 (2×2)
│   │   │       │   ├── ClockTallTile.kt      # 高版 (2×4)
│   │   │       │   ├── ClockDetailedTile.kt  # 详细 (4×2)
│   │   │       │   └── ClockLargeTile.kt     # 大型 (4×4)
│   │   │       └── common/          # 公共组件
│   │   │           └── ErrorTile.kt # 错误瓷砖（配置错误提示）
│   │   ├── screen/                  # 页面级 Composable
│   │   │   ├── DashboardScreen.kt   # 主页面（配置驱动）
│   │   │   ├── InteractionDemoScreen.kt # 交互演示
│   │   │   └── ClockComparisonScreen.kt # 时钟对比
│   │   └── theme/                   # Material3 主题配置
│   │       ├── Color.kt             # 基础颜色定义
│   │       ├── MetroColors.kt       # Metro 配色方案
│   │       ├── Type.kt              # 字体配置
│   │       ├── Theme.kt             # Material3 主题
│   │       ├── MetroTheme.kt        # Metro 主题系统
│   │       └── MetroEasing.kt       # Metro 缓动函数
│   └── viewmodel/                   # ViewModel 层
│       └── DashboardViewModel.kt    # UI 状态管理
└── MainActivity.kt                   # 应用入口

docs/                                  # 📚 完整文档
├── README.md                         # 项目概述
├── QUICK_START.md                    # 5分钟快速开始
├── TILE_COMPONENTS.md                # 组件库API文档
├── ARCHITECTURE.md                   # 架构设计详解
├── DEVELOPMENT.md                    # 开发指南
├── CLOCK_TILE_ANALYSIS.md            # 时钟瓷砖分析
├── ENHANCED_CLOCK_USAGE.md           # 增强时钟使用指南
├── INTERACTION_GUIDE.md              # 交互指南
└── METRO_ENHANCEMENTS.md             # Metro 增强功能
```

**注意**：项目采用 **配置驱动 + 数据绑定** 模式，通过 JSON 配置文件定义布局，ViewModel 提供动态数据。

### MVVM + 配置驱动架构

```
┌────────────────────────────────────────────────────────────┐
│  View 层 (Screen)                                          │
│  - 加载 LayoutConfig                                       │
│  - 使用 TileFactory.CreateTile() 根据配置创建瓷砖           │
└──────────────┬─────────────────────────────┬───────────────┘
               │ observes                    │ reads
┌──────────────▼──────────────┐   ┌─────────▼───────────────┐
│  ViewModel                  │   │  Repository              │
│  - StateFlow<UiState>       │   │  - loadLayoutConfig()    │
│  - 提供动态数据              │   │  - 读取 JSON 配置        │
│  （时间、天气、新闻等）       │   │  - 提供默认配置          │
└─────────────────────────────┘   └──────────────────────────┘
```

### 数据流向说明

1. **配置层**：Repository 加载 JSON 配置或使用默认配置
2. **View 层**：Screen 读取配置，遍历瓷砖列表
3. **工厂层**：TileFactory 根据 `type + variant` 创建对应的瓷砖组件
4. **数据绑定**：ViewModel 通过 StateFlow 提供实时数据（时间、天气等）
5. **自动渲染**：VerticalPriorityLayout 自动计算位置并渲染

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

### 核心理念：配置驱动 + 数据绑定

当前项目采用 **配置驱动** 模式，开发者通过 **JSON 配置文件** 或 **代码配置** 定义瓷砖布局，然后 ViewModel 提供动态数据。

### 方式一：配置驱动（推荐） ⭐

#### 1. 定义配置文件（JSON）

在 `assets/` 目录创建 `layout_config.json`：

```json
{
  "tiles": [
    { "type": "clock", "variant": "detailed", "columns": 4, "rows": 2 },
    { "type": "weather", "variant": "standard", "columns": 2, "rows": 2 },
    { "type": "calendar", "variant": "standard", "columns": 2, "rows": 2 }
  ]
}
```

#### 2. Screen 加载配置并渲染

```kotlin
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // 加载配置（JSON 或默认配置）
    val layoutConfig = remember {
        val repository = LayoutConfigRepository(context)
        repository.loadLayoutConfig() ?: repository.getDefaultLayoutConfig()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF000000)).padding(8.dp)) {
        TileGridContainer(Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns, screenHeight ->
            ProvideTileGrid(baseCellSize, dynamicGap, columns) {
                VerticalPriorityLayout(
                    tiles = layoutConfig.tiles,
                    baseCellSize = baseCellSize,
                    dynamicGap = dynamicGap,
                    columns = columns,
                    screenHeight = screenHeight
                ) { config, index ->
                    // 工厂自动创建瓷砖
                    TileFactory.CreateTile(config, uiState, index)
                }
            }
        }
    }
}
```

**优点**：
- ✅ 无需编写布局代码，只需配置 JSON
- ✅ 自动计算位置和间距
- ✅ 支持配置错误检测（ErrorTile）
- ✅ 易于调整布局顺序和尺寸

### 方式二：代码配置（快速调试）

```kotlin
val layoutConfig = LayoutConfig(
    tiles = listOf(
        TileConfig("clock", "detailed", 4, 2),
        TileConfig("weather", "standard", 2, 2),
        TileConfig("calendar", "standard", 2, 2)
    )
)
```

### 可用的瓷砖变体

#### 时钟瓷砖（Clock）

| 变体 | 尺寸 | 特点 | 配置 |
|-----|------|------|------|
| **simple** | 1×1 | 仅时间 | `{"type":"clock","variant":"simple","columns":1,"rows":1}` |
| **compact** | 2×1 | 时间+日期 | `{"type":"clock","variant":"compact","columns":2,"rows":1}` |
| **standard** | 2×2 | 时间+日期+星期 | `{"type":"clock","variant":"standard","columns":2,"rows":2}` |
| **tall** | 2×4 | 纵向布局+农历 | `{"type":"clock","variant":"tall","columns":2,"rows":4}` |
| **detailed** | 4×2 | 翻转动画+农历 | `{"type":"clock","variant":"detailed","columns":4,"rows":2}` |
| **large** | 4×4 | 大型展示+所有信息 | `{"type":"clock","variant":"large","columns":4,"rows":4}` |

#### 其他瓷砖（遗留组件）

| 类型 | 尺寸 | 配置 |
|-----|------|------|
| **weather** | 2×2 | `{"type":"weather","variant":"standard","columns":2,"rows":2}` |
| **calendar** | 2×2 | `{"type":"calendar","variant":"standard","columns":2,"rows":2}` |
| **todo** | 2×4 | `{"type":"todo","variant":"standard","columns":2,"rows":4}` |
| **news** | 4×4 | `{"type":"news","variant":"standard","columns":4,"rows":4}` |

### 错误处理

如果配置错误（未知变体或尺寸不匹配），TileFactory 会自动显示 ErrorTile：

```
┌─────────────────────┐
│ ⚠️ 配置错误          │
│ 未知变体：clock:xyz  │
│ 建议：检查拼写       │
└─────────────────────┘
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

当前项目的 ViewModel 示例：

```kotlin
// presentation/viewmodel/DashboardViewModel.kt
data class DashboardUiState(
    val currentTime: String = "",
    val currentDate: String = "",
    val lunarDate: String = "",
    val temperature: Int = 22,
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
        // 加载时间、天气、新闻等数据
        _uiState.value = _uiState.value.copy(
            currentTime = "10:12",
            currentDate = "星期一, 10月 28日",
            lunarDate = "农历八月廿二",
            temperature = 22
        )
    }

    fun updateTime() {
        viewModelScope.launch {
            // 定时更新时间
        }
    }
}

// presentation/ui/screen/DashboardScreen.kt
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // 加载配置
    val layoutConfig = remember {
        val repository = LayoutConfigRepository(context)
        repository.loadLayoutConfig() ?: repository.getDefaultLayoutConfig()
    }

    // 渲染瓷砖
    Box(modifier = Modifier.fillMaxSize()) {
        TileGridContainer(Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns, screenHeight ->
            VerticalPriorityLayout(
                tiles = layoutConfig.tiles,
                baseCellSize = baseCellSize,
                dynamicGap = dynamicGap,
                columns = columns,
                screenHeight = screenHeight
            ) { config, index ->
                // 工厂根据配置创建瓷砖，自动绑定 uiState 数据
                TileFactory.CreateTile(config, uiState, index)
            }
        }
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

### 1. 架构使用原则

- ✅ **使用配置驱动** - 通过 JSON 或代码配置定义布局，而不是硬编码
- ✅ **使用工厂模式** - 通过 TileFactory 创建瓷砖，而不是直接实例化组件
- ✅ **注册变体** - 在 TileRegistryInit 中注册所有变体
- ✅ **使用 BaseTile** - 新组件应基于 BaseTile 和 TileSpec 构建
- ✅ **使用 VerticalPriorityLayout** - 让布局引擎自动计算位置
- ❌ 不要手动计算瓷砖位置和尺寸
- ❌ 不要跳过工厂直接使用组件（除非在演示页面）
- ❌ 不要忘记在 TileRegistry 中注册新变体

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

### 4. 配置管理原则

- ✅ **验证配置** - TileFactory 会自动验证配置错误并显示 ErrorTile
- ✅ **提供默认配置** - Repository 应提供 `getDefaultLayoutConfig()`
- ✅ **类型安全** - 使用 TileType 枚举而不是字符串硬编码
- ✅ **尺寸检查** - 在 TileVariantSpec 中定义 supportedSizes
- ❌ 不要在配置中使用不存在的 type 或 variant
- ❌ 不要使用变体不支持的尺寸

### 5. 其他注意事项

1. **依赖管理**: 使用 Version Catalog 统一管理，不要硬编码版本号
2. **预览调试**: 使用 `@Preview` 注解提升开发效率
3. **日志记录**: 使用 Timber 而不是 `println` 或 `Log`
4. **协程作用域**: 在 ViewModel 中使用 `viewModelScope`，不要在 Composable 中直接启动协程
5. **变体初始化**: 确保在 Application 或 MainActivity 中调用 `initializeTileRegistry()`
6. **错误处理**: 依赖 ErrorTile 进行配置错误提示，帮助调试

## 文档资源

详细文档请查看 `docs/` 目录：

- **[docs/README.md](./docs/README.md)** - 项目概述和快速开始
- **[docs/QUICK_START.md](./docs/QUICK_START.md)** - 5分钟快速开始指南
- **[docs/TILE_COMPONENTS.md](./docs/TILE_COMPONENTS.md)** - 组件库完整API文档
- **[docs/ARCHITECTURE.md](./docs/ARCHITECTURE.md)** - 架构设计详解
- **[docs/DEVELOPMENT.md](./docs/DEVELOPMENT.md)** - 开发指南和最佳实践
- **[docs/CLOCK_TILE_ANALYSIS.md](./docs/CLOCK_TILE_ANALYSIS.md)** - 时钟瓷砖变体分析
- **[docs/ENHANCED_CLOCK_USAGE.md](./docs/ENHANCED_CLOCK_USAGE.md)** - 增强时钟使用指南
- **[docs/INTERACTION_GUIDE.md](./docs/INTERACTION_GUIDE.md)** - 交互效果完整指南
- **[docs/METRO_ENHANCEMENTS.md](./docs/METRO_ENHANCEMENTS.md)** - Metro 增强功能文档

## 添加新功能的流程

### 添加新瓷砖类型和变体 ⭐

#### 方式一：添加新变体（推荐）

为现有类型（如 clock）添加新变体：

**Step 1**: 在 `tiles/clock/` 目录创建新变体文件

```kotlin
// presentation/ui/component/tiles/clock/ClockCustomTile.kt
@Composable
fun ClockCustomTile(
    time: String,
    date: String,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current

    BaseTile(
        spec = TileSpec.square(
            color = MetroColors.Blue,
            animation = AnimationType.FLIP
        ),
        modifier = modifier
    ) {
        // 自定义布局
        Column(
            Modifier.fillMaxSize().padding(16.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(time, fontSize = 48.sp, fontWeight = FontWeight.Thin, color = Color.White)
            Text(date, fontSize = 16.sp, fontWeight = FontWeight.Light, color = Color.White)
        }
    }
}
```

**Step 2**: 在 `TileRegistryInit.kt` 中注册变体

```kotlin
// presentation/ui/component/factory/TileRegistryInit.kt
fun initializeTileRegistry() {
    // ... 现有注册代码 ...

    // 注册新的 custom 变体
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "custom",
            supportedSizes = listOf(2 to 2, 4 to 4),  // 支持的尺寸
            defaultSize = 2 to 2  // 默认尺寸
        ) { config, uiState ->
            ClockCustomTile(
                time = uiState.currentTime,
                date = uiState.currentDate
            )
        }
    )
}
```

**Step 3**: 在配置文件中使用

```json
{
  "tiles": [
    { "type": "clock", "variant": "custom", "columns": 2, "rows": 2 }
  ]
}
```

#### 方式二：添加新瓷砖类型

为项目添加全新的瓷砖类型（如 music）：

**Step 1**: 创建瓷砖目录和文件

```kotlin
// presentation/ui/component/tiles/music/MusicStandardTile.kt
@Composable
fun MusicStandardTile(
    songName: String,
    artist: String,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(
            color = Color(0xFF00ABA9),
            animation = AnimationType.PULSE
        ),
        modifier = modifier
    ) {
        Column(
            Modifier.fillMaxSize().padding(16.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text("🎵", fontSize = 64.sp)
            Text(songName, fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
            Text(artist, fontSize = 16.sp, fontWeight = FontWeight.ExtraLight, color = Color.White.copy(0.9f))
        }
    }
}
```

**Step 2**: 在 `TileConfig.kt` 中添加新类型

```kotlin
enum class TileType(val typeName: String) {
    CLOCK("clock"),
    WEATHER("weather"),
    CALENDAR("calendar"),
    TODO("todo"),
    NEWS("news"),
    MUSIC("music")  // 新增
}
```

**Step 3**: 在 `TileRegistryInit.kt` 中注册

```kotlin
TileRegistry.register(
    TileVariantSpec(
        type = "music",
        variant = "standard",
        supportedSizes = listOf(2 to 2),
        defaultSize = 2 to 2
    ) { config, uiState ->
        MusicStandardTile(
            songName = uiState.currentSong,
            artist = uiState.currentArtist
        )
    }
)
```

**Step 4**: 在 ViewModel 中添加数据

```kotlin
data class DashboardUiState(
    // ... 现有字段 ...
    val currentSong: String = "未知歌曲",
    val currentArtist: String = "未知歌手"
)
```

**Step 5**: 在配置中使用

```json
{
  "tiles": [
    { "type": "music", "variant": "standard", "columns": 2, "rows": 2 }
  ]
}
```

## 常见问题

### Q: 瓷砖显示为错误提示（ErrorTile）？
**A**: 检查以下几点：
1. 确认 `type:variant` 在 TileRegistry 中已注册
2. 检查配置的 `columns` 和 `rows` 是否在变体的 `supportedSizes` 中
3. 确认在 Application/MainActivity 中调用了 `initializeTileRegistry()`
4. 查看 ErrorTile 显示的具体错误信息和建议

### Q: 配置文件不生效？
**A**:
1. 确认 JSON 文件放在 `assets/` 目录
2. 检查 JSON 格式是否正确（使用 JSON 验证器）
3. 确认 Repository 的加载逻辑正确
4. 如果 JSON 加载失败，会自动使用默认配置

### Q: 瓷砖布局混乱？
**A**:
1. 检查配置中的 `columns` 总和是否超过网格列数（通常为 6）
2. 确认使用了 `VerticalPriorityLayout` 自动布局引擎
3. 检查 `baseCellSize` 和 `dynamicGap` 计算是否正确

### Q: 动画不流畅？
**A**:
1. 检查是否在主线程执行耗时操作
2. 使用 `viewModelScope.launch` 处理异步任务
3. 避免在 Composable 中进行复杂计算

### Q: 如何添加新的瓷砖类型？
**A**: 参考"添加新功能的流程"章节，按照以下步骤：
1. 创建瓷砖组件文件
2. 在 TileConfig.kt 中添加枚举
3. 在 TileRegistryInit.kt 中注册
4. 在 ViewModel 中添加数据字段

### Q: 如何调试 UI？
**A**:
1. 使用 ADB 截图查看实际效果：
   ```bash
   adb shell screencap -p /sdcard/screenshot.png
   adb pull /sdcard/screenshot.png .
   ```
2. 使用 `@Preview` 注解在 Android Studio 中预览组件
3. 查看 ErrorTile 提供的错误信息

## 组件说明

### 配置层

- **LayoutConfig.kt** - 布局配置数据类（瓷砖列表）
- **TileConfig.kt** - 瓷砖配置数据类（type, variant, columns, rows）+ TileType 枚举
- **TileVariantSpec.kt** - 变体规格定义 + TileRegistry 注册中心
- **LayoutConfigRepository.kt** - 配置加载器（JSON 文件 / 默认配置）

### 工厂层 ⭐ 核心

- **TileFactory.kt** - 瓷砖工厂，根据配置创建瓷砖组件
- **TileRegistryInit.kt** - 变体注册初始化（应用启动时调用）

### 布局引擎层

- **VerticalPriorityLayout.kt** - 垂直优先布局引擎，自动计算瓷砖位置
- **TileGridContainer.kt** - 网格容器，提供响应式网格参数

### 基础组件层（必需）

- **BaseTile.kt** - 基础瓷砖容器（统一入口）
- **TileCard.kt** - 瓷砖卡片，提供 7 种尺寸（Small, Square, MediumWide, etc.）
- **TileSpec.kt** - 瓷砖规格配置（columns, rows, color, animation）
- **TileGrid.kt** - 响应式网格系统（6列自适应）
- **TileAnimation.kt** - 动画组件（Flip, Pulse, Slide）

### 业务组件层

#### 时钟瓷砖（6个变体）
- **ClockSimpleTile.kt** - 简约版 (1×1)
- **ClockCompactTile.kt** - 紧凑版 (2×1)
- **ClockStandardTile.kt** - 标准版 (2×2)
- **ClockTallTile.kt** - 高版 (2×4)
- **ClockDetailedTile.kt** - 详细版 (4×2，翻转动画）
- **ClockLargeTile.kt** - 大型版 (4×4)

#### 公共组件
- **ErrorTile.kt** - 错误瓷砖（配置错误提示）

### 遗留组件（向后兼容）

- **legacy/TileComponents.kt** - 旧的高级组件库（WeatherTile, CalendarTile, TodoTile, NewsTile）

### 扩展组件（可选）

- **TileInteraction.kt** - 交互动效（按压、弹跳、抖动等）
- **MetroEnhancements.kt** - 增强功能（状态栏、角标）
- **MetroTheme.kt** - 主题系统（深色/浅色/高对比度）
- **MetroEasing.kt** - Metro 缓动函数

### 数据模型

- **NewsItem.kt** - 新闻数据模型
- **Notification.kt** - 通知数据模型
- **TodoItem.kt** - 待办事项数据模型

### 导航系统

- **NavGraph.kt** - 导航图定义
- **Screen.kt** - 路由配置

所有组件遵循 **Windows Phone Metro 设计语言**，保持简洁、扁平、高饱和度的视觉风格。
