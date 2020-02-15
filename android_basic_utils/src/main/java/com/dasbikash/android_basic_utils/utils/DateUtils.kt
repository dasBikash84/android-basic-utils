package com.dasbikash.android_basic_utils.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val SECOND_IN_MS: Long = 1000L
    const val MINUTE_IN_MS: Long = 60 * SECOND_IN_MS
    const val HOUR_IN_MS: Long = 60 * MINUTE_IN_MS
    const val DAY_IN_MS: Long = 24 * HOUR_IN_MS
    const val YEAR_IN_MS: Long = 365 * DAY_IN_MS

    private const val FULL_DATE_STRING_FORMAT = "dd MMM yyyy HH:mm:ss"
    private const val SHORT_DATE_STRING_FORMAT = "dd MMM yyyy"
    private const val TIME_STRING_FORMAT = "hh:mm:ss a"

    fun getLongDateString(date: Date,longDateFormat:String= FULL_DATE_STRING_FORMAT):String =
        getDateString(
            longDateFormat,
            date
        )

    private fun getDateString(format:String,date: Date) =
            SimpleDateFormat(format, Locale.ENGLISH).format(date)

    fun getShortDateString(date: Date,shortDateFormat:String= SHORT_DATE_STRING_FORMAT):String =
        getDateString(
            shortDateFormat,
            date
        )

    fun getTimeString(date: Date,timeString:String= TIME_STRING_FORMAT):String =
        getDateString(
            timeString,
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