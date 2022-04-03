package com.example.block.core

import android.content.Context
import android.util.Log
import com.example.block.configs.BlockConfig
import com.example.block.configs.BlockInfo
import com.example.block.interfaces.OnBlockEventListener
import com.example.block.interfaces.Register

class MyChoreographer : Register<BlockConfig> {

    private var isRegister: Boolean = false
    private var choreographerMonitor: ChoreographerMonitor? = null

    constructor() {
    }


    override fun register(context: Context, config: BlockConfig?): Boolean {
        if (isRegister) {
            return true
        }
        choreographerMonitor = config?.let {
            ChoreographerMonitor(it, object : OnBlockEventListener{
                override fun onEventStart() {
                }

                override fun onEventEnd() {

                }

                override fun onEventBlock(eventStartTimeMilliis: Long, eventEndTimeMillis: Long) {
                        val blockInfo = BlockInfo(
                            eventStartTimeMilliis, eventEndTimeMillis
                        )
                        Log.w("scj", "blockBaseinfo ->" + blockInfo.toString())

                }
            })
        }
        choreographerMonitor?.start()
        isRegister = true
        return true
    }

    override fun unRegister() {
        isRegister = false
        choreographerMonitor?.stop()
    }

    override fun work() {
    }
}