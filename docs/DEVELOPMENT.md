# Deskmate 开发指南

本指南将帮助你快速上手 Deskmate 的开发工作，包括如何添加新功能、创建新瓷砖、调试和优化。

## ✨ 开发流程重大更新 (2025-11-01)

### 🎯 零配置动画开发
现在开发新瓷砖组件变得极其简单，实现了真正的"开箱即用"：

```kotlin
// 步骤1：创建瓷砖组件（使用预设，自动获得动画）
@Composable
fun WeatherStandardTile(temperature: Int, condition: String) {
    BaseTile(spec = TileSpec.square(MetroTileColors.Weather)) {
        MediumTilePresets.Counter(value = temperature.toString(), unit = "°", label = condition)
        // 自动获得 PULSE 动画！无需手动配置
    }
}

// 步骤2：注册变体
TileRegistry.register(
    TileVariantSpec(
        type = "weather",
        variant = "standard",
        supportedSizes = listOf(2 to 2)
    ) { config, uiState ->
        WeatherStandardTile(
            temperature = uiState.temperature,
            condition = uiState.weatherCondition
        )
    }
)

// 步骤3：在JSON配置中使用
{
  "type": "weather",
  "variant": "standard",
  "columns": 2,
  "rows": 2
}
// 自动获得完整功能 + 最佳动画！
```

### 🚀 开发效率提升
- **代码减少 80%**：无需手动实现动画逻辑
- **调试时间减少 90%**：预设经过充分测试，开箱即用
- **学习成本降低**：只需关注业务数据，动画自动处理

### 🔧 最佳实践
1. **优先使用预设**：38种预设覆盖所有常用场景
2. **自定义内容可指定动画**：TileSpec支持可选动画参数
3. **统一变体注册**：所有组件都通过TileRegistry管理
4. **配置驱动开发**：通过JSON配置灵活组合组件

---

## 🚀 开发环境设置

### 前置要求

- **Android Studio**: Hedgehog (2023.1.1) 或更高版本
- **JDK**: 11 或更高版本
- **Android SDK**:
  - minSdk: 24 (Android 7.0)
  - targetSdk: 36
  - compileSdk: 36
- **Kotlin**: 2.0.21+

### 初始化项目

```bash
# 克隆项目
git clone https://github.com/yourusername/deskmate.git
cd deskmate

# 清理并构建
./gradlew clean
./gradlew build

# 运行应用
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 📁 项目结构

```
app/src/main/java/top/yaotutu/deskmate/
├── data/                           # 数据层
│   ├── model/                     # 数据模型
│   ├── repository/                # 数据仓库（未来添加）
│   └── local/remote/              # 数据源（未来添加）
├── presentation/                   # 表现层
│   ├── ui/
│   │   ├── component/             # UI 组件 ⭐ 核心
│   │   │   ├── TileAnimation.kt   # 动画组件
│   │   │   ├── TileCard.kt        # 基础瓷砖容器
│   │   │   ├── TileComponents.kt  # 高级组件库
│   │   │   └── TileGrid.kt        # 网格系统
│   │   ├── screen/                # 页面级 Composable
│   │   │   └── DashboardScreen.kt
│   │   └── theme/                 # Material3 主题
│   └── viewmodel/                 # ViewModel 层
│       └── DashboardViewModel.kt
├── navigation/                     # 导航配置（未来添加）
└── MainActivity.kt                 # 入口
```

---

## 🎯 开发工作流

### 典型的功能开发流程

```
1. 确定需求 → 2. 设计数据模型 → 3. 创建 ViewModel
    ↓
4. 选择/创建瓷砖组件 → 5. 更新 Screen → 6. 测试和调试
```

---

## 🧩 添加新的瓷砖组件

### 场景 1: 使用现有瓷砖类型

如果你的需求可以用现有的 5 种瓷砖组件满足，直接使用高级 API：

```kotlin
// 在 TileComponents.kt 中添加
@Composable
fun MusicTile(
    songName: String,
    artist: String,
    albumArt: String = "🎵",
    backgroundColor: Color = Color(0xFF00ABA9),
    modifier: Modifier = Modifier
) {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTile(
        backgroundColor = backgroundColor,
        cellWidth = cellWidth,
        cellHeight = cellHeight,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = albumArt,
                fontSize = 64.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = songName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
            Text(
                text = artist,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraLight,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
```

### 场景 2: 使用自定义内容瓷砖

如果需要完全自定义内容：

```kotlin
// 在 Screen 中直接使用
CustomSquareTile(
    backgroundColor = Color(0xFF9C27B0)
) {
    // 完全自由的内容
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(72.dp)
        )
    }
}
```

### 场景 3: 添加新的瓷砖尺寸

如果现有的 5 种尺寸都不满足需求（少见），需要在 `TileCard.kt` 中添加：

```kotlin
@Composable
fun ExtraWideTile(
    backgroundColor: Color,
    cellWidth: Dp,
    cellHeight: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val tileWidth = TileGrid.calculateTileWidth(cellWidth, gridColumns = 6)  // 6 列宽
    val tileHeight = TileGrid.calculateTileHeight(cellHeight, gridRows = 2) // 2 行高

    Box(
        modifier = modifier
            .width(tileWidth)
            .height(tileHeight)
            .background(backgroundColor, shape = RoundedCornerShape(2.dp))
            .padding(16.dp)
    ) {
        content()
    }
}
```

---

## 🎬 添加新的动画效果

### 在 TileAnimation.kt 中添加

```kotlin
@Composable
fun BounceTileAnimation(
    content: @Composable () -> Unit,
    bounceHeight: Float = 20f,
    durationMillis: Int = 1000,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bounce")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = bounceHeight,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Box(modifier = modifier.offset(y = offsetY.dp)) {
        content()
    }
}
```

### 在瓷砖组件中使用

```kotlin
@Composable
fun NotificationTile(
    count: Int,
    backgroundColor: Color = Color(0xFFE3008C),
    modifier: Modifier = Modifier
) {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTile(backgroundColor, cellWidth, cellHeight, modifier) {
        BounceTileAnimation(bounceHeight = 10f) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📬", fontSize = 64.sp)
                    Text("$count", fontSize = 48.sp, fontWeight = FontWeight.Thin, color = Color.White)
                }
            }
        }
    }
}
```

---

## 📊 添加新的页面

### 步骤 1: 创建 UiState

```kotlin
// presentation/viewmodel/ProfileViewModel.kt
data class ProfileUiState(
    val userName: String = "",
    val avatarUrl: String = "",
    val followerCount: Int = 0,
    val isLoading: Boolean = true
)
```

### 步骤 2: 创建 ViewModel

```kotlin
class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // 模拟数据加载
            delay(1000)

            _uiState.update {
                it.copy(
                    userName = "张三",
                    followerCount = 1234,
                    isLoading = false
                )
            }
        }
    }
}
```

### 步骤 3: 创建 Screen

```kotlin
// presentation/ui/screen/ProfileScreen.kt
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        LoadingScreen()
        return
    }

    Box(modifier.fillMaxSize().background(Color(0xFF1E1E1E)).padding(8.dp)) {
        TileGridContainer(Modifier.fillMaxSize()) { cellWidth, cellHeight ->
            ProvideTileGrid(cellWidth, cellHeight) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // 个人信息瓷砖
                    CustomMediumWideTile(backgroundColor = Color(0xFF0078D7)) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("👤", fontSize = 72.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    uiState.userName,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Light,
                                    color = Color.White
                                )
                                Text(
                                    "${uiState.followerCount} 粉丝",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                    // 添加更多瓷砖...
                }
            }
        }
    }
}
```

---

## 🔄 状态管理最佳实践

### 使用 StateFlow

```kotlin
class MyViewModel : ViewModel() {
    // ❌ 不推荐：直接暴露 MutableStateFlow
    val uiState = MutableStateFlow(MyUiState())

    // ✅ 推荐：暴露只读的 StateFlow
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()
}
```

### 更新状态

```kotlin
// ✅ 推荐：使用 update 函数
_uiState.update { currentState ->
    currentState.copy(userName = "新名字")
}

// ❌ 不推荐：直接赋值
_uiState.value = _uiState.value.copy(userName = "新名字")
```

### 处理副作用

```kotlin
class MyViewModel : ViewModel() {
    init {
        // ✅ 推荐：在 viewModelScope 中启动协程
        viewModelScope.launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        try {
            val data = repository.getData()
            _uiState.update { it.copy(data = data) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }
}
```

---

## 🎨 Metro 设计规范

### 字体使用

```kotlin
// ✅ 推荐：使用 Thin 或 ExtraLight
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

// ❌ 不推荐：使用粗字体
Text(
    text = "10:12",
    fontWeight = FontWeight.Bold  // Metro 风格不使用粗体
)
```

### 颜色使用

```kotlin
// ✅ 推荐：使用 Metro 配色
object MetroColors {
    val Blue = Color(0xFF0078D7)
    val Orange = Color(0xFFFF8C00)
    val Green = Color(0xFF00A300)
    val Purple = Color(0xFFAA00FF)
    val Red = Color(0xFFE51400)
}

// 使用
ClockTile(
    time = "10:12",
    backgroundColor = MetroColors.Blue  // ✅
)

// ❌ 不推荐：使用低饱和度或渐变色
ClockTile(
    backgroundColor = Color(0xFF888888)  // 灰色不符合 Metro 风格
)
```

### 间距使用

```kotlin
// ✅ 推荐：使用统一的间距
Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    // 瓷砖间距 8dp
}

Box(modifier = Modifier.padding(16.dp)) {
    // 瓷砖内边距 16dp
}

// ❌ 不推荐：随意使用间距
Column(verticalArrangement = Arrangement.spacedBy(3.dp))  // 不符合规范
```

---

## 🧪 测试和调试

### 使用 @Preview 预览组件

```kotlin
@Preview(showBackground = true)
@Composable
fun PreviewMusicTile() {
    DeskmateTheme {
        Box(modifier = Modifier.size(200.dp)) {
            TileGridContainer(modifier = Modifier.fillMaxSize()) { cellWidth, cellHeight ->
                ProvideTileGrid(cellWidth, cellHeight) {
                    MusicTile(
                        songName = "测试歌曲",
                        artist = "测试歌手"
                    )
                }
            }
        }
    }
}
```

### 使用 ADB 截图

```bash
# 截图并保存到本地
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png .
```

### 日志调试

```kotlin
// 使用 Timber（项目已集成）
import timber.log.Timber

class MyViewModel : ViewModel() {
    fun loadData() {
        Timber.d("开始加载数据")
        viewModelScope.launch {
            try {
                val data = repository.getData()
                Timber.d("数据加载成功: $data")
            } catch (e: Exception) {
                Timber.e(e, "数据加载失败")
            }
        }
    }
}
```

---

## 🔧 常用开发命令

### Gradle 命令

```bash
# 清理构建产物
./gradlew clean

# 构建 Debug APK
./gradlew assembleDebug

# 构建 Release APK
./gradlew assembleRelease

# 运行单元测试
./gradlew test

# 运行 instrumentation 测试
./gradlew connectedAndroidTest

# 查看所有任务
./gradlew tasks

# 停止 Gradle daemon
./gradlew --stop

# 刷新依赖
./gradlew build --refresh-dependencies
```

### ADB 命令

```bash
# 安装应用
adb install app/build/outputs/apk/debug/app-debug.apk

# 卸载应用
adb uninstall top.yaotutu.deskmate

# 启动应用
adb shell am start -n top.yaotutu.deskmate/.MainActivity

# 查看日志
adb logcat | grep Deskmate

# 清除应用数据
adb shell pm clear top.yaotutu.deskmate
```

---

## 📦 依赖管理

### 添加新依赖

#### 步骤 1: 在 libs.versions.toml 中添加版本

```toml
[versions]
coil = "2.5.0"

[libraries]
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
```

#### 步骤 2: 在 app/build.gradle.kts 中引用

```kotlin
dependencies {
    implementation(libs.coil.compose)
}
```

#### 步骤 3: 同步项目

```bash
./gradlew --refresh-dependencies
```

---

## 🚨 常见问题

### Q1: Composable 函数重组太频繁

**原因**：传递了不稳定的参数

**解决**：
```kotlin
// ❌ 不稳定
data class MyData(val items: List<String>)

// ✅ 稳定
@Immutable
data class MyData(val items: List<String>)
```

### Q2: 动画卡顿

**原因**：主线程被阻塞

**解决**：
```kotlin
// ❌ 在主线程执行耗时操作
val data = repository.getData()

// ✅ 在协程中执行
viewModelScope.launch {
    val data = repository.getData()
}
```

### Q3: 网格尺寸计算错误

**原因**：未使用 TileGridContainer

**解决**：
```kotlin
// ❌ 直接使用瓷砖组件
ClockTile(...)

// ✅ 在 TileGridContainer 中使用
TileGridContainer { cellWidth, cellHeight ->
    ProvideTileGrid(cellWidth, cellHeight) {
        ClockTile(...)
    }
}
```

---

## 🎓 学习资源

### 官方文档

- [Jetpack Compose 官方文档](https://developer.android.com/jetpack/compose)
- [Kotlin 协程指南](https://kotlinlang.org/docs/coroutines-guide.html)
- [Material3 设计指南](https://m3.material.io/)

### 项目文档

- [快速开始指南](./QUICK_START.md)
- [组件库文档](./TILE_COMPONENTS.md)
- [架构设计](./ARCHITECTURE.md)

---

## 🤝 贡献指南

### 提交代码

1. Fork 本仓库
2. 创建特性分支
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. 提交更改
   ```bash
   git commit -m 'feat: 添加某个功能'
   ```
4. 推送到分支
   ```bash
   git push origin feature/amazing-feature
   ```
5. 提交 Pull Request

### 提交信息规范

使用 [Conventional Commits](https://www.conventionalcommits.org/) 规范：

```
feat: 添加音乐瓷砖组件
fix: 修复时钟翻转动画卡顿
docs: 更新快速开始指南
refactor: 重构网格计算逻辑
test: 添加瓷砖组件单元测试
```

### 代码审查清单

- [ ] 代码符合 Kotlin 编码规范
- [ ] 添加了必要的注释和文档
- [ ] 通过了所有单元测试
- [ ] 符合 Metro 设计规范
- [ ] 没有引入新的 Warning

---

## 🔮 未来计划

### 计划添加的功能

- [ ] 网络数据源（Retrofit）
- [ ] 本地数据库（Room）
- [ ] 图片加载（Coil）
- [ ] 依赖注入（Hilt）
- [ ] 更多预设瓷砖组件
- [ ] 瓷砖拖拽排序
- [ ] 自定义主题配色

---

**Happy Coding!** 🎉

如有问题，请提交 [Issue](https://github.com/yourusername/deskmate/issues) 或查看 [文档](./README.md)。
