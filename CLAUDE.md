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
│   ├── layout_tablet.json    # 平板布局配置 (8行×14列)
│   └── layout_phone.json     # 手机布局配置 (4行×10列)
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
- ✅ **页面布局调整原则** - 后期调整页面展示布局时，只需修改 JSON 配置文件（layout_tablet.json、layout_phone.json），**不应修改 Kotlin 代码**（DashboardScreen.kt 等）
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

### layout_tablet.json（平板布局）
- 8 行 × 14 列
- 支持横向滚动
- 46 个瓷砖

### layout_phone.json（手机布局）
- 4 行 × 10 列
- 支持横向滚动
- 16 个瓷砖

### 配置格式示例

```json
{
  "comment": "平板布局 - 8行×14列",
  "areas": [
    "C C W W A B P P Q Q R S a b",
    "C C W W D E P P Q Q T U c d",
    ...
  ],
  "tiles": {
    "C": {"type": "clock", "variant": "2x2"},
    "W": {"type": "weather", "variant": "2x2"},
    "A": {"type": "calendar", "variant": "1x1"}
  }
}
```

## 可用的瓷砖变体

所有业务瓷砖类型支持完整的 6 个尺寸变体：

| 类型 | 1×1 | 1×2 | 2×2 | 4×2 | 2×4 | 4×4 |
|------|-----|-----|-----|-----|-----|-----|
| Clock | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Weather | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Calendar | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Todo | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| News | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |

**设计理念**：
- **1×1（简约版）**：图标展示，快速识别
- **1×2（紧凑版）**：核心信息，空间高效
- **2×2（标准版）**：平衡显示，常用尺寸
- **4×2（高版）**：垂直列表，多项展示
- **2×4（详细版）**：横向扩展，丰富信息
- **4×4（大型版）**：仪表盘视图，全面分析

## 添加新瓷砖变体流程

### Step 1: 创建瓷砖文件
```kotlin
// presentation/component/tiles/clock/Clock3x3Tile.kt
@Composable
fun Clock3x3Tile(
    time: String,
    date: String,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(3, 3, MetroColors.Blue, AnimationType.FLIP),
        modifier = modifier
    ) {
        // 自定义布局
    }
}
```

### Step 2: 注册变体
```kotlin
// presentation/component/factory/TileRegistryInit.kt
TileRegistry.register(
    TileVariantSpec(
        type = "clock",
        variant = "3x3",  // ⭐ 使用尺寸格式
        supportedSizes = listOf(3 to 3),
        defaultSize = 3 to 3
    ) { config, uiState ->
        Clock3x3Tile(
            time = uiState.currentTime,
            date = uiState.currentDate
        )
    }
)
```

### Step 3: 在配置中使用
```json
{
  "tiles": [
    { "type": "clock", "variant": "3x3", "columns": 3, "rows": 3 }
  ]
}
```

## 常见问题

### Q: 如何调整页面布局？
**A**: 只需修改 `assets/layout_tablet.json` 或 `assets/layout_phone.json`，不要修改 Kotlin 代码。

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
