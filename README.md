# Deskmate

一个基于 Kotlin + Jetpack Compose 的现代化 Android 应用。

## 技术栈

- **语言**: Kotlin 2.0.21
- **UI 框架**: Jetpack Compose
- **架构模式**: MVVM (Model-View-ViewModel)
- **最低 SDK**: 24 (Android 7.0)
- **目标 SDK**: 36

## 主要依赖

### 核心库
- AndroidX Core KTX
- Jetpack Compose (Material3)
- Navigation Compose
- Lifecycle (ViewModel, Runtime)

### 异步处理
- Kotlin Coroutines
- Kotlin Flow

### 可选依赖（按需启用）
- **网络请求**: Retrofit + OkHttp + Kotlinx Serialization
- **本地数据库**: Room
- **图片加载**: Coil
- **日志**: Timber

## 项目结构

```
app/src/main/java/top/yaotutu/deskmate/
├── data/                  # 数据层
│   ├── local/            # 本地数据源
│   ├── remote/           # 远程数据源
│   ├── repository/       # 数据仓库
│   └── model/            # 数据模型
├── presentation/          # 表现层
│   ├── ui/
│   │   ├── screen/       # 页面
│   │   ├── component/    # 可复用组件
│   │   └── theme/        # 主题配置
│   └── viewmodel/        # ViewModel
├── navigation/            # 导航配置
└── utils/                 # 工具类
```


| variant 值  | 对应组件              | 尺寸  | 说明   |
  |------------|-------------------|-----|------|
| "simple"   | ClockSimpleTile   | 1×1 | 简洁时钟 |
| "standard" | ClockStandardTile | 2×2 | 标准时钟 |
| "detailed" | ClockDetailedTile | 4×2 | 详细时钟 |
| "large"    | ClockLargeTile    | 4×4 | 超大时钟 |
| "tall"     | ClockTallTile     | 2×4 | 垂直时钟 |
| "compact"  | ClockCompactTile  | 2×1 | 紧凑时钟 |


## 开始使用

### 环境要求

- Android Studio Ladybug | 2024.2.1 或更高版本
- JDK 11 或更高版本
- Android SDK 36

### 构建项目

1. 克隆仓库
```bash
git clone <repository-url>
cd deskmate
```

2. 打开项目
使用 Android Studio 打开项目根目录

3. 同步 Gradle
等待 Gradle 同步完成

4. 运行项目
选择设备或模拟器，点击运行按钮

## 依赖管理

项目使用 Gradle Version Catalog（libs.versions.toml）统一管理依赖版本。

可选依赖默认被注释，需要时在 `app/build.gradle.kts` 中取消注释即可启用。

## 代码规范

- 遵循 Kotlin 官方编码规范
- 使用 Jetpack Compose 最佳实践
- MVVM 架构分层清晰

## License

TODO: 添加许可证信息
