package com.example.block.core

import android.util.Printer
import com.example.block.configs.BlockConfig
import com.example.block.interfaces.OnBlockEventListener

class BlockPrinter : Printer {

    private var onBlockEventListener: OnBlockEventListener? = null
    private var blockConfig: BlockConfig


    // 一次事件开始时间
    private var mThisEventStartTime: Long = 0

    // 事件开始标记
    private var mEventStart = false

    constructor(blockConfig: BlockConfig, onBlockEventListener: OnBlockEventListener) {
        this.onBlockEventListener = onBlockEventListener
        this.blockConfig = blockConfig
    }

    override fun println(p0: String?) {
        if (!mEventStart) {// 事件开始
            mThisEventStartTime = System.currentTimeMillis()
            mEventStart = true
            onBlockEventListener?.onEventStart()
        } else {// 事件结束
            val thisEventEndTime = System.currentTimeMillis()
            mEventStart = false

            val eventCostTime = thisEventEndTime - mThisEventStartTime
            if (eventCostTime >= blockConfig.blockThresholdMillis) {
                onBlockEventListener?.onEventBlock(mThisEventStartTime, thisEventEndTime)
            }
            onBlockEventListener?.onEventEnd()
        }
    }
}