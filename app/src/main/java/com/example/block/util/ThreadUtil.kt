package com.example.block.util

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process
import java.util.HashMap

object ThreadUtil
{

    private val sLockForHandlerManager = Any()

    private val sHandlerMap: MutableMap<String, Handler> = HashMap()

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    fun createIfNotExistHandler(tag: String): Handler {
        synchronized(sLockForHandlerManager) {
            val tmp = sHandlerMap[tag]
            if (tmp != null) {
                return tmp
            }
            val handlerThread =
                HandlerThread(tag, Process.THREAD_PRIORITY_BACKGROUND)
            handlerThread.start()
            val handler = Handler(handlerThread.looper)
            sHandlerMap[tag] = handler
            return handler
        }
    }

    fun obtainHandler(tag: String): Handler? {
        synchronized(sLockForHandlerManager) { return sHandlerMap[tag] }
    }

    fun destoryHandler(tag: String) {
        synchronized(sLockForHandlerManager) {
            val handler = sHandlerMap.remove(tag)
            handler?.looper?.quit()
        }
    }
}