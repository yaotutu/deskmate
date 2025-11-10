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
│   ├── component/
│   │   ├── base/         # 基础组件 (BaseTile, TileGrid, TileSpec)
│   │   ├── factory/      # 工厂层 (TileFactory, TileRegistryInit)
│   │   └── tiles/        # 业务瓷砖 (clock/, weather/, calendar/, todo/, news/)
│   ├── screen/           # 页面 (DashboardScreen)
│   ├── theme/            # 响应式设计系统 (MetroTypography, MetroSpacing, etc.)
│   └── viewmodel/        # ViewModel
├── navigation/            # 导航配置
└── utils/                 # 工具类
```

## 支持的瓷砖尺寸

项目支持 **5 种标准尺寸**（变体命名规则：AxB = A行×B列）：

| variant 值 | 尺寸  | 说明       |
|-----------|------|----------|
| "1x1"     | 1×1  | 简约版    |
| "2x2"     | 2×2  | 标准版    |
| "2x4"     | 2×4  | 宽版      |
| "4x2"     | 4×2  | 高版      |
| "4x4"     | 4×4  | 大型版    |


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
