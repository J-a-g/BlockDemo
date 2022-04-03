package com.example.block

import android.content.Context
import com.example.block.configs.BlockConfig
import com.example.block.core.Block
import com.example.block.core.MyChoreographer

class BlockKit private constructor() {
    @Volatile
    private var instance: BlockKit? = null

    companion object {
        val inst: BlockKit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BlockKit()
        }
    }

    private val block: Block = Block()
    private val myChoreographer: MyChoreographer = MyChoreographer()


    @Synchronized
    fun register(context: Context, blockConfig: BlockConfig) {
        block.register(context, blockConfig)
//        myChoreographer.register(context, blockConfig)
    }

    @Synchronized
    fun unRegister() {
        block.unRegister()
//        myChoreographer.unRegister()
    }
}