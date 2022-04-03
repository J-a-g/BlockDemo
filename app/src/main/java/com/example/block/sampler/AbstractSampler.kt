package com.example.block.sampler

import android.os.Handler
import android.util.Log
import com.example.block.util.ThreadUtil
import java.util.concurrent.atomic.AtomicBoolean

abstract class AbstractSampler {

    private val mShouldSample = AtomicBoolean(false)

    companion object {
        val DO_DUMP = "do-dump"
    }

    var mSampleDelay: Long = 0

    private val mRunnable = Runnable { doSample() }

    constructor(sampleDelay: Long) {
        Log.w("scj", "sampleDelay 2222:" + sampleDelay)
        mSampleDelay = sampleDelay
    }

    open fun start() {
        if (mShouldSample.getAndSet(true)) {
            return
        }
        val handler: Handler? = ThreadUtil.obtainHandler(DO_DUMP)
        handler?.removeCallbacks(mRunnable)
        handler?.postDelayed(mRunnable, mSampleDelay)
    }

    open fun stop() {
        if (!mShouldSample.getAndSet(false)) {
            return
        }
        val handler: Handler? = ThreadUtil.obtainHandler(DO_DUMP)
        handler?.removeCallbacks(mRunnable)
    }

    abstract fun doSample()
}