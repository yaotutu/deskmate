# Deskmate 📱

一个基于 Kotlin + Jetpack Compose 的现代化 Android 桌面小部件应用，采用 Windows Phone 风格的动态瓷砖设计。

---

## ✨ 特性

- 🎨 **Windows Phone 风格** - 经典的 Metro 设计语言
- 🔄 **动态瓷砖** - 翻转、滑动、脉冲等流畅动画
- 🧩 **组件化设计** - 开箱即用的瓷砖组件库
- 📊 **数据驱动** - 只需关注数据，布局和动画自动处理
- 🎯 **MVVM 架构** - 清晰的代码组织和状态管理

---

## 📸 预览

### Windows Phone 风格动态瓷砖
- **时钟瓷砖**: 超大纤细字体 + 翻转动画
- **天气瓷砖**: 图标和温度 + 脉冲动画
- **日历瓷砖**: 巨大的日期数字
- **待办瓷砖**: 待办事项列表
- **新闻瓷砖**: 自动轮播新闻

---

## 🚀 快速开始

### 前置要求
- Android Studio Hedgehog | 2023.1.1+
- JDK 11+
- Android SDK 24+
- Kotlin 2.0.21+

### 克隆项目
```bash
git clone https://github.com/yourusername/deskmate.git
cd deskmate
```

### 构建运行
```bash
# 构建 Debug APK
./gradlew assembleDebug

# 安装到设备
adb install app/build/outputs/apk/debug/app-debug.apk

# 或者直接在 Android Studio 中运行
```

---

## 📚 文档

- [快速开始指南](./QUICK_START.md) - 5分钟上手
- [组件库文档](./TILE_COMPONENTS.md) - 瓷砖组件完整说明
- [架构设计](./ARCHITECTURE.md) - 项目架构详解
- [开发指南](./DEVELOPMENT.md) - 如何开发新功能

---

## 🏗 项目结构

```
deskmate/
├── app/src/main/java/top/yaotutu/deskmate/
│   ├── data/                      # 数据层
│   │   └── model/                 # 数据模型
│   ├── navigation/                # 导航配置
│   ├── presentation/              # 表现层
│   │   ├── ui/
│   │   │   ├── component/        # UI 组件
│   │   │   │   ├── TileAnimation.kt      # 动画组件
│   │   │   │   ├── TileCard.kt           # 基础瓷砖
│   │   │   │   ├── TileComponents.kt     # 高级组件库 ⭐
│   │   │   │   └── TileGrid.kt           # 网格系统
│   │   │   ├── screen/           # 页面
│   │   │   └── theme/            # 主题配置
│   │   └── viewmodel/            # ViewModel
│   └── MainActivity.kt
├── docs/                          # 文档
└── CLAUDE.md                      # 项目说明
```

---

## 🎯 核心技术栈

| 技术 | 版本 | 用途 |
|-----|------|-----|
| Kotlin | 2.0.21 | 主要开发语言 |
| Jetpack Compose | Latest | 声明式 UI 框架 |
| Material3 | Latest | UI 组件库 |
| Kotlin Coroutines | Latest | 异步编程 |
| Flow | Latest | 响应式数据流 |
| ViewModel | Latest | UI 状态管理 |
| Navigation Compose | Latest | 导航管理 |

---

## 🧩 组件化设计

### 使用前（底层 API）
```kotlin
// 需要 30+ 行代码，手动配置所有细节
MediumWideTile(
    backgroundColor = Color(0xFF0078D7),
    cellWidth = cellWidth,
    cellHeight = cellHeight
) {
    FlipTileAnimation(
        frontContent = { /* 复杂的布局代码 */ },
        backContent = { /* 复杂的布局代码 */ }
    )
}
```

### 使用后（高级 API）
```kotlin
// 只需 4 行代码，自动处理所有细节
ClockTile(
    time = "10:12",
    date = "星期一, 10月 27日",
    lunarDate = "农历八月廿一"
)
```

**代码量减少 90%！** 详见 [组件库文档](./TILE_COMPONENTS.md)

---

## 🎨 Metro 配色方案

| 颜色名称 | 色值 | 用途 |
|---------|------|------|
| Metro 蓝 | `#0078D7` | 时钟、主要操作 |
| Metro 橙 | `#FF8C00` | 天气、警告 |
| Metro 绿 | `#00A300` | 日历、成功 |
| Metro 亮紫 | `#AA00FF` | 待办、强调 |
| Metro 鲜红 | `#E51400` | 新闻、重要信息 |

---

## 📖 开发示例

### 创建一个新的瓷砖页面

```kotlin
@Composable
fun MyDashboard() {
    TileGridContainer(modifier = Modifier.fillMaxSize()) { cellWidth, cellHeight ->
        ProvideTileGrid(cellWidth, cellHeight) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ClockTile(time = "10:12", date = "...", lunarDate = "...")
                    WeatherTile(temperature = 22)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalendarTile(month = "十月", day = 27)
                    TodoTile(items = listOf("买菜"))
                    NewsTile(newsItems = listOf("头条" to "内容"))
                }
            }
        }
    }
}
```

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

### 贡献指南
1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

---

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](../LICENSE) 文件

---

## 🙏 致谢

- 设计灵感来自 Windows Phone 的 Metro 设计语言
- 使用 [Jetpack Compose](https://developer.android.com/jetpack/compose) 构建
- 图标来自 Unicode Emoji

---

## 📞 联系方式

- 项目主页: [GitHub](https://github.com/yourusername/deskmate)
- 问题反馈: [Issues](https://github.com/yourusername/deskmate/issues)
- 邮箱: your.email@example.com

---

**用数据驱动设计，让开发更简单！** 🚀
