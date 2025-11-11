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
│       │   ├── base/       # 基础组件 (BaseTile, TileGrid, TileSpec, AnimationScope)
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

## 添加新瓷砖变体流程 ⭐ 2025-01-10 更新

### Step 1: 创建瓷砖文件
```kotlin
// presentation/component/tiles/clock/Clock2x2Tile.kt
@Composable
fun Clock2x2Tile(
    time: String,
    date: String,
    weekday: String,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(MetroColors.Blue, AnimationType.FLIP),  // ⭐ 指定动画类型
        modifier = modifier
    ) {
        // ⭐ 方式 1：使用 Preset（推荐）
        with(MediumTilePresets) {
            TitleSubtitle(
                title = time,
                subtitle = date,
                backSubtitle = weekday  // Preset 自动处理翻转动画
            )
        }

        // ⭐ 方式 2：自定义动画内容
        // flip(
        //     front = {
        //         Column {
        //             Text(time, fontSize = MetroTypography.displayLarge())
        //             Text(date, fontSize = MetroTypography.bodyMedium())
        //         }
        //     },
        //     back = {
        //         Text(weekday, fontSize = MetroTypography.titleLarge())
        //     }
        // )
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
            date = uiState.currentDate,
            weekday = uiState.currentWeekday
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

### 架构升级概述

**动机**：之前的动画系统存在 API 不一致问题 - 简单动画（PULSE、ROTATE）自动处理，复杂动画（FLIP、SLIDE）需要手动使用辅助函数（FlipContent、SlideContent）。

**解决方案**：通过 AnimationScope DSL 统一所有动画 API，基于 Kotlin Lambda Receiver 模式实现零配置、类型安全的动画系统。

### 核心改进

| 指标 | 改进 |
|------|------|
| **代码简洁性** | BaseTile 从 567 行减少到 98 行（-82%）|
| **API 一致性** | 所有动画使用统一 DSL，消除手动包装器 |
| **类型安全** | 编译时检查动画配置 |
| **可维护性** | 删除 400+ 行样板代码 |

### AnimationScope DSL API ⭐ 核心接口

**BaseTile 新签名**：
```kotlin
@Composable
fun BaseTile(
    spec: TileSpec,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable AnimationScope.() -> Unit  // ⭐ Lambda Receiver
)
```

**使用模式**：

```kotlin
// ✅ 模式 1: 使用 Preset（最推荐）
BaseTile(spec = TileSpec.square(color)) {
    with(MediumTilePresets) {  // ⭐ 建立 AnimationScope 上下文
        TitleSubtitle(
            title = "标题",
            subtitle = "副标题"
        )
    }
}

// ✅ 模式 2: 单一内容（简单动画）
BaseTile(spec = TileSpec.small(color, AnimationType.PULSE)) {
    single {  // ⭐ DSL 方法
        Text("内容", style = MaterialTheme.typography.bodyLarge)
    }
}

// ✅ 模式 3: 翻转动画
BaseTile(spec = TileSpec.square(color, AnimationType.FLIP)) {
    flip(  // ⭐ DSL 方法
        front = { Text("正面") },
        back = { Text("背面") }
    )
}

// ✅ 模式 4: 滑动轮播
BaseTile(spec = TileSpec.wideMedium(color, AnimationType.SLIDE)) {
    slide(  // ⭐ DSL 方法
        { NewsItem("新闻1") },
        { NewsItem("新闻2") },
        { NewsItem("新闻3") }
    )
}

// ❌ 旧模式（已删除）：
BaseTile(spec) {
    FlipContent(  // ✗ 不再支持
        front = { ... },
        back = { ... }
    )
}
```

### 完整的 AnimationScope DSL 方法

| DSL 方法 | 适用动画类型 | 参数 | 说明 |
|---------|-------------|------|------|
| `single { }` | PULSE, ROTATE, SHIMMER, DEPTH, BOUNCE, SHAKE | content: @Composable () -> Unit | 单一内容，自动应用简单动画 |
| `flip(front, back)` | FLIP | front, back: @Composable () -> Unit | 翻转动画，默认 3 秒间隔 |
| `slide(...)` | SLIDE | vararg contents: @Composable () -> Unit | 滑动轮播，默认 3 秒间隔 |
| `fade(...)` | FADE | vararg contents: @Composable () -> Unit | 淡入淡出切换 |
| `counter(value) { }` | COUNTER | targetValue: Int, content: @Composable (Int) -> Unit | 数字滚动动画 |
| `peek(main, peek)` | PEEK | mainContent, peekContent: @Composable () -> Unit | 探出动画 |
| `marquee { }` | MARQUEE | content: @Composable () -> Unit | 跑马灯滚动 |
| `wipe(...)` | WIPE | contents: List<@Composable () -> Unit> | 擦除切换 |

### Preset 系统集成 ⭐ 重要

所有 Preset 函数现在都是 `AnimationScope` 的扩展函数，必须通过 `with()` 调用：

**Preset 文件列表**：
- `SmallTilePresets.kt` - 1×1 瓷砖预设
- `MediumTilePresets.kt` - 2×2 瓷砖预设
- `WideTilePresets.kt` - 2×4 瓷砖预设
- `TallTilePresets.kt` - 4×2 瓷砖预设
- `LargeTilePresets.kt` - 4×4 瓷砖预设
- `CompactTilePresets.kt` - 1×2 瓷砖预设

**示例**：
```kotlin
// ✅ 正确：使用 with() 建立上下文
BaseTile(spec = TileSpec.square(MetroColors.Blue)) {
    with(MediumTilePresets) {
        TitleSubtitle(
            title = "18:17",
            subtitle = "星期二",
            backSubtitle = "11月10日"
        )
    }
}

// ❌ 错误：直接调用会编译失败
BaseTile(spec = TileSpec.square(MetroColors.Blue)) {
    MediumTilePresets.TitleSubtitle(...)  // ✗ 编译错误
}
```

### 技术实现细节

**AnimationScope 接口** (`presentation/component/base/AnimationScope.kt`):
```kotlin
interface AnimationScope {
    fun single(content: @Composable () -> Unit)
    fun flip(front: @Composable () -> Unit, back: @Composable () -> Unit)
    fun slide(vararg contents: @Composable () -> Unit)
    fun fade(vararg contents: @Composable () -> Unit)
    fun counter(targetValue: Int, durationMillis: Int = 2000, content: @Composable (Int) -> Unit)
    fun peek(mainContent: @Composable () -> Unit, peekContent: @Composable () -> Unit, ...)
    fun marquee(direction: MarqueeDirection = MarqueeDirection.LEFT, speed: Float = 30f, ...)
    fun wipe(contents: List<@Composable () -> Unit>, ...)
}
```

**AnimationScopeImpl** 实现：
- 根据 `AnimationType` 自动路由到对应动画组件
- 简单动画（PULSE、ROTATE）通过 `single()` 自动包装
- 复杂动画（FLIP、SLIDE）通过专用方法调用

**BaseTile 简化** (`presentation/component/base/BaseTile.kt`):
```kotlin
@Composable
fun BaseTile(
    spec: TileSpec,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable AnimationScope.() -> Unit
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    var animatedContent: (@Composable () -> Unit)? = null

    // ⭐ 创建 AnimationScope 实例
    val scope = AnimationScopeImpl(
        animationType = spec.animation,
        applyAnimation = { composable -> animatedContent = composable }
    )

    // ⭐ 在 AnimationScope 上下文中执行 content lambda
    scope.content()

    Tile(
        rows = spec.rows,
        columns = spec.columns,
        backgroundColor = spec.color,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        onClick = onClick,
        clickEffect = TileClickEffect.PRESS_SCALE,
        modifier = modifier,
        content = animatedContent ?: {}
    )
}
```

### 迁移完成状态

**已更新文件**（共 48 个）：
- ✅ **核心框架** (2 个)
  - `AnimationScope.kt` - 新创建的 DSL 接口
  - `BaseTile.kt` - 从 567 行简化到 98 行

- ✅ **Preset 系统** (6 个)
  - `SmallTilePresets.kt`
  - `MediumTilePresets.kt`
  - `WideTilePresets.kt`
  - `TallTilePresets.kt`
  - `LargeTilePresets.kt`
  - `CompactTilePresets.kt`

- ✅ **业务瓷砖** (38 个)
  - `clock/` - 6 个文件
  - `weather/` - 7 个文件
  - `calendar/` - 7 个文件
  - `todo/` - 6 个文件
  - `news/` - 6 个文件
  - `special/` - 4 个文件（Contact, Mail, Music, Photo）

- ✅ **工厂注册** (1 个)
  - `TileRegistryInit.kt` - 所有注册代码更新

- ✅ **演示页面** (2 个)
  - `AnimationDemoScreen.kt`
  - `AnimationDemoTiles.kt`

**验证结果**：
- ✅ 编译成功：`BUILD SUCCESSFUL in 11s`
- ✅ 运行验证：所有 16 个瓷砖正常渲染
- ✅ 动画工作：FLIP、SLIDE、PULSE 等动画正常
- ✅ 无性能问题：消除了之前的 Infinity.dp 问题

### 其他修复（2025-01-10）

在重构过程中同时修复了以下问题：

1. **配置文件尺寸错误**
   - 修复 `B1` 和 `C2` 瓷砖变体从 `2x2` 改为 `1x2`
   - 原因：配置中声明为 2×2，但实际在 areas 中只占 1×2 区域

2. **horizontalScroll 导致的 Infinity.dp 问题**
   - 问题：`TileGridContainer` 在 `horizontalScroll` 内接收无限宽度约束
   - 解决：在 `DashboardScreen.kt` 中跳过 `TileGridContainer`，直接计算布局参数
   - 结果：contentWidth 从 `Infinity.dp` 变为正常值（~1325.dp）

### 最佳实践更新

**使用 AnimationScope DSL 的注意事项**：

1. ✅ **优先使用 Preset** - Preset 已经封装了最佳实践，减少重复代码
2. ✅ **使用 with() 调用 Preset** - 必须建立 AnimationScope 上下文
3. ✅ **根据动画类型选择方法** - FLIP 用 flip()，SLIDE 用 slide()
4. ❌ **不要导入旧的辅助函数** - FlipContent、SlideContent 等已删除
5. ❌ **不要跳过 AnimationScope** - 所有 BaseTile 内容必须使用 DSL

**常见错误**：
```kotlin
// ❌ 错误 1：忘记使用 with()
BaseTile(spec) {
    MediumTilePresets.TitleSubtitle(...)  // 编译错误
}

// ✅ 修复：
BaseTile(spec) {
    with(MediumTilePresets) {
        TitleSubtitle(...)
    }
}

// ❌ 错误 2：导入已删除的函数
import top.yaotutu.deskmate.presentation.component.base.FlipContent  // 不存在

// ✅ 修复：使用 DSL
BaseTile(spec) {
    flip(front = { ... }, back = { ... })
}
```
