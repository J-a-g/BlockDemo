package com.example.block.interfaces

import android.content.Context

interface Register<T> {
    fun register(context: Context, config: T?): Boolean

    fun unRegister()

    fun work()
}