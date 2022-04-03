package com.example.block.configs

class BlockConfig {
    //长卡顿、连续短卡顿、ANR监控、报名过滤

    var blockThresholdMillis: Long = 0


    private val BLOCK_THRESHOLD: Long = 2000

    constructor(blockThresholdMillis: Long) {
        this.blockThresholdMillis = blockThresholdMillis
    }

    constructor(){
        this.blockThresholdMillis = BLOCK_THRESHOLD
    }
}