package com.dasbikash.android_basic_utils.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Extension function to hide view
 * */
fun View.hide(){
    visibility = View.GONE
}

/**
 * Extension function to show view
 * */
fun View.show(){
    visibility = View.VISIBLE
}

/**
 * Extension function to make view invisible
 * */
fun View.invisiable(){
    visibility = View.INVISIBLE
}

/**
 * Extension function to toggle view visibility
 * */
fun View.toggle(){
    if (visibility== View.GONE) {
        visibility = View.VISIBLE
    }else{
        visibility = View.GONE
    }
}

/**
 * Extension function to run task with fragment context.
 * Task will run only if fragment is added i.e. not detached from parent view
 *
 * @param task subject task into which fragment context will be injected as parameter
 * @return Generic return value of task
 * */
fun <R> Fragment.runWithContext(task:(Context)->R){
    runIfActive {
        context?.let { task(it) }
    }
}

/**
 * Extension function to run task with fragment activity.
 * Task will run only if fragment is added i.e. not detached from parent view
 *
 * @param task subject task into which fragment activity will be injected as parameter
 * @return Generic return value of task
 * */
fun <R> Fragment.runWithActivity(task:(Activity)->R){
    runIfActive {
        activity?.let { task(it) }
    }
}

/**
 * Extension function on Activity class to launch activity of given type.
 *
 * @param type java Class type of given activity class
 * */
fun <T: Activity> Activity.jumpToSelectedActivity(type:Class<T>){
    val intent = Intent(this,type)
    startActivity(intent)
}

/**
 * Extension function on show Long Toast from Activity
 * Will run only if activity is not destroyed
 *
 * @param message Toast message
 * */
fun Activity.showLongToast(message: String){
    runIfActive {
        DisplayUtils.showLongToast(this,message)
    }
}

/**
 * Extension function on show Long Toast from Activity
 * Will run only if activity is not destroyed
 *
 * @param messageId string id resource for Toast message
 * */
fun Activity.showLongToast(@StringRes messageId: Int)
        = showLongToast(getString(messageId))

/**
 * Extension function on show short Toast from Activity
 * Will run only if activity is not destroyed
 *
 * @param message Toast message
 * */
fun Activity.showShortToast(message: String){
    runIfActive {
        DisplayUtils.showShortToast(this,message)
    }
}

/**
 * Extension function on show short Toast from Activity
 * Will run only if activity is not destroyed
 *
 * @param messageId string id resource for Toast message
 * */
fun Activity.showShortToast(@StringRes messageId: Int)
    = showShortToast(getString(messageId))

/**
 * Extension function on show Long Toast from fragment
 *
 * @param message Toast message
 * */
fun Fragment.showLongToast(message: String){
    runWithContext {
        DisplayUtils.showLongToast(it,message)
    }
}

/**
 * Extension function on show Long Toast from fragment
 *
 * @param messageId string id resource for Toast message
 * */
fun Fragment.showLongToast(@StringRes messageId: Int)
        = showLongToast(getString(messageId))

/**
 * Extension function on show short Toast from fragment
 *
 * @param message Toast message
 * */
fun Fragment.showShortToast(message: String){
    runWithContext {
        DisplayUtils.showShortToast(it,message)
    }
}

/**
 * Extension function on show short Toast from fragment
 *
 * @param messageId string id resource for Toast message
 * */
fun Fragment.showShortToast(@StringRes messageId: Int)
    = showShortToast(getString(messageId))


/**
 * Extension function on launch task on main thread with optional delay
 *
 * @param task posted functional parameter
 * @param delayMs optional delay in milli-seconds
 * */
fun runOnMainThread(task: () -> Any?,delayMs:Long=0L){
    Handler(Looper.getMainLooper()).postDelayed( { task() },delayMs)
}

/**
 * Extension function on check if running on Main/UI thread
 *
 * @return true if on main thread else false
 *
 */
fun isOnMainThread() = (Thread.currentThread() == Looper.getMainLooper().thread)

/**
 * Extension function on launch task on main thread with optional delay from activity
 * Task will run only if activity is not destroyed
 *
 * @param task posted functional parameter
 * @param delayMs optional delay in milli-seconds
 * */
fun Activity.runOnMainThread(task: () -> Any?,delayMs:Long=0L){
        Handler(Looper.getMainLooper()).postDelayed({
            runIfActive {
                task()
            }
        }, delayMs)
}

/**
 * Extension function on launch task on main thread with optional delay from Fragment
 * Task will run only if fragment is attached on parent activity
 *
 * @param task posted functional parameter
 * @param delayMs optional delay in milli-seconds
 * */
fun Fragment.runOnMainThread(task: () -> Any?,delayMs:Long=0L){
    runIfActive {
        Handler(Looper.getMainLooper()).postDelayed({ task() }, delayMs)
    }
}

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

/**
 * Extension function on launch task from activity
 * Task will run only if activity is not destroyed
 *
 * @param task posted functional parameter
 * */
fun Activity.runIfActive(task:()->Any?){
    if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            !isDestroyed
        } else {
            true
        }
    ) {
        task()
    }
}


/**
 * Extension function on launch task from fragment
 * Task will run only if fragment is attached on parent activity
 *
 * @param task posted functional parameter
 * */
fun Fragment.runIfActive(task:()->Any?){
    if (isAdded) {
        task()
    }
}

/**
 * Extension function on launch async task
 * suspending any suspension function
 *
 * @param task posted functional parameter
 * */
suspend fun <T:Any> runSuspended(task:()->T):T {
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
suspend fun coroutineContext(): CoroutineContext = suspendCoroutine { it.resume(it.context) }


/**
 * Extension function on launch task from any LifecycleOwner
 * Task will run only if LifecycleOwner is not destroyed
 *
 * @param task posted functional parameter
 * */
fun LifecycleOwner.runIfNotDestroyed(task:()->Any?){
    if (this.lifecycle.currentState != Lifecycle.State.DESTROYED){
        task()
    }
}

/**
 * Extension function on launch task from any LifecycleOwner
 * Task will run only if LifecycleOwner is resumed
 *
 * @param task posted functional parameter
 * */
fun LifecycleOwner.runIfResumed(task:()->Any?){
    if (this.lifecycle.currentState == Lifecycle.State.RESUMED){
        task()
    }
}

/**
 * Extension function on launch task from any LifecycleOwner
 * Task will run only if LifecycleOwner is created
 *
 * @param task posted functional parameter
 * */
fun LifecycleOwner.runIfCreated(task:()->Any?){
    if (this.lifecycle.currentState == Lifecycle.State.CREATED){
        task()
    }
}