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

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val DAY_IN_MS: Long = 24 * 60 * 60 * 1000L
    const val HOUR_IN_MS: Long = 60 * 60 * 1000L
    const val MINUTE_IN_MS: Long = 60 * 1000L
    const val SECOND_IN_MS: Long = 1000L

    private const val FULL_DATE_STRING_FORMAT = "dd MMM yyyy HH:mm:ss"
    private const val SHORT_DATE_STRING_FORMAT = "dd MMM yyyy"
    private const val TIME_STRING_FORMAT = "hh:mm:ss a"

    fun getLongDateString(date: Date):String =
        getDateString(
            FULL_DATE_STRING_FORMAT,
            date
        )

    private fun getDateString(format:String,date: Date) =
            SimpleDateFormat(format, Locale.ENGLISH).format(date)

    fun getShortDateString(date: Date):String =
        getDateString(
            SHORT_DATE_STRING_FORMAT,
            date
        )

    fun getTimeString(date: Date):String =
        getDateString(
            TIME_STRING_FORMAT,
            date
        )

    fun getTimeBasedGreetingsMessage():String{
//        Morning: 5:00 am to 12:00 am
//        Afternoon: 12:00 pm to 5:00 pm
//        Evening: 5:00 pm to 10:00 pm
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when{
            hour >= 17 -> "Good evening"
            hour >= 12 -> "Good after-noon"
            else -> "Good morning"
        }
    }
}