# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Deskmate 是一个基于 Kotlin + Jetpack Compose 的现代化 Android 桌面小部件应用，采用 **Windows Phone 风格的动态瓷砖设计**和 MVVM 架构模式。

### 核心特性

- 🎨 **Windows Phone Metro 设计语言** - 经典的扁平化动态瓷砖
- 🏭 **工厂模式 + 变体系统** - 灵活的组件注册和创建机制
- 📝 **配置驱动布局** - 通过 JSON 配置文件定义瓷砖布局
- 🎛️ **单一主页面架构** - DashboardScreen 作为唯一主页，通过修改 JSON 配置文件调整页面布局，无需修改 Kotlin 代码
- 📐 **响应式设计系统** - MetroTypography、MetroSpacing、MetroPadding、MetroIconSize 自动适配不同屏幕

### 核心技术栈

- **语言**: Kotlin 2.0.21 (JDK 11)
- **UI 框架**: Jetpack Compose + Material3
- **架构**: MVVM (Model-View-ViewModel)
- **异步**: Kotlin Coroutines + Flow
- **导航**: Navigation Compose

## 项目架构

### 工厂模式 + 变体系统 ⭐ 核心架构

```
配置层 (JSON)
    ↓ 加载
DashboardScreen (唯一主页)
    ↓ 使用
TileFactory (工厂)
    ↓ 创建
业务瓷砖 (Clock, Weather, Calendar, Todo, News)
    ↓ 基于
BaseTile + TileSpec (基础组件)
```

### 目录结构（精简版）

```
app/src/main/
├── assets/
│   └── layout_unified.json  # 统一布局配置 (8行×18列，平板显示全部，手机显示前4行)
├── java/top/yaotutu/deskmate/
│   ├── data/
│   │   ├── model/           # 数据模型 (LayoutConfig, TileConfig, TileVariantSpec)
│   │   └── repository/      # Repository 层 (LayoutConfigRepository)
│   ├── navigation/          # 导航配置 (NavGraph, Screen)
│   └── presentation/
│       ├── component/
│       │   ├── base/       # 基础组件 (BaseTile, TileGrid, TileSpec)
│       │   ├── factory/    # 工厂层 ⭐ (TileFactory, TileRegistryInit)
│       │   └── tiles/      # 业务瓷砖 (clock/, weather/, calendar/, todo/, news/)
│       ├── screen/         # 页面 (DashboardScreen - 唯一主页)
│       ├── theme/          # 响应式设计系统 (MetroTypography, MetroSpacing, etc.)
│       └── viewmodel/      # ViewModel (DashboardViewModel)
└── MainActivity.kt
```

## 开发最佳实践 ⭐ 必读

### 1. 架构使用原则

**配置驱动开发**
- ✅ **页面布局调整原则** - 后期调整页面展示布局时，只需修改 JSON 配置文件（layout_unified.json），**不应修改 Kotlin 代码**（DashboardScreen.kt 等）
- ✅ **使用工厂模式** - 通过 TileFactory 创建瓷砖，而不是直接实例化组件
- ✅ **注册变体** - 在 TileRegistryInit 中注册所有变体
- ✅ **使用预设样式** - 优先使用 `XxxTilePresets` 中的预设布局，减少重复代码

**文件命名规范**
- ✅ **尺寸命名** - 新瓷砖使用尺寸格式命名：`Clock1x1Tile.kt`、`Clock2x2Tile.kt`（不要用 ClockSimpleTile、ClockStandardTile）
- ✅ **Variant ID** - 配置文件中使用尺寸格式：`"variant": "1x1"`（不要用 "simple"、"standard"）

**禁止事项**
- ❌ **不要手动计算瓷砖位置和尺寸** - 让布局引擎自动计算
- ❌ **不要跳过工厂直接使用组件** - 除非在演示页面
- ❌ **不要忘记注册新变体** - 在 TileRegistryInit 中注册
- ❌ **不要修改 Kotlin 调整布局** - 修改 JSON 配置文件

### 2. 响应式设计系统使用 ⭐ 重要

**禁止硬编码**
- ❌ 禁止使用 `8.dp`、`16.dp` 等硬编码间距
- ❌ 禁止使用 `12.dp`、`16.dp` 等硬编码内边距
- ❌ 禁止使用 `24.dp`、`48.dp` 等硬编码图标尺寸
- ❌ 禁止使用 `14.sp`、`18.sp` 等硬编码字号

**必须使用响应式系统**
```kotlin
// ✅ 正确：使用响应式系统
import top.yaotutu.deskmate.presentation.theme.MetroSpacing
import top.yaotutu.deskmate.presentation.theme.MetroPadding
import top.yaotutu.deskmate.presentation.theme.MetroIconSize
import top.yaotutu.deskmate.presentation.theme.MetroTypography

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
}

// ❌ 错误：硬编码尺寸
Column(
    modifier = Modifier.padding(16.dp),  // ✗ 硬编码
    verticalArrangement = Arrangement.spacedBy(8.dp)  // ✗ 硬编码
) {
    Icon(modifier = Modifier.size(48.dp))  // ✗ 硬编码
    Text(text = "标题", fontSize = 18.sp)  // ✗ 硬编码
}
```

### 3. Metro 设计规范

**字体规范**
- ✅ 使用 **FontWeight.Thin/ExtraLight** 字重
- ❌ 禁止使用粗体（Metro 风格不使用粗体）

**配色规范**
- ✅ 使用 Metro 官方配色（高饱和度纯色）
- ❌ 禁止低饱和度、渐变、阴影

**间距规范**
- 瓷砖间距：8dp（固定）
- 屏幕边距：8dp

## 配置文件说明

### layout_unified.json（统一布局）
- **布局网格**: 8 行 × 18 列
- **平板显示**: 显示全部 8 行（rows 0-7）
- **手机显示**: 仅显示前 4 行（rows 0-3）
- **横向滚动**: 两种设备均支持横向滚动查看所有 18 列
- **无特殊处理**: 手机端没有单独的布局文件，直接展示统一布局的上半部分

### 配置格式示例

```json
{
  "comment": "统一布局 - 8行×18列（手机显示前4行，平板显示全8行）",
  "areas": [
    "C1 C1 C1 C1 W1 W1 W1 W1 N1 N1 N1 N1 A1 A1 T1 T1 T1 T1",
    "C1 C1 C1 C1 W1 W1 W1 W1 N1 N1 N1 N1 A1 A1 T1 T1 T1 T1",
    ...
  ],
  "tiles": {
    "C1": {"type": "clock", "variant": "4x4"},
    "W1": {"type": "weather", "variant": "2x4"},
    "A1": {"type": "calendar", "variant": "1x1"}
  }
}
```

## 可用的瓷砖变体

⭐ **重要说明：变体命名规则为 AxB = A行×B列（第一个数字是行数）**

所有业务瓷砖类型支持以下 **5 种标准尺寸**：

| 类型 | 1×1 | 2×2 | 2×4 | 4×2 | 4×4 |
|------|-----|-----|-----|-----|-----|
| Clock | ✅ | ✅ | ✅ | ✅ | ✅ |
| Weather | ✅ | ✅ | ✅ | ✅ | ✅ |
| Calendar | ✅ | ✅ | ✅ | ✅ | ✅ |
| Todo | ✅ | ✅ | ✅ | ✅ | ✅ |
| News | ✅ | ✅ | ✅ | ✅ | ✅ |

**设计理念**：
- **1×1（简约版）**：图标展示，快速识别
- **2×2（标准版）**：平衡显示，常用尺寸
- **2×4（宽版）**：横向扩展，丰富信息
- **4×2（高版）**：垂直列表，多项展示
- **4×4（大型版）**：仪表盘视图，全面分析

## 添加新瓷砖变体流程

### Step 1: 创建瓷砖文件
```kotlin
// presentation/component/tiles/clock/Clock2x2Tile.kt
@Composable
fun Clock2x2Tile(
    time: String,
    date: String,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.medium(MetroColors.Blue),  // 使用预设 spec
        modifier = modifier
    ) {
        // 自定义布局或使用 Preset
        MediumTilePresets.TitleSubtitle(
            title = time,
            subtitle = date
        )
    }
}
```

### Step 2: 注册变体
```kotlin
// presentation/component/factory/TileRegistryInit.kt
TileRegistry.register(
    TileVariantSpec(
        type = "clock",
        variant = "2x2",  // ⭐ 使用标准尺寸格式（2行×2列）
        supportedSizes = listOf(2 to 2),  // ⭐ 格式：(rows, columns)
        defaultSize = 2 to 2
    ) { config, uiState ->
        Clock2x2Tile(
            time = uiState.currentTime,
            date = uiState.currentDate
        )
    }
)
```

### Step 3: 在配置中使用
```json
{
  "tiles": {
    "C": {"type": "clock", "variant": "2x2"}
  }
}
```

## 常见问题

### Q: 如何调整页面布局？
**A**: 只需修改 `assets/layout_unified.json`，不要修改 Kotlin 代码。平板会显示全部 8 行，手机会自动显示前 4 行。

### Q: 瓷砖显示为错误提示（ErrorTile）？
**A**: 检查以下几点：
1. 确认 `type:variant` 在 TileRegistry 中已注册
2. 检查配置的 `columns` 和 `rows` 是否在变体的 `supportedSizes` 中
3. 确认在 Application/MainActivity 中调用了 `initializeTileRegistry()`

### Q: 如何添加新的瓷砖类型？
**A**: 参考上面的"添加新瓷砖变体流程"。

## 项目配置

- **namespace**: `top.yaotutu.deskmate`
- **minSdk**: 24 (Android 7.0)
- **targetSdk**: 36
- **compileSdk**: 36

---

## ⭐ 2025-01-10 动画系统重大重构

### 新的 AnimationScope DSL API

所有动画现在统一通过 AnimationScope DSL 配置,实现了：
- ✅ **API 一致性** - 简单动画和复杂动画使用相同模式
- ✅ **零配置体验** - Preset 自动选择最佳动画
- ✅ **类型安全** - 编译时检查动画配置
- ✅ **代码简洁** - 消除 400+ 行样板代码

### 使用示例

```kotlin
// 1. 简单内容
BaseTile(spec = TileSpec.small(color)) {
    single { Text("内容") }
}

// 2. 使用 Preset（推荐）
BaseTile(spec = TileSpec.square(color)) {
    with(MediumTilePresets) {
        TitleSubtitle(title = "标题", subtitle = "副标题")
    }
}

// 3. 翻转动画
BaseTile(spec = TileSpec.square(color, AnimationType.FLIP)) {
    flip(
        front = { Text("正面") },
        back = { Text("背面") }
    )
}

// 4. 滑动动画
BaseTile(spec = TileSpec.wideMedium(color, AnimationType.SLIDE)) {
    slide(
        { NewsItem("新闻1") },
        { NewsItem("新闻2") },
        { NewsItem("新闻3") }
    )
}
```

### 完整的 AnimationScope DSL 方法

| DSL 方法 | 适用动画 | 说明 |
|---------|---------|------|
| `single { }` | PULSE, ROTATE, SHIMMER 等 | 单一内容 |
| `flip(front, back)` | FLIP | 翻转动画 |
| `slide(...)` | SLIDE | 滑动轮播 |
| `fade(...)` | FADE | 淡入淡出 |
| `counter(value) { }` | COUNTER | 数字滚动 |
| `peek(main, peek)` | PEEK | 探出动画 |
| `marquee { }` | MARQUEE | 跑马灯 |
| `wipe(...)` | WIPE | 擦除切换 |

### 迁移完成状态

- ✅ 核心框架（AnimationScope.kt, BaseTile.kt）
- ✅ Preset 系统（6个文件）
- ✅ 业务瓷砖（38个文件）
- ✅ TileRegistryInit.kt
- ✅ 演示页面（AnimationDemoScreen.kt, AnimationDemoTiles.kt）
- ✅ 编译验证通过
