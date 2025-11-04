package top.yaotutu.deskmate.util

import java.util.*

/**
 * 农历（阴历）+ 黄历工具类
 *
 * 支持公历转农历，提供：
 * - 农历年月日
 * - 天干地支
 * - 生肖
 * - 二十四节气
 * - 传统节日（农历 + 公历）
 * - 黄历宜忌
 * - 星座
 *
 * 支持年份范围：1900-2100
 */
object LunarCalendar {

    // 农历数据：1900-2100年每年的农历信息（编码）
    // 每个值表示一年的农历数据：闰月信息 + 每月天数
    private val lunarInfo = longArrayOf(
        0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
        0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
        0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
        0x14b63, 0x09370, 0x049f8, 0x04970, 0x064b0, 0x168a6, 0x0ea50, 0x06b20, 0x1a6c4, 0x0aae0,
        0x0a2e0, 0x0d2e3, 0x0c960, 0x0d557, 0x0d4a0, 0x0da50, 0x05d55, 0x056a0, 0x0a6d0, 0x055d4,
        0x052d0, 0x0a9b8, 0x0a950, 0x0b4a0, 0x0b6a6, 0x0ad50, 0x055a0, 0x0aba4, 0x0a5b0, 0x052b0,
        0x0b273, 0x06930, 0x07337, 0x06aa0, 0x0ad50, 0x14b55, 0x04b60, 0x0a570, 0x054e4, 0x0d160,
        0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0, 0x0d150, 0x0f252,
        0x0d520
    )

    // 天干
    private val gan = arrayOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")

    // 地支
    private val zhi = arrayOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")

    // 生肖
    private val animals = arrayOf("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪")

    // 农历月份
    private val monthNames = arrayOf(
        "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
    )

    // 农历日期
    private val dayNames = arrayOf(
        "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
        "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
        "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
    )

    /**
     * 农历 + 黄历数据类
     */
    data class LunarDate(
        val year: Int,          // 农历年
        val month: Int,         // 农历月
        val day: Int,           // 农历日
        val isLeapMonth: Boolean, // 是否闰月
        val yearGanZhi: String, // 年干支（如：甲子）
        val monthGanZhi: String,// 月干支
        val dayGanZhi: String,  // 日干支
        val animal: String,     // 生肖
        val monthName: String,  // 月份名称（如：正月、腊月）
        val dayName: String,    // 日期名称（如：初一、十五）
        val solarTerm: String?, // 节气（如果当天是节气）
        val festival: String?,  // 节日（农历或公历）
        val constellation: String, // 星座
        val dayLucky: String,   // 今日宜
        val dayAvoid: String    // 今日忌
    ) {
        /**
         * 格式化为完整字符串（如：农历甲子年正月初一）
         */
        fun toFullString(): String {
            val leapPrefix = if (isLeapMonth) "闰" else ""
            return "农历${yearGanZhi}年${leapPrefix}${monthName}月${dayName}"
        }

        /**
         * 格式化为简短字符串（如：正月初一）
         */
        fun toShortString(): String {
            val leapPrefix = if (isLeapMonth) "闰" else ""
            return "${leapPrefix}${monthName}月${dayName}"
        }

        /**
         * 获取特殊提示（节气或节日）
         */
        fun getSpecialInfo(): String? {
            return solarTerm ?: festival
        }
    }

    /**
     * 公历转农历
     *
     * @param calendar Calendar 对象
     * @return 农历数据
     */
    fun solarToLunar(calendar: Calendar): LunarDate {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return solarToLunar(year, month, day)
    }

    /**
     * 公历转农历
     *
     * @param year 公历年
     * @param month 公历月（1-12）
     * @param day 公历日
     * @return 农历数据
     */
    fun solarToLunar(year: Int, month: Int, day: Int): LunarDate {
        // 基准日期：1900年1月31日（农历1900年正月初一）
        val baseDate = Calendar.getInstance().apply {
            set(1900, 0, 31, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val targetDate = Calendar.getInstance().apply {
            set(year, month - 1, day, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // 计算相差天数
        val offsetDays = ((targetDate.timeInMillis - baseDate.timeInMillis) / 86400000L).toInt()

        // 计算农历日期
        var lunarYear = 1900
        var lunarMonth = 1
        var lunarDay = 1
        var isLeapMonth = false
        var daysInYear: Int
        var remainDays = offsetDays

        // 逐年减去天数
        while (remainDays > 0) {
            daysInYear = yearDays(lunarYear)
            if (remainDays < daysInYear) break
            remainDays -= daysInYear
            lunarYear++
        }

        // 逐月减去天数
        val leapMonth = leapMonthOfYear(lunarYear)
        var monthIndex = 1

        while (monthIndex <= 12) {
            // 闰月
            if (leapMonth > 0 && monthIndex == leapMonth + 1 && !isLeapMonth) {
                val daysInLeapMonth = leapDays(lunarYear)
                if (remainDays < daysInLeapMonth) {
                    isLeapMonth = true
                    lunarMonth = leapMonth
                    lunarDay = remainDays + 1
                    break
                }
                remainDays -= daysInLeapMonth
                // 继续处理闰月之后的正常月份
            }

            val daysInMonth = monthDays(lunarYear, monthIndex)
            if (remainDays < daysInMonth) {
                lunarMonth = monthIndex
                lunarDay = remainDays + 1
                break
            }
            remainDays -= daysInMonth
            monthIndex++
        }

        // 计算干支
        val yearGanZhi = getYearGanZhi(lunarYear)
        val monthGanZhi = getMonthGanZhi(lunarYear, lunarMonth)
        val dayGanZhi = getDayGanZhi(year, month, day)

        // 生肖
        val animal = animals[(lunarYear - 1900) % 12]

        // 月份和日期名称
        val monthName = if (lunarMonth <= 12) monthNames[lunarMonth - 1] else "?"
        val dayName = if (lunarDay <= 30) dayNames[lunarDay - 1] else "?"

        // 计算节气
        val solarTerm = LunarCalendarData.getSolarTerm(year, month, day)

        // 获取节日（优先农历节日）
        val lunarFestival = LunarCalendarData.getLunarFestival(lunarMonth, lunarDay, isLeapMonth)
        val solarFestival = LunarCalendarData.getSolarFestival(month, day)
        val festival = lunarFestival ?: solarFestival

        // 计算星座
        val constellation = LunarCalendarData.getConstellation(month, day)

        // 计算宜忌（基于日干支）
        val ganIndex = (year - 4) % 10
        val zhiIndex = (calculateDayOffset(year, month, day) + 1) % 12
        val dayLucky = LunarCalendarData.getDayLucky(dayGanZhi, ganIndex, zhiIndex)
        val dayAvoid = LunarCalendarData.getDayAvoid(dayGanZhi, ganIndex, zhiIndex)

        return LunarDate(
            year = lunarYear,
            month = lunarMonth,
            day = lunarDay,
            isLeapMonth = isLeapMonth,
            yearGanZhi = yearGanZhi,
            monthGanZhi = monthGanZhi,
            dayGanZhi = dayGanZhi,
            animal = animal,
            monthName = monthName,
            dayName = dayName,
            solarTerm = solarTerm,
            festival = festival,
            constellation = constellation,
            dayLucky = dayLucky,
            dayAvoid = dayAvoid
        )
    }

    /**
     * 计算日期偏移（用于干支计算）
     */
    private fun calculateDayOffset(year: Int, month: Int, day: Int): Int {
        val baseDate = Calendar.getInstance().apply {
            set(1900, 0, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val targetDate = Calendar.getInstance().apply {
            set(year, month - 1, day, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return ((targetDate.timeInMillis - baseDate.timeInMillis) / 86400000L).toInt()
    }

    /**
     * 获取某年的总天数
     */
    private fun yearDays(year: Int): Int {
        var sum = 348
        var mask = 0x8000
        for (i in 0..11) {  // 12个月
            if ((lunarInfo[year - 1900].toInt() and mask) != 0) sum++
            mask = mask shr 1
        }
        return sum + leapDays(year)
    }

    /**
     * 获取某年闰月的天数
     */
    private fun leapDays(year: Int): Int {
        if (leapMonthOfYear(year) != 0) {
            return if ((lunarInfo[year - 1900].toInt() and 0x10000) != 0) 30 else 29
        }
        return 0
    }

    /**
     * 获取某年的闰月月份（0表示无闰月）
     */
    private fun leapMonthOfYear(year: Int): Int {
        return (lunarInfo[year - 1900].toInt() and 0xf)
    }

    /**
     * 获取某年某月的天数
     */
    private fun monthDays(year: Int, month: Int): Int {
        return if ((lunarInfo[year - 1900].toInt() and (0x10000 shr month)) != 0) 30 else 29
    }

    /**
     * 获取年份的天干地支
     */
    private fun getYearGanZhi(year: Int): String {
        val ganIndex = (year - 4) % 10
        val zhiIndex = (year - 4) % 12
        return gan[ganIndex] + zhi[zhiIndex]
    }

    /**
     * 获取月份的天干地支（简化版）
     */
    private fun getMonthGanZhi(year: Int, month: Int): String {
        val ganIndex = ((year - 1900) * 12 + month - 1) % 10
        val zhiIndex = (month + 1) % 12
        return gan[ganIndex] + zhi[zhiIndex]
    }

    /**
     * 获取日期的天干地支
     */
    private fun getDayGanZhi(year: Int, month: Int, day: Int): String {
        // 以1900年1月1日（甲子日）为基准
        val baseDate = Calendar.getInstance().apply {
            set(1900, 0, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val targetDate = Calendar.getInstance().apply {
            set(year, month - 1, day, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val offsetDays = ((targetDate.timeInMillis - baseDate.timeInMillis) / 86400000L).toInt()
        val ganIndex = (offsetDays + 9) % 10
        val zhiIndex = (offsetDays + 1) % 12

        return gan[ganIndex] + zhi[zhiIndex]
    }
}
