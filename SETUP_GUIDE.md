# 项目初始化指南

## 已完成的初始化工作

### 1. ✅ MVVM 目录结构

项目采用标准的 MVVM 架构，目录结构如下：

```
app/src/main/java/top/yaotutu/deskmate/
├── data/                      # 数据层
│   ├── local/                # 本地数据源（SharedPreferences, Room 等）
│   ├── remote/               # 远程数据源（API 接口）
│   ├── repository/           # Repository 实现
│   └── model/                # 数据模型
├── presentation/              # 表现层
│   ├── ui/
│   │   ├── screen/          # 页面级 Composable
│   │   ├── component/       # 可复用组件
│   │   └── theme/           # 主题配置（Color, Type, Theme）
│   └── viewmodel/           # ViewModel 层
├── navigation/                # 导航配置
│   ├── NavGraph.kt          # 导航图
│   └── Screen.kt            # 路由定义
└── utils/                     # 工具类
```

### 2. ✅ 依赖库配置

#### 核心依赖（已启用）
- **Jetpack Compose**: Material3 UI 框架
- **Navigation Compose**: 导航组件
- **ViewModel**: Lifecycle ViewModel
- **Coroutines**: 协程支持
- **Timber**: 日志库

#### 可选依赖（按需启用）
在 `app/build.gradle.kts` 中取消注释即可使用：

- **网络请求**: Retrofit + OkHttp + Kotlinx Serialization
- **本地数据库**: Room
- **图片加载**: Coil

#### KSP 插件
如需使用 Room 等需要注解处理的库，在以下文件中取消注释：
- `gradle/libs.versions.toml`: 取消注释 ksp 插件定义
- `app/build.gradle.kts`: 取消注释 `alias(libs.plugins.ksp)`

### 3. ✅ 导航系统

已创建基础的 Navigation Compose 配置：

- **Screen.kt**: 使用 sealed class 定义路由
- **NavGraph.kt**: 配置导航图

使用示例：
```kotlin
// 在 MainActivity 或根 Composable 中
val navController = rememberNavController()
NavGraph(navController = navController)

// 导航到其他页面
navController.navigate(Screen.Detail.route)
```

### 4. ✅ 版本控制

`.gitignore` 已完善，包含：
- Android 构建产物
- IDE 配置文件
- 签名密钥文件
- 本地配置文件

### 5. ✅ 文档

- `README.md`: 项目说明文档
- `SETUP_GUIDE.md`: 本文档，项目初始化指南

## 下一步建议

### 创建第一个功能模块

1. **创建数据模型**
   ```kotlin
   // data/model/User.kt
   data class User(
       val id: String,
       val name: String
   )
   ```

2. **创建 Repository**
   ```kotlin
   // data/repository/UserRepository.kt
   class UserRepository {
       suspend fun getUser(id: String): User {
           // 实现数据获取逻辑
       }
   }
   ```

3. **创建 ViewModel**
   ```kotlin
   // presentation/viewmodel/UserViewModel.kt
   class UserViewModel : ViewModel() {
       private val repository = UserRepository()

       private val _user = MutableStateFlow<User?>(null)
       val user: StateFlow<User?> = _user.asStateFlow()

       fun loadUser(id: String) {
           viewModelScope.launch {
               _user.value = repository.getUser(id)
           }
       }
   }
   ```

4. **创建 Screen**
   ```kotlin
   // presentation/ui/screen/UserScreen.kt
   @Composable
   fun UserScreen(
       viewModel: UserViewModel = viewModel()
   ) {
       val user by viewModel.user.collectAsState()

       // UI 实现
   }
   ```

### 启用可选依赖

#### 启用网络请求（Retrofit）
1. 在 `app/build.gradle.kts` 中取消注释网络相关依赖
2. 创建 API 接口：`data/remote/ApiService.kt`
3. 配置 Retrofit 实例：`data/remote/RetrofitClient.kt`

#### 启用数据库（Room）
1. 在 `gradle/libs.versions.toml` 中取消注释 ksp 插件
2. 在 `app/build.gradle.kts` 中取消注释：
   - ksp 插件
   - Room 依赖
3. 创建实体：`data/local/entity/UserEntity.kt`
4. 创建 DAO：`data/local/dao/UserDao.kt`
5. 创建数据库：`data/local/AppDatabase.kt`

## 代码规范建议

1. **包命名**: 使用小写字母，避免下划线
2. **类命名**: 使用大驼峰命名法（PascalCase）
3. **函数/变量命名**: 使用小驼峰命名法（camelCase）
4. **Composable 函数**: 使用大驼峰命名法，就像类名一样
5. **常量**: 使用全大写字母 + 下划线分隔

## 常用命令

```bash
# 清理构建
./gradlew clean

# 构建 Debug APK
./gradlew assembleDebug

# 运行测试
./gradlew test

# 检查依赖更新
./gradlew dependencyUpdates
```

## 问题排查

### Gradle 同步失败
```bash
# 清理 Gradle 缓存
./gradlew clean
./gradlew --stop

# 重新同步
./gradlew build --refresh-dependencies
```

### 依赖未识别
确保 `gradle/libs.versions.toml` 中的版本号定义正确，并且在 `app/build.gradle.kts` 中正确引用。

## 资源链接

- [Jetpack Compose 官方文档](https://developer.android.com/jetpack/compose)
- [Android Architecture 指南](https://developer.android.com/topic/architecture)
- [Kotlin 协程指南](https://kotlinlang.org/docs/coroutines-guide.html)
