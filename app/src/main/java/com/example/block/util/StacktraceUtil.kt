package com.example.block.util

import java.util.ArrayList

object  StacktraceUtil {
        fun stackTraceToStringArray(stackTraceElements: List<StackTraceElement>): List<String> {
            val stackList: MutableList<String> = ArrayList()
            for (traceElement in stackTraceElements) {
                stackList.add(traceElement.toString())
            }
            return stackList
        }
}