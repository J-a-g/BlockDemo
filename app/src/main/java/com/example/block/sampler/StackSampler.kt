package com.example.block.sampler

import android.os.Looper
import android.util.Log
import java.util.*

class StackSampler: AbstractSampler {

    private val DEFAULT_MAX_ENTRY_COUNT = 30
    private val sStackMap = LinkedHashMap<Long, Array<StackTraceElement>>()

    private var mMaxEntryCount = 0


    constructor(sampleDelay: Long): super(sampleDelay){
        this.mMaxEntryCount = DEFAULT_MAX_ENTRY_COUNT

    }
    constructor(maxEntryCount: Int, sampleDelay: Long): super(sampleDelay){
        this.mMaxEntryCount = maxEntryCount
    }

    /**
     * 获取这个时间段内dump的堆栈信息
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun getThreadStackEntries(
        startTime: Long,
        endTime: Long): MutableMap<Long, List<StackTraceElement>> {
        val result: MutableMap<Long, List<StackTraceElement>> = LinkedHashMap()
        synchronized(sStackMap) {
            for (entryTime in sStackMap.keys) {
                if (startTime < entryTime && entryTime < endTime) {
                    result.put(entryTime, sStackMap.get(entryTime)?.toList() as List<StackTraceElement>)
                }
            }
        }
        return result
    }

    override fun doSample() {
        synchronized(sStackMap) {
            if (sStackMap.size == mMaxEntryCount && mMaxEntryCount > 0) {
                sStackMap.remove(sStackMap.keys.iterator().next())
            }
            sStackMap.put(
                System.currentTimeMillis(),
                Looper.getMainLooper().thread.stackTrace
            )
        }
    }
}