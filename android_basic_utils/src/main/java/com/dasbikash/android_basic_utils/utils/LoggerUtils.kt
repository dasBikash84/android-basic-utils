/*
 * Copyright 2020 das.bikash.dev@gmail.com. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dasbikash.android_basic_utils.utils

import android.util.Log

/**
 * Helper class for logging
 */
object LoggerUtils {
    private const val TAG = "AU>>"
    private const val MAX_TAG_LENGTH = 23

    private var enabled:Boolean = true
    private var tag:String = TAG

    /**
     * Optional method for configuring logger
     *
     * @author Bikash Das
     * @param enabled for enable/disable logging.
     * @param tag for setting logger tag preamble.
     * */
    fun init(enabled:Boolean=true, tag:String= TAG){
        LoggerUtils.enabled = enabled
        LoggerUtils.tag = tag
    }

    fun debugStackTrace(ex: Throwable) {
        if (enabled) {
            logStackTrace(ex)
        }
    }

    /**
     * For printing log message
     *
     * @author Bikash Das
     * @param message for logging
     * @param type caller class type
     * @sample
     * */
    fun <T> debugLog(message: String, type: Class<T>) {
        if (enabled) {
            return log(message, type)
        }
    }

    fun <T> debugLog(obj: Any, type: Class<T>) {
        return debugLog(obj.toString(),type)
    }

    private fun <T> log(message: String, type: Class<T>) {
        var classNameEndIndex = type.simpleName.length
        if (classNameEndIndex > (MAX_TAG_LENGTH - tag.length)) {
            classNameEndIndex = MAX_TAG_LENGTH - tag.length
        }
        Log.d(tag + type.simpleName.substring(0, classNameEndIndex), message)
    }

    private fun logStackTrace(ex: Throwable) {
        ex.printStackTrace()
//        debugLog(ExceptionUtils.getStackTraceAsString(ex))
    }
}

/**
 * For printing log message
 *
 * @author Bikash Das
 * @param message for logging
 * */
fun Any.debugLog(message: String){
    LoggerUtils.debugLog(message,this::class.java)
}

/**
 * For printing string version of an object
 *
 * @author Bikash Das
 * @param model subject object
 * */
fun Any.debugLog(model: Any){
    LoggerUtils.debugLog(model,this::class.java)
}

/**
 * For printing error stacktrace
 *
 * @author Bikash Das
 * @param ex subject throwable
 * */
fun Any.debugStackTrace(ex: Throwable){
    LoggerUtils.debugStackTrace(ex)
}