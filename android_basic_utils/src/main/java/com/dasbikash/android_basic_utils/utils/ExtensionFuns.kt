package com.dasbikash.android_basic_utils.utils

import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Extension function on check if running on Main/UI thread
 *
 * @return true if on main thread else false
 *
 */
internal fun isOnMainThread() = (Thread.currentThread() == Looper.getMainLooper().thread)


/**
 * Get currency string for double value
 *
 * @return currency string for subject value
 * */
fun Double.getCurrencyString():String{
    return NumberFormat.getCurrencyInstance().format(this).substring(1)
}

/**
 * Get currency string for Long value
 *
 * @return currency string for subject value
 * */
fun Long.getCurrencyString():String{
    return NumberFormat.getCurrencyInstance().format(this).substring(1)
}

internal suspend fun <T> runSuspended(task:()->T):T {
    coroutineContext().let {
        return withContext(it) {
            return@withContext async(Dispatchers.IO) { task() }.await()
        }
    }
}

/**
 * Extension function on access CoroutineContext from inside of any suspension function
 *
 * @return subject CoroutineContext
 * */
internal suspend fun coroutineContext(): CoroutineContext = suspendCoroutine { it.resume(it.context) }