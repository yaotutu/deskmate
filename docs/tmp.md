1. 文件结构

presentation/ui/component/base/presets/
├── SmallTilePresets.kt      (1×1) - 小方形预设
├── CompactTilePresets.kt    (2×1) - 紧凑横向预设
├── MediumTilePresets.kt     (2×2) - 标准方形预设
├── WideTilePresets.kt       (4×2) - 宽矩形预设
├── TallTilePresets.kt       (2×4) - 高矩形预设
└── LargeTilePresets.kt      (4×4) - 大方形预设

2. 预设内容规划

SmallTilePresets.kt (1×1)

提供 5 种预设：
1. SingleLabel - 单行大字（时间、数字）
2. IconOnly - 纯图标（应用图标）
3. IconWithBadge - 图标+角标（通知数量）
4. MiniCounter - 小型计数器（步数）
5. StatusIndicator - 状态指示器（在线状态）

CompactTilePresets.kt (2×1)

提供 5 种预设：
1. TimeDateCompact - 时间+日期紧凑排列
2. IconLabel - 图标+标签横向排列
3. ProgressBar - 进度条显示
4. DualValue - 两个数值并排
5. StatusText - 状态文本横向显示

MediumTilePresets.kt (2×2)

提供 8 种预设：
1. TitleSubtitle - 标题+副标题（最常用）
2. IconTitle - 图标+标题
3. IconTitleSubtitle - 图标+标题+副标题
4. Counter - 大数字+单位+标签
5. TwoRowList - 两行列表项
6. IconGrid2x2 - 2×2 图标网格
7. HeaderBody - 头部+主体内容
8. ImageOverlay - 图片+覆盖文字

WideTilePresets.kt (4×2)

提供 6 种预设：
1. HorizontalTitleSubtitle - 标题副标题横向排列
2. IconTextSide - 图标和文字左右分布
3. ThreeColumns - 三列信息展示
4. Timeline - 时间线横向展示
5. MetricsDashboard - 多指标仪表盘
6. MediaPlayer - 媒体播放器布局

TallTilePresets.kt (2×4)

提供 6 种预设：
1. VerticalList - 纵向列表（3-5项）
2. Timeline - 时间线纵向展示
3. StepProgress - 步骤进度
4. DetailedCard - 详细卡片（多行信息）
5. ChatPreview - 聊天预览
6. WeatherForecast - 天气预报纵向

LargeTilePresets.kt (4×4)

提供 8 种预设：
1. Dashboard - 仪表盘（多指标）
2. RichCard - 丰富卡片（所有信息）
3. Calendar - 日历视图
4. PhotoGrid - 照片网格（4×4）
5. NewsList - 新闻列表（多项）
6. DetailedInfo - 详细信息展示
7. ChartDisplay - 图表展示
8. FeatureShowcase - 功能展示