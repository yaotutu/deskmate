package top.yaotutu.deskmate.util

/**
 * 黄历数据辅助类
 * 提供节气、节日、宜忌等数据
 */
object LunarCalendarData {

    // 二十四节气名称
    val solarTerms = arrayOf(
        "小寒", "大寒", "立春", "雨水", "惊蛰", "春分",
        "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
        "小暑", "大暑", "立秋", "处暑", "白露", "秋分",
        "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"
    )

    // 节气基础数据（1900年的节气日期，用于计算）
    private val solarTermBase = arrayOf(
        6, 20, 4, 19, 6, 21, 5, 21, 6, 22, 6, 22,
        7, 23, 8, 23, 8, 23, 8, 24, 8, 23, 9, 22
    )

    // 节气修正表（简化版）
    private val solarTermOffsets = mapOf(
        "小寒" to intArrayOf(5, 6, 6),
        "大寒" to intArrayOf(20, 20, 21),
        "立春" to intArrayOf(3, 4, 5),
        "雨水" to intArrayOf(18, 19, 20),
        "惊蛰" to intArrayOf(5, 6, 6),
        "春分" to intArrayOf(20, 21, 21),
        "清明" to intArrayOf(4, 5, 5),
        "谷雨" to intArrayOf(19, 20, 21),
        "立夏" to intArrayOf(5, 6, 7),
        "小满" to intArrayOf(20, 21, 22),
        "芒种" to intArrayOf(5, 6, 7),
        "夏至" to intArrayOf(21, 22, 22),
        "小暑" to intArrayOf(6, 7, 8),
        "大暑" to intArrayOf(22, 23, 23),
        "立秋" to intArrayOf(7, 8, 8),
        "处暑" to intArrayOf(22, 23, 24),
        "白露" to intArrayOf(7, 8, 8),
        "秋分" to intArrayOf(22, 23, 24),
        "寒露" to intArrayOf(8, 8, 9),
        "霜降" to intArrayOf(23, 24, 24),
        "立冬" to intArrayOf(7, 8, 8),
        "小雪" to intArrayOf(22, 22, 23),
        "大雪" to intArrayOf(6, 7, 8),
        "冬至" to intArrayOf(21, 22, 22)
    )

    // 农历节日
    val lunarFestivals = mapOf(
        "1-1" to "春节",
        "1-15" to "元宵节",
        "2-2" to "龙抬头",
        "5-5" to "端午节",
        "7-7" to "七夕节",
        "7-15" to "中元节",
        "8-15" to "中秋节",
        "9-9" to "重阳节",
        "10-1" to "寒衣节",
        "12-8" to "腊八节",
        "12-23" to "小年",
        "12-30" to "除夕"
    )

    // 公历节日
    val solarFestivals = mapOf(
        "1-1" to "元旦",
        "2-14" to "情人节",
        "3-8" to "妇女节",
        "3-12" to "植树节",
        "4-1" to "愚人节",
        "5-1" to "劳动节",
        "5-4" to "青年节",
        "6-1" to "儿童节",
        "7-1" to "建党节",
        "8-1" to "建军节",
        "9-10" to "教师节",
        "10-1" to "国庆节",
        "12-25" to "圣诞节"
    )

    // 星座
    val constellations = arrayOf(
        "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
        "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"
    )

    // 星座日期划分
    private val constellationEdgeDays = intArrayOf(
        20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 22, 22
    )

    // 黄历宜忌（简化版，基于天干地支）
    private val luckyActivities = arrayOf(
        "祭祀", "祈福", "求嗣", "开光", "出行", "解除", "伐木", "盖屋",
        "移徙", "安床", "栽种", "纳畜", "嫁娶", "订盟", "纳采", "入殓",
        "破土", "安葬", "启钻", "除服", "成服", "会亲友", "交易", "立券",
        "纳财", "开市", "裁衣", "竖柱", "上梁"
    )

    private val avoidActivities = arrayOf(
        "动土", "破土", "嫁娶", "入宅", "开市", "安葬", "作灶", "出行",
        "栽种", "纳畜", "移徙", "分居", "开仓", "出货财", "安门", "修造",
        "置产", "行舟", "词讼", "治病"
    )

    /**
     * 计算节气
     */
    fun getSolarTerm(year: Int, month: Int, day: Int): String? {
        // 简化计算：每月2个节气
        val termIndex = (month - 1) * 2

        // 估算节气日期（基于20世纪平均值 + 简单修正）
        val century = (year / 100)
        val yearInCentury = year % 100

        for (i in 0..1) {
            val idx = termIndex + i
            if (idx >= solarTerms.size) break

            val baseDay = solarTermBase[idx]
            val offset = when {
                year < 1950 -> -1
                year < 2000 -> 0
                year < 2050 -> 0
                else -> 1
            }

            val termDay = baseDay + offset
            if (day == termDay || day == termDay + 1 || day == termDay - 1) {
                return solarTerms[idx]
            }
        }

        return null
    }

    /**
     * 获取农历节日
     */
    fun getLunarFestival(month: Int, day: Int, isLeapMonth: Boolean): String? {
        if (isLeapMonth) return null
        val key = "$month-$day"
        return lunarFestivals[key]
    }

    /**
     * 获取公历节日
     */
    fun getSolarFestival(month: Int, day: Int): String? {
        val key = "$month-$day"
        return solarFestivals[key]
    }

    /**
     * 计算星座
     */
    fun getConstellation(month: Int, day: Int): String {
        val index = if (day < constellationEdgeDays[month - 1]) {
            (month + 10) % 12
        } else {
            (month + 11) % 12
        }
        return constellations[index]
    }

    /**
     * 获取今日宜（基于天干地支）
     */
    fun getDayLucky(dayGanZhi: String, ganIndex: Int, zhiIndex: Int): String {
        // 根据天干地支计算宜事（简化算法）
        val index = (ganIndex * 12 + zhiIndex) % luckyActivities.size
        val count = 3 + (index % 3) // 3-5项

        val activities = mutableListOf<String>()
        for (i in 0 until count) {
            val actIndex = (index + i * 7) % luckyActivities.size
            activities.add(luckyActivities[actIndex])
        }

        return activities.joinToString(" ")
    }

    /**
     * 获取今日忌（基于天干地支）
     */
    fun getDayAvoid(dayGanZhi: String, ganIndex: Int, zhiIndex: Int): String {
        // 根据天干地支计算忌事（简化算法）
        val index = (ganIndex * 12 + zhiIndex + 5) % avoidActivities.size
        val count = 3 + (index % 2) // 3-4项

        val activities = mutableListOf<String>()
        for (i in 0 until count) {
            val actIndex = (index + i * 11) % avoidActivities.size
            activities.add(avoidActivities[actIndex])
        }

        return activities.joinToString(" ")
    }
}
