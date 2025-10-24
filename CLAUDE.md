# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Deskmate 是一个基于 Kotlin + Jetpack Compose 的现代化 Android 应用,采用 MVVM 架构模式。

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

## MVVM 架构结构

```
app/src/main/java/top/yaotutu/deskmate/
├── data/                      # 数据层
│   ├── local/                # 本地数据源 (SharedPreferences, Room)
│   ├── remote/               # 远程数据源 (API 接口)
│   ├── repository/           # Repository 实现 (数据层抽象)
│   └── model/                # 数据模型 (实体类)
├── presentation/              # 表现层
│   ├── ui/
│   │   ├── screen/          # 页面级 Composable (完整的界面)
│   │   ├── component/       # 可复用的 UI 组件
│   │   └── theme/           # Material3 主题配置
│   └── viewmodel/           # ViewModel 层 (UI 状态管理)
├── navigation/                # 导航配置
│   ├── NavGraph.kt          # Navigation Compose 导航图
│   └── Screen.kt            # 路由定义 (sealed class)
└── utils/                     # 工具类和扩展函数
```

### 架构指南

1. **数据流向**: View -> ViewModel -> Repository -> DataSource
2. **State 管理**: ViewModel 使用 StateFlow 或 MutableState 暴露 UI 状态
3. **导航**: 使用 Navigation Compose,路由在 `Screen.kt` 中定义为 sealed class
4. **依赖注入**: 目前未集成 DI 框架,使用手动依赖传递

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

## 导航系统使用

```kotlin
// 定义路由 (navigation/Screen.kt)
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }
}

// 在 Composable 中使用
val navController = rememberNavController()
NavGraph(navController = navController)

// 导航到其他页面
navController.navigate(Screen.Profile.createRoute("123"))
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

## 注意事项

1. MainActivity 目前使用默认的 Greeting 组件,需要集成 NavGraph 来启用导航
2. 项目已配置完整的 MVVM 目录结构,但大部分目录为空,需要根据实际功能添加文件
3. 依赖版本通过 Version Catalog 统一管理,不要在 build.gradle.kts 中硬编码版本号
4. 编写 Composable 时注意使用 `@Preview` 注解提升开发效率
