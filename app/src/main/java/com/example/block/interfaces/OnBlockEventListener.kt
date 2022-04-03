package com.example.block.interfaces

interface OnBlockEventListener {

    fun onEventStart()

    fun onEventEnd()

    fun onEventBlock(eventStartTimeMilliis: Long, eventEndTimeMillis: Long)
}