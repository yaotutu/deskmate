# Metro 设计增强方案

本文档列出了让 Deskmate 瓷砖更加还原 Windows Phone Metro 设计的增强功能。

---

## 🎯 第一阶段：核心交互增强（优先级：高）

### 1. 点击反馈动画 ⭐ 最重要

**Windows Phone 特色**：点击瓷砖时有轻微的缩放和透明度变化。

#### 实现效果
- 按下时：缩小至 95%，透明度 0.7
- 释放时：恢复至 100%，透明度 1.0
- 动画时长：150ms

#### 代码示例

```kotlin
// TileAnimation.kt - 新增点击反馈动画
@Composable
fun TilePressAnimation(
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150, easing = FastOutSlowInEasing)
    )

    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = tween(150)
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() }
                )
            }
    ) {
        content()
    }
}
```

#### 使用方式

```kotlin
// 在 TileCard.kt 的所有瓷砖容器中包裹
@Composable
fun SquareTile(
    backgroundColor: Color,
    cellWidth: Dp,
    cellHeight: Dp,
    onClick: () -> Unit = {},  // 新增 onClick 参数
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TilePressAnimation(onClick = onClick) {
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
}
```

---

### 2. Peek 动画

**Windows Phone 特色**：内容快速"偷看"预览，然后恢复。

#### 实现效果
- 每隔 10-15 秒
- 内容快速向上滑动露出底部内容
- 持续 1 秒后恢复

#### 代码示例

```kotlin
// TileAnimation.kt - 新增 Peek 动画
@Composable
fun PeekTileAnimation(
    frontContent: @Composable () -> Unit,
    peekContent: @Composable () -> Unit,
    peekIntervalMillis: Long = 12000,
    peekDurationMillis: Int = 1000,
    modifier: Modifier = Modifier
) {
    var isPeeking by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(peekIntervalMillis)
            isPeeking = true
            delay(peekDurationMillis.toLong())
            isPeeking = false
        }
    }

    val offsetY by animateFloatAsState(
        targetValue = if (isPeeking) -100f else 0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    Box(modifier = modifier) {
        // 底部内容（Peek 内容）
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 100.dp)
        ) {
            peekContent()
        }

        // 主要内容
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = offsetY.dp)
        ) {
            frontContent()
        }
    }
}
```

#### 使用示例

```kotlin
// WeatherTile 增强版
@Composable
fun EnhancedWeatherTile(
    temperature: Int,
    icon: String = "☀",
    weatherDescription: String = "晴朗",  // Peek 内容
    backgroundColor: Color = Color(0xFFFF8C00),
    modifier: Modifier = Modifier
) {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTile(backgroundColor, cellWidth, cellHeight, modifier) {
        PeekTileAnimation(
            frontContent = {
                // 主要内容：图标和温度
                Column(Alignment.CenterHorizontally) {
                    Text(icon, fontSize = 72.sp)
                    Text("$temperature°", fontSize = 56.sp, fontWeight = FontWeight.Thin, color = Color.White)
                }
            },
            peekContent = {
                // Peek 内容：天气描述
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        weatherDescription,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }
        )
    }
}
```

---

### 3. 徽章/角标（Badge）

**Windows Phone 特色**：在瓷砖右上角显示未读数。

#### 实现效果
- 圆形或方形角标
- 显示数字或图标
- 位于右上角

#### 代码示例

```kotlin
// TileCard.kt - 新增 Badge 组件
@Composable
fun TileBadge(
    count: Int,
    backgroundColor: Color = Color.Red,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    if (count > 0) {
        Box(
            modifier = modifier
                .size(24.dp)
                .background(backgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (count > 99) "99+" else count.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

// 带 Badge 的瓷砖容器
@Composable
fun SquareTileWithBadge(
    backgroundColor: Color,
    cellWidth: Dp,
    cellHeight: Dp,
    badgeCount: Int = 0,  // 新增参数
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box {
        SquareTile(backgroundColor, cellWidth, cellHeight, modifier, content)

        // 右上角 Badge
        if (badgeCount > 0) {
            TileBadge(
                count = badgeCount,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
            )
        }
    }
}
```

#### 使用示例

```kotlin
// 消息瓷砖带未读数
@Composable
fun MessageTile(
    unreadCount: Int,
    backgroundColor: Color = Color(0xFFAA00FF),
    modifier: Modifier = Modifier
) {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    SquareTileWithBadge(
        backgroundColor = backgroundColor,
        cellWidth = cellWidth,
        cellHeight = cellHeight,
        badgeCount = unreadCount,
        modifier = modifier
    ) {
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text("📧", fontSize = 64.sp)
            Text("消息", fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
        }
    }
}
```

---

### 4. 图片背景支持

**Windows Phone 特色**：使用图片作为瓷砖背景。

#### 实现效果
- 支持图片 URL 或本地资源
- 自动添加暗色遮罩保证文字可读性
- 图片缩放模式：裁剪居中

#### 代码示例

```kotlin
// TileCard.kt - 图片背景瓷砖
@Composable
fun ImageBackgroundTile(
    imageUrl: String? = null,
    @DrawableRes imageRes: Int? = null,
    overlayAlpha: Float = 0.4f,  // 遮罩透明度
    cellWidth: Dp,
    cellHeight: Dp,
    tileColumns: Int,
    tileRows: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val tileWidth = TileGrid.calculateTileWidth(cellWidth, tileColumns)
    val tileHeight = TileGrid.calculateTileHeight(cellHeight, tileRows)

    Box(
        modifier = modifier
            .width(tileWidth)
            .height(tileHeight)
            .clip(RoundedCornerShape(2.dp))
    ) {
        // 背景图片
        if (imageUrl != null || imageRes != null) {
            AsyncImage(  // 需要 Coil 库
                model = imageUrl ?: imageRes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 暗色遮罩
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = overlayAlpha))
            )
        }

        // 内容
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            content()
        }
    }
}
```

#### 使用示例

```kotlin
// 照片瓷砖
@Composable
fun PhotoTile(
    photoUrl: String,
    caption: String,
    backgroundColor: Color = Color(0xFF0078D7),
    modifier: Modifier = Modifier
) {
    val cellWidth = LocalCellWidth.current
    val cellHeight = LocalCellHeight.current

    ImageBackgroundTile(
        imageUrl = photoUrl,
        overlayAlpha = 0.3f,
        cellWidth = cellWidth,
        cellHeight = cellHeight,
        tileColumns = 4,
        tileRows = 4,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                caption,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }
    }
}
```

---

## 🎨 第二阶段：动画增强（优先级：中）

### 5. Rotate 旋转动画

```kotlin
@Composable
fun RotateTileAnimation(
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    rotateIntervalMillis: Long = 5000,
    modifier: Modifier = Modifier
) {
    var isRotated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(rotateIntervalMillis)
            isRotated = !isRotated
        }
    }

    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(800, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = modifier.graphicsLayer {
            rotationY = rotation
            cameraDistance = 12f * density
        }
    ) {
        if (rotation < 90f) {
            frontContent()
        } else {
            Box(Modifier.graphicsLayer { rotationY = 180f }) {
                backContent()
            }
        }
    }
}
```

### 6. Marquee 文字滚动

```kotlin
@Composable
fun MarqueeText(
    text: String,
    fontSize: TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.Light,
    color: Color = Color.White,
    modifier: Modifier = Modifier
) {
    var shouldAnimate by remember { mutableStateOf(false) }

    BasicMarqueeText(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        // 当文字过长时启用滚动
        iterations = Int.MAX_VALUE,
        delayMillis = 2000,
        velocity = 30.dp
    )
}
```

---

## 📊 第三阶段：实时更新（优先级：中）

### 7. 实时内容更新

```kotlin
// ViewModel 中实现定时更新
class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        // 每分钟更新时间
        viewModelScope.launch {
            while (true) {
                updateTime()
                delay(60_000)
            }
        }

        // 每 30 秒更新天气
        viewModelScope.launch {
            while (true) {
                updateWeather()
                delay(30_000)
            }
        }
    }
}
```

### 8. 动态颜色变化

```kotlin
@Composable
fun DynamicColorTile(
    content: @Composable (Color) -> Unit
) {
    val colors = listOf(
        Color(0xFF0078D7),
        Color(0xFFFF8C00),
        Color(0xFF00A300),
        Color(0xFFAA00FF)
    )

    var colorIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(10000)
            colorIndex = (colorIndex + 1) % colors.size
        }
    }

    val currentColor by animateColorAsState(
        targetValue = colors[colorIndex],
        animationSpec = tween(1000)
    )

    content(currentColor)
}
```

---

## 🎯 实施优先级建议

### 立即实现（最大影响）
1. ✅ **点击反馈动画** - Metro 核心交互
2. ✅ **徽章/角标** - 实用且视觉效果好
3. ✅ **Peek 动画** - 增加动态感

### 短期实现（1-2 周）
4. ✅ **图片背景支持** - 视觉效果提升明显
5. ✅ **Rotate 动画** - 丰富动画类型
6. ✅ **Marquee 滚动** - 解决文字过长问题

### 长期优化
7. ✅ **实时更新** - 真正的 Live Tile
8. ✅ **动态颜色** - 提升视觉趣味性
9. ✅ **视差效果** - 如果有滚动场景

---

## 📝 实施建议

### 代码组织
```
presentation/ui/component/
├── TileAnimation.kt          # 所有动画效果
│   ├── TilePressAnimation    # 点击反馈 ⭐ 新增
│   ├── FlipTileAnimation     # 翻转（已有）
│   ├── PulseTileAnimation    # 脉冲（已有）
│   ├── SlideTileAnimation    # 滑动（已有）
│   ├── PeekTileAnimation     # Peek ⭐ 新增
│   └── RotateTileAnimation   # 旋转 ⭐ 新增
├── TileCard.kt               # 基础容器
│   ├── TileBadge             # 角标组件 ⭐ 新增
│   ├── ImageBackgroundTile   # 图片背景 ⭐ 新增
│   └── (其他已有瓷砖)
├── TileComponents.kt         # 高级组件
│   ├── (现有组件)
│   └── (增强版组件) ⭐ 新增
└── TileGrid.kt               # 网格系统
```

### 依赖添加

如果要支持图片背景，需要启用 Coil：

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(libs.coil.compose)  // 取消注释
}
```

---

## 🎨 设计细节参考

### Metro 动画时长标准
- **快速反馈**: 150ms（点击）
- **内容切换**: 300-500ms（翻转、滑动）
- **持续动画**: 800-1000ms（旋转）
- **呼吸效果**: 2000ms+（脉冲）

### Metro 缓动函数
- **进入**: `FastOutSlowInEasing`
- **退出**: `FastOutLinearInEasing`
- **持续**: `LinearEasing`

### Metro 交互原则
1. **即时反馈** - 点击必须有视觉反馈
2. **清晰动画** - 动画目的明确，不花哨
3. **内容优先** - 动画服务于内容展示
4. **性能优先** - 保持 60fps 流畅度

---

## 🚀 实施计划

### 第 1 天：点击反馈
- 实现 `TilePressAnimation`
- 更新所有 TileCard 组件支持 onClick
- 测试性能和视觉效果

### 第 2 天：Badge 和 Peek
- 实现 `TileBadge` 组件
- 实现 `PeekTileAnimation`
- 创建增强版瓷砖组件

### 第 3-4 天：图片背景
- 启用 Coil 依赖
- 实现 `ImageBackgroundTile`
- 创建照片瓷砖示例

### 第 5 天：测试和优化
- 性能测试
- 动画流畅度优化
- 文档更新

---

**让瓷砖真正"活"起来！** 🎉
