# 瓷砖交互动效使用指南

本指南介绍如何在 Deskmate 瓷砖中使用各种点击交互动效。

---

## 🎯 概述

所有瓷砖现在都支持丰富的点击交互动效，无需跳转页面，纯视觉反馈。这些动效完全符合 Windows Phone Metro 设计规范。

---

## 📚 可用动效类型

| 动效类型 | 效果描述 | 适用场景 | 推荐度 |
|---------|---------|---------|--------|
| **PRESS_SCALE** | 按压缩小至 95%，透明度降低 | 所有瓷砖的默认交互 | ⭐⭐⭐⭐⭐ |
| **PRESS_FLASH** | 按压时透明度快速闪烁 | 强调性瓷砖、重要通知 | ⭐⭐⭐⭐ |
| **TAP_BOUNCE** | 点击后弹跳至 110%，然后回弹 | 游戏类、娱乐类瓷砖 | ⭐⭐⭐ |
| **TAP_PULSE** | 点击后快速脉冲（缩小-放大-恢复） | 刷新类、更新类瓷砖 | ⭐⭐⭐⭐ |
| **TAP_SHAKE** | 点击后左右快速抖动 | 互动类、游戏类瓷砖 | ⭐⭐⭐ |
| **TAP_FLIP_TRIGGER** | 点击触发瓷砖翻转 | 有双面内容的瓷砖 | ⭐⭐⭐⭐ |
| **NONE** | 无动效 | 纯展示类瓷砖 | ⭐ |

---

## 🚀 基础用法

### 方式 1：在基础瓷砖容器中使用

所有基础瓷砖容器（SquareTile, MediumWideTile, TallTile, LargeTile, FullWideTile）都支持点击动效。

```kotlin
@Composable
fun MyTile() {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTile(
        backgroundColor = Color(0xFF0078D7),
        cellWidth = cellWidth,
        cellHeight = cellHeight,
        onClick = {
            // 点击回调
            println("瓷砖被点击了！")
        },
        clickEffect = TileClickEffect.PRESS_SCALE,  // 动效类型
    ) {
        // 瓷砖内容
        Text("点我", color = Color.White, fontSize = 24.sp)
    }
}
```

### 方式 2：在高级组件中使用

高级组件（ClockTile, WeatherTile 等）会在未来版本中支持点击动效参数。

当前版本，如果需要自定义动效，使用自定义瓷砖：

```kotlin
@Composable
fun CustomWeatherTile(
    temperature: Int,
    onClick: () -> Unit = {}
) {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTile(
        backgroundColor = Color(0xFFFF8C00),
        cellWidth = cellWidth,
        cellHeight = cellHeight,
        onClick = onClick,
        clickEffect = TileClickEffect.TAP_PULSE  // 点击脉冲
    ) {
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text("☀", fontSize = 72.sp)
            Text("$temperature°", fontSize = 56.sp, fontWeight = FontWeight.Thin, color = Color.White)
        }
    }
}
```

---

## 🎨 动效详解

### 1. PRESS_SCALE - 经典按压效果 ⭐ 推荐

**效果**：
- 按下时：缩小至 95%，透明度降至 0.7
- 释放时：恢复至 100%，透明度恢复至 1.0
- 动画时长：150ms

**推荐理由**：
- Windows Phone 经典交互
- 提供明确的触觉反馈
- 性能优异
- 适用于所有场景

**使用示例**：

```kotlin
SquareTile(
    backgroundColor = Color(0xFF0078D7),
    cellWidth = cellWidth,
    cellHeight = cellHeight,
    onClick = { /* 处理点击 */ },
    clickEffect = TileClickEffect.PRESS_SCALE  // ✅ 默认值，可省略
) {
    // 内容
}
```

---

### 2. PRESS_FLASH - 按压闪烁

**效果**：
- 按下时：透明度快速降至 0.5
- 释放时：透明度快速恢复至 1.0
- 动画时长：100ms

**适用场景**：
- 重要通知瓷砖
- 警告信息瓷砖
- 需要强调的按钮

**使用示例**：

```kotlin
SquareTile(
    backgroundColor = Color(0xFFE51400),  // 红色
    cellWidth = cellWidth,
    cellHeight = cellHeight,
    onClick = { /* 清除通知 */ },
    clickEffect = TileClickEffect.PRESS_FLASH
) {
    Column {
        Text("⚠", fontSize = 64.sp)
        Text("3 条通知", fontSize = 20.sp, color = Color.White)
    }
}
```

---

### 3. TAP_BOUNCE - 点击弹跳

**效果**：
- 点击后：瓷砖先放大至 110%
- 然后：弹性回弹至原始大小
- 使用弹簧动画

**适用场景**：
- 游戏瓷砖
- 娱乐应用
- 互动元素

**使用示例**：

```kotlin
SquareTile(
    backgroundColor = Color(0xFF00A300),
    cellWidth = cellWidth,
    cellHeight = cellHeight,
    onClick = { /* 启动游戏 */ },
    clickEffect = TileClickEffect.TAP_BOUNCE
) {
    Column {
        Text("🎮", fontSize = 64.sp)
        Text("开始游戏", fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
    }
}
```

---

### 4. TAP_PULSE - 点击脉冲

**效果**：
- 点击后：缩小至 90%（150ms）
- 然后：放大至 105%（150ms）
- 最后：恢复至 100%（自动）

**适用场景**：
- 刷新按钮
- 更新类瓷砖
- 同步状态瓷砖

**使用示例**：

```kotlin
SquareTile(
    backgroundColor = Color(0xFFAA00FF),
    cellWidth = cellWidth,
    cellHeight = cellHeight,
    onClick = { viewModel.refresh() },
    clickEffect = TileClickEffect.TAP_PULSE
) {
    Column {
        Text("🔄", fontSize = 64.sp)
        Text("刷新", fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
    }
}
```

---

### 5. TAP_SHAKE - 点击抖动

**效果**：
- 点击后：左右快速抖动
- 抖动序列：+10, -10, +8, -8, +5, -5, 0（单位：像素）
- 每次抖动间隔：50ms

**适用场景**：
- 错误提示
- 互动反馈
- 趣味元素

**使用示例**：

```kotlin
SquareTile(
    backgroundColor = Color(0xFFE51400),
    cellWidth = cellWidth,
    cellHeight = cellHeight,
    onClick = { /* 密码错误 */ },
    clickEffect = TileClickEffect.TAP_SHAKE
) {
    Column {
        Text("🔒", fontSize = 64.sp)
        Text("密码错误", fontSize = 18.sp, fontWeight = FontWeight.Light, color = Color.White)
    }
}
```

---

### 6. TAP_FLIP_TRIGGER - 点击翻转 ⭐ 特殊

**效果**：
- 点击后：瓷砖 Y 轴 3D 翻转至背面
- 再次点击：翻转回正面
- 翻转角度：0° ↔ 180°
- 动画时长：600ms

**适用场景**：
- 有正反两面内容的瓷砖
- 详情展开/收起
- 信息切换

**使用示例**：

```kotlin
// 特殊用法：不使用 SquareTile，直接使用 TileTapFlipTrigger
TileTapFlipTrigger(
    frontContent = {
        Box(
            Modifier
                .width(tileWidth)
                .height(tileHeight)
                .background(Color(0xFF0078D7))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text("☀", fontSize = 72.sp)
                Text("22°", fontSize = 48.sp, fontWeight = FontWeight.Thin, color = Color.White)
            }
        }
    },
    backContent = {
        Box(
            Modifier
                .width(tileWidth)
                .height(tileHeight)
                .background(Color(0xFF00A300))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text("晴朗", fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color.White)
                Text("湿度: 45%", fontSize = 18.sp, fontWeight = FontWeight.ExtraLight, color = Color.White)
                Text("风速: 12km/h", fontSize = 18.sp, fontWeight = FontWeight.ExtraLight, color = Color.White)
            }
        }
    }
)
```

---

## 💡 最佳实践

### 1. 选择合适的动效

```kotlin
// ✅ 推荐：大多数场景使用 PRESS_SCALE
SquareTile(clickEffect = TileClickEffect.PRESS_SCALE)

// ✅ 推荐：刷新类使用 TAP_PULSE
RefreshTile(clickEffect = TileClickEffect.TAP_PULSE)

// ❌ 不推荐：普通展示瓷砖使用过于花哨的动效
ClockTile(clickEffect = TileClickEffect.TAP_SHAKE)  // 时钟不需要抖动
```

### 2. 保持动效一致性

同一类型的瓷砖应使用相同的动效：

```kotlin
// ✅ 推荐：同类瓷砖使用统一动效
WeatherTile(clickEffect = TileClickEffect.PRESS_SCALE)
CalendarTile(clickEffect = TileClickEffect.PRESS_SCALE)
ClockTile(clickEffect = TileClickEffect.PRESS_SCALE)

// ❌ 不推荐：随意混用
WeatherTile(clickEffect = TileClickEffect.TAP_BOUNCE)
CalendarTile(clickEffect = TileClickEffect.TAP_SHAKE)
ClockTile(clickEffect = TileClickEffect.PRESS_FLASH)
```

### 3. 处理点击事件

```kotlin
// ✅ 推荐：在 onClick 中处理业务逻辑
SquareTile(
    onClick = {
        // 更新数据
        viewModel.updateData()
        // 显示 Toast
        showToast("已更新")
        // 触发震动反馈
        vibrateOnce()
    },
    clickEffect = TileClickEffect.TAP_PULSE
) {
    // 内容
}
```

### 4. 避免过度使用动效

```kotlin
// ✅ 推荐：纯展示类瓷砖不需要点击功能
SquareTile(
    clickEffect = TileClickEffect.NONE  // 无动效
) {
    Text("纯展示内容")
}

// 或者直接不传 onClick 和 clickEffect（使用默认值）
SquareTile(
    backgroundColor = Color(0xFF0078D7),
    cellWidth = cellWidth,
    cellHeight = cellHeight
) {
    Text("纯展示内容")
}
```

---

## 🎬 演示页面

项目中包含了一个完整的交互动效演示页面：

```kotlin
// InteractionDemoScreen.kt
@Composable
fun InteractionDemoScreen() {
    // 展示所有 6 种动效
    // 每个瓷砖显示点击次数
    // 可以直接运行体验
}
```

### 运行演示

1. 在 `MainActivity.kt` 中临时替换为 `InteractionDemoScreen`：

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeskmateTheme {
                InteractionDemoScreen()  // 演示页面
            }
        }
    }
}
```

2. 运行应用，点击各个瓷砖体验不同动效

---

## 📊 性能考虑

### 动效性能对比

| 动效类型 | 性能消耗 | 流畅度 | 备注 |
|---------|---------|--------|------|
| PRESS_SCALE | 低 | 60fps | 硬件加速，推荐 |
| PRESS_FLASH | 低 | 60fps | 仅透明度变化 |
| TAP_BOUNCE | 中 | 60fps | 使用弹簧动画 |
| TAP_PULSE | 中 | 60fps | 多阶段动画 |
| TAP_SHAKE | 中 | 60fps | 位移动画 |
| TAP_FLIP_TRIGGER | 高 | 60fps | 3D 变换，注意使用频率 |

### 优化建议

1. **优先使用 PRESS_SCALE**：性能最优，体验最好
2. **避免同时触发多个动画**：一次只动画一个瓷砖
3. **减少 3D 变换**：TAP_FLIP_TRIGGER 性能消耗较大，不要过度使用
4. **测试真机性能**：在中低端设备上测试流畅度

---

## 🔧 高级用法

### 自定义动效参数

如果内置动效不满足需求，可以直接使用动画组件：

```kotlin
@Composable
fun CustomAnimationTile() {
    TilePressEffect(
        onTap = { /* 点击回调 */ },
        scaleDown = 0.90f,       // 自定义缩放比例（默认 0.95）
        alphaDown = 0.5f,        // 自定义透明度（默认 0.7）
        durationMillis = 200     // 自定义动画时长（默认 150ms）
    ) {
        // 瓷砖内容
    }
}
```

### 组合多种动效

```kotlin
// 按压 + 点击效果组合
TileCombinedEffect(
    onTap = { /* 点击回调 */ },
    clickEffect = TileClickEffect.TAP_PULSE
) {
    // 按压时有缩放反馈
    // 释放后触发脉冲动画
}
```

---

## 🎯 完整示例

### 天气瓷砖带点击刷新

```kotlin
@Composable
fun WeatherTileWithRefresh(
    viewModel: WeatherViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTile(
        backgroundColor = Color(0xFFFF8C00),
        cellWidth = cellWidth,
        cellHeight = cellHeight,
        onClick = {
            viewModel.refreshWeather()
        },
        clickEffect = TileClickEffect.TAP_PULSE  // 刷新用脉冲
    ) {
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.weatherIcon,
                fontSize = 72.sp,
                color = Color.White
            )
            Text(
                text = "${uiState.temperature}°",
                fontSize = 56.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White,
                lineHeight = 56.sp
            )
            if (uiState.isRefreshing) {
                Text(
                    text = "刷新中...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}
```

---

## 📝 总结

- ✅ **PRESS_SCALE** - 默认选择，适用 90% 场景
- ✅ **TAP_PULSE** - 刷新/更新类瓷砖的最佳选择
- ✅ **TAP_FLIP_TRIGGER** - 双面内容的理想方案
- ✅ 保持动效一致性
- ✅ 避免过度使用花哨动效
- ✅ 在真机上测试性能

**让每一次点击都有反馈，让瓷砖真正"活"起来！** 🎉
