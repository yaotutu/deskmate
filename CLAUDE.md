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
- ✨ **零配置动画系统** - 使用预设自动获得最佳动画效果
- 🚀 **开发效率提升 80%** - 预设系统大幅减少重复代码
- 📁 **扁平化目录结构** - 移除冗余嵌套，提升代码可读性 (2025-11-01 重构)
- 🏷️ **直观的尺寸命名** - 使用 1x1、2x2 等直观命名，替代语义化命名 (2025-11-01 重构)
- 🎯 **MetroTypography 设计令牌** - 响应式字号系统，自动适配不同密度屏幕 (2025-01-05 重构)
- 🔍 **容器级全局缩放系统** - 类似前端 zoom，所有元素等比缩放，零代码侵入 (2025-01-05 新增)
- 📐 **响应式内容适配系统** - 基于 baseCellSize 的固定比例缩放，所有间距/内边距/图标自适应 (2025-11-07 新增)

### 🔄 重构历史

**2025-11-01 重大重构**
- ✅ 移除 `presentation/ui/` 中间层，减少目录嵌套（4层 → 3层）
- ✅ 扁平化 `animation/` 目录，移除 4 个子目录（core/advanced/interaction/special）
- ✅ 合并 `interaction/` 和 `enhancement/` 到 `common/`
- ✅ 重命名所有时钟组件：`ClockSimpleTile` → `Clock1x1Tile` 等
- ✅ 更新 variant ID 命名：`simple` → `1x1`，`standard` → `2x2` 等
- ✅ 更新所有配置文件（clock_showcase.json, perfect_layout.json, dashboard_layout.json）
- ✅ 更新 200+ 个 package 声明和 import 语句

**2025-01-05 容器级全局缩放系统**
- ✅ 创建 `MetroScaleSystem.kt` 全局缩放系统（类似前端 zoom）
- ✅ 修改 `TileGridContainer` 添加容器级 graphicsLayer 缩放
- ✅ 实现基准屏幕系统（BASE_CELL_SIZE = 264dp）
- ✅ 自动计算缩放系数：scaleRatio = 当前尺寸 / 基准尺寸
- ✅ 所有元素（字体、图标、间距、圆角）自动等比缩放
- ✅ 零代码侵入，无需修改任何瓷砖组件（50+ 文件）
- ✅ 创建 `ScaleTestScreen` 测试页面，支持动态调整缩放比例
- ✅ 完美解决不同屏幕尺寸下的视觉一致性问题

**2025-11-07 响应式内容适配系统** ⭐ 最新
- ✅ 创建 `LocalTileBaseUnit` (基于 baseCellSize)，作为所有内容的度量基准
- ✅ 创建 `MetroSpacing.kt` 响应式间距系统（5个级别：tiny, small, medium, large, extraLarge）
- ✅ 创建 `MetroPadding.kt` 响应式内边距系统（4个级别 + auto 自动计算）
- ✅ 创建 `MetroIconSize.kt` 响应式图标尺寸系统（4个级别）
- ✅ 重构 6 个预设文件（38种预设），替换 65 个硬编码间距/内边距值
- ✅ 重构 7 个硬编码瓷砖文件，替换 22 个硬编码值
- ✅ 修复 TileRegistryInit.kt 中 1×2 瓷砖的 supportedSizes 定义错误
- ✅ 创建 ResponsiveTestScreen 测试页面，验证所有预设的响应式效果
- ✅ **核心理念**：使用 1×1 瓷砖尺寸作为基础单位，所有内容按固定比例缩放
- ✅ **效果**：不同屏幕尺寸下，瓷砖内容保持相同的视觉比例和密度

**2025-01-05 字号系统重构**
- ✅ 创建 `MetroTypography.kt` 设计令牌系统
- ✅ 实现响应式字号（根据屏幕密度自动调整：213dpi、520dpi等）
- ✅ 更新 6 个预设文件（65处参数默认值）
- ✅ 更新 10 个硬编码瓷砖文件（70+处硬编码字号）
- ✅ 实现 Single Source of Truth，修改一处全局生效
- ✅ 解决高密度屏幕显示问题（小米设备 520dpi）

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
│  - tiles/special/ (特殊瓷砖：Photo, Music等)      │
│  - 职责: 具体瓷砖实现、数据绑定                    │
└───────────────┬───────────────────────────────────┘
                │ uses
┌───────────────▼───────────────────────────────────┐
│  基础层 (Foundation Layer)                        │
│  - BaseTile.kt (基础瓷砖)                         │
│  - TileCard.kt (瓷砖容器)                         │
│  - TileSpec.kt (瓷砖规格)                         │
│  - animation/ (14种动画：Flip, Pulse, Slide等)   │
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
├── presentation/                      # 表现层 ⭐ 已扁平化（移除 ui/ 中间层）
│   ├── component/                    # UI 组件 ⭐ 核心
│   │   ├── animation/               # 动画组件（扁平化，14个文件，无子目录）
│   │   │   ├── FlipAnimation.kt    # 核心 - 翻转动画
│   │   │   ├── PulseAnimation.kt   # 核心 - 脉冲动画
│   │   │   ├── SlideAnimation.kt   # 核心 - 滑动动画
│   │   │   ├── FadeAnimation.kt    # 核心 - 淡入淡出动画
│   │   │   ├── MarqueeAnimation.kt # 核心 - 跑马灯动画
│   │   │   ├── PeekAnimation.kt    # 核心 - 探视动画
│   │   │   ├── RotateAnimation.kt  # 高级 - 旋转动画
│   │   │   ├── StaggerEnterAnimation.kt # 高级 - 错峰进入
│   │   │   ├── ShimmerAnimation.kt # 高级 - 微光动画
│   │   │   ├── WipeAnimation.kt    # 高级 - 擦除动画
│   │   │   ├── DepthAnimation.kt   # 高级 - 深度动画
│   │   │   ├── BounceAnimation.kt  # 交互 - 弹跳动画
│   │   │   ├── ShakeAnimation.kt   # 交互 - 抖动动画
│   │   │   └── CounterAnimation.kt # 特殊 - 数字滚动
│   │   ├── base/                    # 基础组件层
│   │   │   ├── BaseTile.kt          # 基础瓷砖（统一容器）
│   │   │   ├── TileCard.kt          # 瓷砖卡片（7种尺寸）
│   │   │   ├── TileSpec.kt          # 瓷砖规格配置 + AnimationType
│   │   │   ├── TileGrid.kt          # 网格系统 + CompositionLocal
│   │   │   └── presets/             # 预设系统（6个文件，38种预设）
│   │   │       ├── SmallTilePresets.kt    # 1×1 预设
│   │   │       ├── CompactTilePresets.kt  # 2×1 预设
│   │   │       ├── MediumTilePresets.kt   # 2×2 预设
│   │   │       ├── WideTilePresets.kt     # 4×2 预设
│   │   │       ├── TallTilePresets.kt     # 2×4 预设
│   │   │       └── LargeTilePresets.kt    # 4×4 预设
│   │   ├── common/                  # 通用组件（合并 interaction + enhancement）
│   │   │   ├── TileClickEffect.kt   # 点击效果枚举
│   │   │   ├── TileClickEffects.kt  # 6种单一效果实现
│   │   │   ├── TileInteractionWrappers.kt # 交互包装器
│   │   │   ├── MetroStatusBar.kt    # Metro 风格状态栏
│   │   │   └── MetroBadge.kt        # 角标系统（数字+点）
│   │   ├── factory/                 # 工厂层 ⭐ 核心
│   │   │   ├── TileFactory.kt       # 瓷砖工厂（根据配置创建）
│   │   │   └── TileRegistryInit.kt  # 变体注册初始化
│   │   ├── layout/                  # 布局引擎
│   │   │   └── VerticalPriorityLayout.kt # 垂直优先自动布局
│   │   └── tiles/                   # 业务瓷砖实现
│   │       ├── clock/               # 时钟瓷砖变体（6个尺寸）
│   │       │   ├── Clock1x1Tile.kt      # 1×1 简约版（仅时间）
│   │       │   ├── Clock1x2Tile.kt      # 1×2 紧凑版（时间+日期）
│   │       │   ├── Clock2x2Tile.kt      # 2×2 标准版（时间+日期+星期）
│   │       │   ├── Clock4x2Tile.kt      # 4×2 高版（垂直布局+农历）
│   │       │   ├── Clock2x4Tile.kt      # 2×4 详细版（翻转动画+农历）
│   │       │   └── Clock4x4Tile.kt      # 4×4 大型版（完整信息）
│   │       ├── weather/             # 天气瓷砖变体（6个尺寸）
│   │       │   ├── Weather1x1Tile.kt    # 1×1 简约版（图标）
│   │       │   ├── Weather1x2Tile.kt    # 1×2 紧凑版（温度+状况）
│   │       │   ├── Weather2x2Tile.kt    # 2×2 标准版（完整信息）
│   │       │   ├── Weather4x2Tile.kt    # 4×2 高版（预报列表）
│   │       │   ├── Weather2x4Tile.kt    # 2×4 详细版（周视图）
│   │       │   └── Weather4x4Tile.kt    # 4×4 大型版（仪表盘）
│   │       ├── calendar/            # 日历瓷砖变体（6个尺寸）
│   │       │   ├── Calendar1x1Tile.kt   # 1×1 简约版（图标）
│   │       │   ├── Calendar1x2Tile.kt   # 1×2 紧凑版（日期+星期）
│   │       │   ├── Calendar2x2Tile.kt   # 2×2 标准版（日期+事件）
│   │       │   ├── Calendar4x2Tile.kt   # 4×2 高版（事件列表）
│   │       │   ├── Calendar2x4Tile.kt   # 2×4 详细版（时间轴）
│   │       │   └── Calendar4x4Tile.kt   # 4×4 大型版（月视图）
│   │       ├── todo/                # 待办瓷砖变体（6个尺寸）
│   │       │   ├── Todo1x1Tile.kt       # 1×1 简约版（图标）
│   │       │   ├── Todo1x2Tile.kt       # 1×2 紧凑版（任务标题）
│   │       │   ├── Todo2x2Tile.kt       # 2×2 标准版（完成计数）
│   │       │   ├── Todo4x2Tile.kt       # 4×2 高版（待办列表）
│   │       │   ├── Todo2x4Tile.kt       # 2×4 详细版（任务统计）
│   │       │   └── Todo4x4Tile.kt       # 4×4 大型版（仪表盘）
│   │       ├── news/                # 新闻瓷砖变体（6个尺寸）
│   │       │   ├── News1x1Tile.kt       # 1×1 简约版（图标）
│   │       │   ├── News1x2Tile.kt       # 1×2 紧凑版（标题）
│   │       │   ├── News2x2Tile.kt       # 2×2 标准版（摘要）
│   │       │   ├── News4x2Tile.kt       # 4×2 高版（新闻列表）
│   │       │   ├── News2x4Tile.kt       # 2×4 详细版（详情）
│   │       │   └── News4x4Tile.kt       # 4×4 大型版（仪表盘）
│   │       ├── common/              # 公共组件
│   │       │   └── ErrorTile.kt     # 错误瓷砖（配置错误提示）
│   │       ├── legacy/              # 遗留组件（向后兼容）
│   │       │   └── TileComponents.kt # 旧组件库（已废弃）
│   │       └── special/             # 特殊瓷砖（示例）
│   │           ├── PhotoTile.kt     # 照片瓷砖
│   │           ├── MusicTile.kt     # 音乐瓷砖
│   │           ├── ContactTile.kt   # 联系人瓷砖
│   │           └── MailTile.kt      # 邮件瓷砖
│   ├── screen/                      # 页面级 Composable
│   │   ├── DashboardScreen.kt       # 主页面（配置驱动）
│   │   ├── InteractionDemoScreen.kt # 交互演示页面
│   │   ├── AnimationDemoScreen.kt   # 动画演示页面
│   │   ├── PresetsDemoScreen.kt     # 预设演示页面
│   │   ├── ScaleTestScreen.kt       # 缩放测试页面
│   │   └── ResponsiveTestScreen.kt  # 响应式系统测试页面 ⭐ 新增
│   ├── theme/                       # Material3 主题配置
│   │   ├── Color.kt                 # 基础颜色定义
│   │   ├── MetroColors.kt           # Metro 配色方案（高饱和度）
│   │   ├── Type.kt                  # 字体配置（Thin/Light）
│   │   ├── Theme.kt                 # Material3 主题
│   │   ├── MetroTheme.kt            # Metro 主题系统
│   │   ├── MetroEasing.kt           # Metro 缓动函数
│   │   ├── MetroTypography.kt       # 响应式字号系统
│   │   ├── MetroSpacing.kt          # 响应式间距系统 ⭐ 新增
│   │   ├── MetroPadding.kt          # 响应式内边距系统 ⭐ 新增
│   │   ├── MetroIconSize.kt         # 响应式图标尺寸系统 ⭐ 新增
│   │   └── MetroScaleSystem.kt      # 容器级全局缩放系统
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

### 通用规范

- **包命名**: 全小写,无下划线 (如 `top.yaotutu.deskmate.data.model`)
- **类命名**: 大驼峰 PascalCase (如 `UserViewModel`)
- **函数/变量**: 小驼峰 camelCase (如 `loadUserData`)
- **Composable 函数**: 大驼峰 PascalCase (如 `UserScreen`)
- **常量**: 全大写 + 下划线 (如 `MAX_RETRY_COUNT`)
- 遵循 [Kotlin 官方编码规范](https://kotlinlang.org/docs/coding-conventions.html)

### 瓷砖命名规范 ⭐ 重要

**文件命名**：使用 `{Type}{Size}Tile.kt` 格式

```
✅ 推荐：
- Clock1x1Tile.kt    # 1×1 时钟
- Clock2x2Tile.kt    # 2×2 时钟
- Clock4x2Tile.kt    # 4×2 时钟
- Music2x2Tile.kt    # 2×2 音乐

❌ 不推荐：
- ClockSimpleTile.kt   # 语义化命名不直观
- ClockStandardTile.kt # 无法直接看出尺寸
- ClockLargeTile.kt    # "大型" 概念模糊
```

**Variant ID 命名**：配置文件中使用尺寸格式

```json
✅ 推荐：
{ "type": "clock", "variant": "1x1", "columns": 1, "rows": 1 }
{ "type": "clock", "variant": "2x2", "columns": 2, "rows": 2 }
{ "type": "clock", "variant": "2x4", "columns": 4, "rows": 2 }

❌ 不推荐：
{ "type": "clock", "variant": "simple", "columns": 1, "rows": 1 }
{ "type": "clock", "variant": "standard", "columns": 2, "rows": 2 }
{ "type": "clock", "variant": "detailed", "columns": 4, "rows": 2 }
```

**优势**：
- ✅ 一眼看出瓷砖尺寸
- ✅ 配置文件更加直观
- ✅ variant 与 columns/rows 一致，避免混淆
- ✅ 新手友好，无需记忆语义对应关系

## 瓷砖组件使用指南 ⭐ 重点

### 核心理念：配置驱动 + 数据绑定 + 预设样式

当前项目采用 **配置驱动** 模式，开发者通过 **JSON 配置文件** 或 **代码配置** 定义瓷砖布局，然后 ViewModel 提供动态数据。

**新增预设系统** (2025-01-31)：提供 38 种按尺寸分类的预设样式，开发者只需传递数据，自动应用符合 Metro 设计规范的布局。详见 [docs/TILE_PRESETS_GUIDE.md](./docs/TILE_PRESETS_GUIDE.md)。

### 方式一：配置驱动（推荐） ⭐

#### 1. 定义配置文件（JSON）

在 `assets/` 目录创建 `layout_config.json`：

```json
{
  "columns": 6,
  "rows": 4,
  "areas": [
    "C C C C W W",
    "C C C C W W",
    "A A . . . .",
    ". . . . . ."
  ],
  "tiles": {
    "C": {"type": "clock", "variant": "2x4"},
    "W": {"type": "weather", "variant": "2x2"},
    "A": {"type": "calendar", "variant": "2x2"}
  }
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
                    // maxHeight 需要包含瓷砖间的间距
                    // 4行瓷砖 = baseCellSize*4 + 中间3个间距
                    maxHeight = baseCellSize * 4 + dynamicGap * 3
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
    columns = 6,
    rows = 4,
    areas = listOf(
        "C C C C W W",
        "C C C C W W",
        "A A . . . .",
        ". . . . . ."
    ),
    tiles = mapOf(
        "C" to TileDefinition("clock", "4x2"),
        "W" to TileDefinition("weather", "2x2"),
        "A" to TileDefinition("calendar", "2x2")
    )
)
```

### 可用的瓷砖变体

**变体覆盖总览**（截至 2025-11-03）：

所有业务瓷砖类型现已支持完整的 6 个尺寸变体，提供从简约到全功能的渐进式展示：

| 类型 | 1×1 | 1×2 | 2×2 | 4×2 | 2×4 | 4×4 | 总计 |
|------|-----|-----|-----|-----|-----|-----|------|
| Clock | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 6 |
| Weather | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 6 |
| Calendar | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 6 |
| Todo | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 6 |
| News | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 6 |
| **总计** | **5** | **5** | **5** | **5** | **5** | **5** | **30** |

**设计理念**：
- **1×1（简约版）**：图标展示，快速识别
- **1×2（紧凑版）**：核心信息，空间高效
- **2×2（标准版）**：平衡显示，常用尺寸
- **4×2（高版）**：垂直列表，多项展示
- **2×4（详细版）**：横向扩展，丰富信息
- **4×4（大型版）**：仪表盘视图，全面分析

#### 时钟瓷砖（Clock）

| 变体 | 尺寸 | 特点 | 配置 |
|-----|------|------|------|
| **1x1** | 1×1 | 简约版，仅时间 | `{"type":"clock","variant":"1x1","columns":1,"rows":1}` |
| **1x2** | 1×2 | 紧凑版，时间+日期 | `{"type":"clock","variant":"1x2","columns":2,"rows":1}` |
| **2x2** | 2×2 | 标准版，时间+日期+星期 | `{"type":"clock","variant":"2x2","columns":2,"rows":2}` |
| **4x2** | 4×2 | 高版，纵向布局+农历 | `{"type":"clock","variant":"4x2","columns":2,"rows":4}` |
| **2x4** | 2×4 | 详细版，翻转动画+农历 | `{"type":"clock","variant":"2x4","columns":4,"rows":2}` |
| **4x4** | 4×4 | 大型版，完整信息展示 | `{"type":"clock","variant":"4x4","columns":4,"rows":4}` |

#### 天气瓷砖（Weather）

| 变体 | 尺寸 | 特点 | 配置 |
|-----|------|------|------|
| **1x1** | 1×1 | 简约版，天气图标 | `{"type":"weather","variant":"1x1","columns":1,"rows":1}` |
| **1x2** | 1×2 | 紧凑版，温度+天气状况 | `{"type":"weather","variant":"1x2","columns":2,"rows":1}` |
| **2x2** | 2×2 | 标准版，完整天气信息 | `{"type":"weather","variant":"2x2","columns":2,"rows":2}` |
| **4x2** | 4×2 | 高版，天气预报列表 | `{"type":"weather","variant":"4x2","columns":2,"rows":4}` |
| **2x4** | 2×4 | 详细版，周视图预报 | `{"type":"weather","variant":"2x4","columns":4,"rows":2}` |
| **4x4** | 4×4 | 大型版，天气仪表盘 | `{"type":"weather","variant":"4x4","columns":4,"rows":4}` |

#### 日历瓷砖（Calendar）

| 变体 | 尺寸 | 特点 | 配置 |
|-----|------|------|------|
| **1x1** | 1×1 | 简约版，日历图标 | `{"type":"calendar","variant":"1x1","columns":1,"rows":1}` |
| **1x2** | 1×2 | 紧凑版，日期+星期 | `{"type":"calendar","variant":"1x2","columns":2,"rows":1}` |
| **2x2** | 2×2 | 标准版，日期+事件 | `{"type":"calendar","variant":"2x2","columns":2,"rows":2}` |
| **4x2** | 4×2 | 高版，事件列表 | `{"type":"calendar","variant":"4x2","columns":2,"rows":4}` |
| **2x4** | 2×4 | 详细版，时间轴视图 | `{"type":"calendar","variant":"2x4","columns":4,"rows":2}` |
| **4x4** | 4×4 | 大型版，月视图 | `{"type":"calendar","variant":"4x4","columns":4,"rows":4}` |

#### 待办瓷砖（Todo）

| 变体 | 尺寸 | 特点 | 配置 |
|-----|------|------|------|
| **1x1** | 1×1 | 简约版，待办图标 | `{"type":"todo","variant":"1x1","columns":1,"rows":1}` |
| **1x2** | 1×2 | 紧凑版，任务标题 | `{"type":"todo","variant":"1x2","columns":2,"rows":1}` |
| **2x2** | 2×2 | 标准版，完成计数 | `{"type":"todo","variant":"2x2","columns":2,"rows":2}` |
| **4x2** | 4×2 | 高版，待办列表 | `{"type":"todo","variant":"4x2","columns":2,"rows":4}` |
| **2x4** | 2×4 | 详细版，任务统计 | `{"type":"todo","variant":"2x4","columns":4,"rows":2}` |
| **4x4** | 4×4 | 大型版，任务仪表盘 | `{"type":"todo","variant":"4x4","columns":4,"rows":4}` |

#### 新闻瓷砖（News）

| 变体 | 尺寸 | 特点 | 配置 |
|-----|------|------|------|
| **1x1** | 1×1 | 简约版，新闻图标 | `{"type":"news","variant":"1x1","columns":1,"rows":1}` |
| **1x2** | 1×2 | 紧凑版，新闻标题 | `{"type":"news","variant":"1x2","columns":2,"rows":1}` |
| **2x2** | 2×2 | 标准版，新闻摘要 | `{"type":"news","variant":"2x2","columns":2,"rows":2}` |
| **4x2** | 4×2 | 高版，新闻列表 | `{"type":"news","variant":"4x2","columns":2,"rows":4}` |
| **2x4** | 2×4 | 详细版，新闻详情 | `{"type":"news","variant":"2x4","columns":4,"rows":2}` |
| **4x4** | 4×4 | 大型版，新闻仪表盘 | `{"type":"news","variant":"4x4","columns":4,"rows":4}` |

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

### 字号使用规范 ⭐ 重要

**核心原则**：Single Source of Truth（单一数据源）

**必须使用 MetroTypography 设计令牌系统**，禁止硬编码字号！

```kotlin
// ✅ 正确：使用 MetroTypography
import top.yaotutu.deskmate.presentation.theme.MetroTypography

Text(
    text = "10:12",
    fontSize = MetroTypography.displayHuge(),  // 超大号，自动适配屏幕密度
    fontWeight = FontWeight.Thin
)

Text(
    text = "星期一",
    fontSize = MetroTypography.bodyLarge(),  // 正文大号
    fontWeight = FontWeight.Light
)

// ❌ 禁止：硬编码字号
Text(text = "10:12", fontSize = 96.sp)  // 硬编码，不适配高密度屏幕
Text(text = "星期一", fontSize = 20.sp)  // 硬编码，维护困难
```

**可用的字号级别**（根据屏幕密度自动调整）：

| 方法 | 标准密度 | 中密度 | 高密度 | 典型用途 |
|------|---------|--------|--------|---------|
| `displayHuge()` | 96sp | 120sp | 160sp | 主要时间、温度显示 |
| `displayLarge()` | 72sp | 96sp | 120sp | 主标题、日期显示 |
| `displayMedium()` | 48sp | 64sp | 80sp | 副标题、图标 |
| `titleLarge()` | 36sp | 40sp | 48sp | 小瓷砖主内容 |
| `bodyLarge()` | 20sp | 24sp | 28sp | 日期、天气状况 |
| `bodyMedium()` | 16sp | 18sp | 22sp | 次要文字、描述 |
| `bodySmall()` | 14sp | 16sp | 18sp | 辅助文字、农历 |
| `labelSmall()` | 12sp | 14sp | 16sp | 标签、角标 |

**优势**：
- ✅ 自动适配不同密度屏幕（213dpi、520dpi等）
- ✅ 统一管理，修改一处全局生效
- ✅ 类型安全，避免魔法数字
- ✅ 未来可轻松扩展（主题切换、暗色模式等）

**重构说明**（2025-01-05）：
- ✅ 新增 `MetroTypography.kt` 设计令牌系统
- ✅ 更新 6 个预设文件（SmallTilePresets, CompactTilePresets等）
- ✅ 更新 10 个硬编码瓷砖文件（Clock4x2, Calendar4x4等）
- ✅ 所有字号统一引用 MetroTypography，禁止硬编码

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

// presentation/screen/DashboardScreen.kt
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
            ProvideTileGrid(baseCellSize, dynamicGap, columns) {
                VerticalPriorityLayout(
                    tiles = layoutConfig.tiles,
                    baseCellSize = baseCellSize,
                    dynamicGap = dynamicGap,
                    maxHeight = baseCellSize * 4 + dynamicGap * 3
                ) { config, index ->
                    // 工厂根据配置创建瓷砖，自动绑定 uiState 数据
                    TileFactory.CreateTile(config, uiState, index)
                }
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

**配置与工厂**
- ✅ **使用配置驱动** - 通过 JSON 或代码配置定义布局，而不是硬编码
- ✅ **使用工厂模式** - 通过 TileFactory 创建瓷砖，而不是直接实例化组件
- ✅ **注册变体** - 在 TileRegistryInit 中注册所有变体
- ✅ **使用 BaseTile** - 新组件应基于 BaseTile 和 TileSpec 构建
- ✅ **使用预设样式** - 优先使用 `XxxTilePresets` 中的预设布局，减少重复代码
- ✅ **使用 VerticalPriorityLayout** - 让布局引擎自动计算位置

**文件组织**
- ✅ **扁平化结构** - 避免过度嵌套，保持目录层级在 3-4 层以内
- ✅ **尺寸命名** - 新瓷砖使用尺寸格式命名（如 `Clock1x1Tile.kt`）
- ✅ **功能分组** - 按功能分组文件（animation/、tiles/、factory/ 等）
- ✅ **路径一致性** - 确保 package 声明与文件路径一致

**禁止事项**
- ❌ 不要手动计算瓷砖位置和尺寸
- ❌ 不要跳过工厂直接使用组件（除非在演示页面）
- ❌ 不要忘记在 TileRegistry 中注册新变体
- ❌ 不要重复编写已有的布局代码（检查预设系统是否有对应模板）
- ❌ 不要使用语义化 variant ID（如 "simple"），使用尺寸格式（如 "1x1"）
- ❌ 不要创建不必要的中间层目录（如 ui/）

**响应式系统使用原则** ⭐ 重要
- ✅ **使用 MetroSpacing** - 所有 `Spacer` 和 `Arrangement.spacedBy` 必须使用 MetroSpacing
- ✅ **使用 MetroPadding** - 所有 `.padding()` 必须使用 MetroPadding（优先使用 `auto()`）
- ✅ **使用 MetroIconSize** - 所有图标尺寸必须使用 MetroIconSize
- ✅ **使用 MetroTypography** - 所有字号必须使用 MetroTypography（已强制要求）
- ✅ **使用 LocalTileBaseUnit** - 自定义宽度/高度使用 `LocalTileBaseUnit.current * ratio`
- ✅ **固定比例原则** - 所有间距/内边距/图标尺寸使用固定比例（不随瓷砖尺寸变化）

**禁止硬编码**
- ❌ 禁止使用 `8.dp`、`16.dp` 等硬编码间距
- ❌ 禁止使用 `12.dp`、`16.dp` 等硬编码内边距
- ❌ 禁止使用 `24.dp`、`48.dp` 等硬编码图标尺寸
- ❌ 禁止使用 `14.sp`、`18.sp` 等硬编码字号

```kotlin
// ✅ 正确：使用响应式系统
import top.yaotutu.deskmate.presentation.theme.MetroSpacing
import top.yaotutu.deskmate.presentation.theme.MetroPadding
import top.yaotutu.deskmate.presentation.theme.MetroIconSize
import top.yaotutu.deskmate.presentation.component.base.LocalTileBaseUnit

Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(MetroPadding.medium()),  // ✓ 响应式内边距
    verticalArrangement = Arrangement.spacedBy(MetroSpacing.large())  // ✓ 响应式间距
) {
    Icon(
        modifier = Modifier.size(MetroIconSize.large()),  // ✓ 响应式图标
        // ...
    )
    Text(
        text = "标题",
        fontSize = MetroTypography.bodyLarge()  // ✓ 响应式字号
    )
    Spacer(modifier = Modifier.height(MetroSpacing.medium()))  // ✓ 响应式间距
}

// ❌ 错误：硬编码尺寸
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),  // ✗ 硬编码
    verticalArrangement = Arrangement.spacedBy(8.dp)  // ✗ 硬编码
) {
    Icon(
        modifier = Modifier.size(48.dp),  // ✗ 硬编码
        // ...
    )
    Text(
        text = "标题",
        fontSize = 18.sp  // ✗ 硬编码
    )
}
```

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

**Step 1**: 在 `component/tiles/clock/` 目录创建新变体文件

```kotlin
// presentation/component/tiles/clock/Clock3x3Tile.kt (示例：自定义3×3尺寸)
/**
 * 时钟瓷砖 3×3 - 自定义版（示例）
 *
 * 特性：
 * - 自定义尺寸展示
 * - 演示如何创建新的时钟变体
 * - 使用 Flip 翻转动画
 */
@Composable
fun Clock3x3Tile(
    time: String,
    date: String,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current

    BaseTile(
        spec = TileSpec(3, 3, MetroColors.Blue, AnimationType.FLIP),
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
// presentation/component/factory/TileRegistryInit.kt
fun initializeTileRegistry() {
    // ... 现有注册代码 ...

    // 注册新的 3x3 变体（使用尺寸命名规范）
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "3x3",  // ⭐ 使用尺寸格式命名
            supportedSizes = listOf(3 to 3),  // 支持的尺寸
            defaultSize = 3 to 3  // 默认尺寸
        ) { config, uiState ->
            Clock3x3Tile(
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
    { "type": "clock", "variant": "3x3", "columns": 3, "rows": 3 }
  ]
}
```

#### 方式二：添加新瓷砖类型

为项目添加全新的瓷砖类型（如 music）：

**Step 1**: 创建瓷砖目录和文件

```kotlin
// presentation/component/tiles/music/Music2x2Tile.kt
/**
 * 音乐瓷砖 2×2 - 标准版（示例）
 *
 * 特性：
 * - 显示当前播放的歌曲和艺术家
 * - 使用 Pulse 脉冲动画
 * - 青色背景（Metro 风格）
 */
@Composable
fun Music2x2Tile(
    songName: String,
    artist: String,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(2, 2, Color(0xFF00ABA9), AnimationType.PULSE),
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
// presentation/component/factory/TileRegistryInit.kt
TileRegistry.register(
    TileVariantSpec(
        type = "music",
        variant = "2x2",  // ⭐ 使用尺寸格式命名（推荐）
        supportedSizes = listOf(2 to 2),
        defaultSize = 2 to 2
    ) { config, uiState ->
        Music2x2Tile(
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
    { "type": "music", "variant": "2x2", "columns": 2, "rows": 2 }
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
1. **检查加载的文件名** - 确认 DashboardScreen 中 `repository.loadLayoutConfig()` 加载的是哪个文件
   ```kotlin
   // 检查 DashboardScreen.kt 第 36 行左右
   val layoutConfig = remember {
       repository.loadLayoutConfig("clock_showcase.json")  // 实际加载的文件
   }
   ```
2. 确认 JSON 文件放在 `assets/` 目录
3. 检查 JSON 格式是否正确（使用 JSON 验证器）
4. 确认所有配置文件都已更新（如果重构了 variant ID）
5. 执行 clean build 并重新安装：
   ```bash
   ./gradlew clean && ./gradlew assembleDebug
   adb uninstall top.yaotutu.deskmate
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
6. 如果 JSON 加载失败，会自动使用默认配置

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
4. 检查 logcat 输出：
   ```bash
   adb logcat -d | grep TileFactory  # 查看工厂创建日志
   adb logcat -d | grep LayoutConfig # 查看配置加载日志
   ```

### Q: 重构后如何保证代码正确性？
**A**:
1. **编译检查** - 确保 `./gradlew clean && ./gradlew assembleDebug` 成功
2. **路径验证** - 使用 IDE 的 "Find Usages" 检查所有引用是否正确
3. **配置更新** - 确保所有 JSON 配置文件都已同步更新
4. **运行测试** - 截图验证应用实际运行效果
5. **文档同步** - 更新 CLAUDE.md 和相关文档

### Q: 旧的语义化命名还能用吗？
**A**: 不推荐，但如果需要向后兼容：
1. 可以在 TileRegistry 中同时注册两种 variant ID：
   ```kotlin
   // 同时支持 "1x1" 和 "simple"
   TileRegistry.register(/* variant = "1x1" */)
   TileRegistry.register(/* variant = "simple" */)  // 向后兼容
   ```
2. 新功能统一使用尺寸命名（如 `1x1`）
3. 逐步迁移旧配置文件

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
- **Clock1x1Tile.kt** - 1×1 简约版（仅时间）
- **Clock1x2Tile.kt** - 1×2 紧凑版（时间+日期）
- **Clock2x2Tile.kt** - 2×2 标准版（时间+日期+星期）
- **Clock4x2Tile.kt** - 4×2 高版（垂直布局+农历）
- **Clock2x4Tile.kt** - 2×4 详细版（翻转动画+农历）
- **Clock4x4Tile.kt** - 4×4 大型版（完整信息展示）

#### 天气瓷砖（6个变体）⭐ 新增
- **Weather1x1Tile.kt** - 1×1 简约版（天气图标）
- **Weather1x2Tile.kt** - 1×2 紧凑版（温度+状况）
- **Weather2x2Tile.kt** - 2×2 标准版（完整天气信息）
- **Weather4x2Tile.kt** - 4×2 高版（天气预报列表）
- **Weather2x4Tile.kt** - 2×4 详细版（周视图预报）
- **Weather4x4Tile.kt** - 4×4 大型版（天气仪表盘）

#### 日历瓷砖（6个变体）⭐ 新增
- **Calendar1x1Tile.kt** - 1×1 简约版（日历图标）
- **Calendar1x2Tile.kt** - 1×2 紧凑版（日期+星期）
- **Calendar2x2Tile.kt** - 2×2 标准版（日期+事件）
- **Calendar4x2Tile.kt** - 4×2 高版（事件列表）
- **Calendar2x4Tile.kt** - 2×4 详细版（时间轴视图）
- **Calendar4x4Tile.kt** - 4×4 大型版（月视图）

#### 待办瓷砖（6个变体）⭐ 新增
- **Todo1x1Tile.kt** - 1×1 简约版（待办图标）
- **Todo1x2Tile.kt** - 1×2 紧凑版（任务标题）
- **Todo2x2Tile.kt** - 2×2 标准版（完成计数）
- **Todo4x2Tile.kt** - 4×2 高版（待办列表）
- **Todo2x4Tile.kt** - 2×4 详细版（任务统计）
- **Todo4x4Tile.kt** - 4×4 大型版（任务仪表盘）

#### 新闻瓷砖（6个变体）⭐ 新增
- **News1x1Tile.kt** - 1×1 简约版（新闻图标）
- **News1x2Tile.kt** - 1×2 紧凑版（新闻标题）
- **News2x2Tile.kt** - 2×2 标准版（新闻摘要）
- **News4x2Tile.kt** - 4×2 高版（新闻列表）
- **News2x4Tile.kt** - 2×4 详细版（新闻详情）
- **News4x4Tile.kt** - 4×4 大型版（新闻仪表盘）

#### 公共组件
- **ErrorTile.kt** - 错误瓷砖（配置错误提示）

### 遗留组件（向后兼容，已废弃）

- **legacy/TileComponents.kt** - 旧的高级组件库（已被上述变体替代）

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
