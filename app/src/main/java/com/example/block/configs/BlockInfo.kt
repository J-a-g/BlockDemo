package com.example.block.configs

import androidx.annotation.Keep
import com.example.block.util.StacktraceUtil
import com.example.block.util.StacktraceUtil.stackTraceToStringArray
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Keep
class BlockInfo : Serializable {
    //卡顿开始时间
    var timeStart: Long = 0

    //卡顿结束时间
    var timeEnd: Long = 0

    //原始的堆栈信息
    @Transient
    var mThreadStackEntries: Map<Long, List<StackTraceElement>>? = null

    //线程的堆栈情况(去重并且转换为字符串显示)
    var threadStackEntriesForExport: Map<String, List<String>>? = null

    constructor(  timeStart: Long,
                  timeEnd: Long,
                  threadStackEntries: Map<Long, List<StackTraceElement>>?){
        this.timeStart = timeStart
        this.timeEnd = timeEnd
        mThreadStackEntries = threadStackEntries
        threadStackEntriesForExport = convertToStackString(threadStackEntries)
    }

    constructor(  timeStart: Long,
                  timeEnd: Long){
        this.timeStart = timeStart
        this.timeEnd = timeEnd
    }

    private val TIME_FORMATTER = SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US)

    /**
     * 原始的堆栈信息转换为字符串类型的堆栈信息
     *
     * @param ts
     * @return
     */
    private fun convertToStackString(ts: Map<Long, List<StackTraceElement>>?): Map<String, List<String>>? {
        if (ts == null || ts.isEmpty()) {
            return LinkedHashMap()
        }
        // 筛选之后的堆栈
        val filterMap: MutableMap<Long, List<StackTraceElement>> = LinkedHashMap()
        for (key in ts.keys) {
            val value = ts[key]!!
            if (!filterMap.containsValue(value)) {
                filterMap[key] = value
            }
        }
        // 转换为字符串
        val result: MutableMap<String, List<String>> = LinkedHashMap()
        for ((key, value) in filterMap) {
            result[TIME_FORMATTER.format(key)] = stackTraceToStringArray(value)
        }
        return result
    }

    override fun toString(): String {
        return "LongBlockInfo{" +
                "timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", threadStackEntriesForExport=" + threadStackEntriesForExport +
                ", mThreadStackEntries=" + mThreadStackEntries +
                '}'
    }
}