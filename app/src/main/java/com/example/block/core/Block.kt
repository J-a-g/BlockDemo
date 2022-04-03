package com.example.block.core

import android.content.Context
import android.os.Looper
import android.util.Log
import com.example.block.configs.BlockConfig
import com.example.block.configs.BlockInfo
import com.example.block.interfaces.OnBlockEventListener
import com.example.block.interfaces.Register
import com.example.block.sampler.AbstractSampler
import com.example.block.sampler.StackSampler
import com.example.block.util.ThreadUtil
import io.reactivex.schedulers.Schedulers

class Block : Register<BlockConfig> {

    private lateinit var blockPrinter: BlockPrinter
    private var isRegister: Boolean = false
    private lateinit var blockConfig: BlockConfig
    private lateinit var stackSampler: StackSampler

    @Synchronized
    override fun register(context: Context, config: BlockConfig?): Boolean {
        if (isRegister) {
            return true
        }
        if (config == null) {
            return false
        }
        this.blockConfig = config
        this.stackSampler = StackSampler((config.blockThresholdMillis * 0.9).toLong())
        this.blockPrinter = BlockPrinter(config, object : OnBlockEventListener {
            override fun onEventStart() {
                stackSampler.start()
            }

            override fun onEventEnd() {
                stackSampler.stop()
            }

            override fun onEventBlock(eventStartTimeMilliis: Long, eventEndTimeMillis: Long) {
                Schedulers.computation().scheduleDirect {
                    val threadStackEntries = stackSampler.getThreadStackEntries(
                        eventStartTimeMilliis,
                        eventEndTimeMillis
                    )

                    val blockInfo = BlockInfo(
                        eventStartTimeMilliis, eventEndTimeMillis, threadStackEntries
                    )
                    Log.w("scj", "blockBaseinfo ->" + blockInfo.toString())
                }
            }
        })
        Looper.getMainLooper().setMessageLogging(this.blockPrinter)
        ThreadUtil.createIfNotExistHandler(AbstractSampler.DO_DUMP)
        isRegister = true
        return true
    }

    @Synchronized
    override fun unRegister() {
        isRegister = false
        Looper.getMainLooper().setMessageLogging(null)
        ThreadUtil.destoryHandler(AbstractSampler.DO_DUMP)
        stackSampler.stop()
    }

    override fun work() {
    }
}