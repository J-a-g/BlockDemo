package com.example.block.core

import android.view.Choreographer
import com.example.block.configs.BlockConfig
import com.example.block.interfaces.OnBlockEventListener

class ChoreographerMonitor : Choreographer.FrameCallback {
    private var mChoreographer: Choreographer? = null

    // 一次事件开始时间
    private var mThisEventStartTime: Long = 0
    // 事件开始标记
    private var mEventStart = false
    private var blockConfig: BlockConfig
    private var onBlockEventListener: OnBlockEventListener? = null

    constructor(blockConfig: BlockConfig, onBlockEventListener: OnBlockEventListener) {
        this.blockConfig = blockConfig
        this.onBlockEventListener = onBlockEventListener
    }


    fun start() {
        mChoreographer = Choreographer.getInstance()
        mChoreographer?.postFrameCallback(this)
    }

    fun stop() {
        mChoreographer?.removeFrameCallback(this)
        mChoreographer = null
    }

    override fun doFrame(frameTimeNanos: Long) {
        if (!mEventStart) {//事件开始
            mEventStart = true
            mThisEventStartTime = System.currentTimeMillis()
        }

        val thisEventEndTime = System.currentTimeMillis()
        val eventCostTime = thisEventEndTime - mThisEventStartTime
        if (eventCostTime > blockConfig.blockThresholdMillis) {
            //发生卡顿 响应时间大于1s3
            onBlockEventListener?.onEventBlock(mThisEventStartTime, thisEventEndTime)
        }
        mThisEventStartTime = System.currentTimeMillis()
        mChoreographer?.postFrameCallback(this)
    }
}