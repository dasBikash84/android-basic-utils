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

fun View.hide(){
    visibility = View.GONE
}
fun View.show(){
    visibility = View.VISIBLE
}
fun View.invisiable(){
    visibility = View.INVISIBLE
}
fun View.toggle(){
    if (visibility== View.GONE) {
        visibility = View.VISIBLE
    }else{
        visibility = View.GONE
    }
}

fun <R> Fragment.runWithContext(task:(Context)->R){
    runIfActive {
        context?.let { task(it) }
    }
}

fun <R> Fragment.runWithActivity(task:(Activity)->R){
    runIfActive {
        activity?.let { task(it) }
    }
}

fun <T: Activity> Activity.jumpToSelectedActivity(type:Class<T>){
    val intent = Intent(this,type)
    startActivity(intent)
}

fun Fragment.showLongToast(message: String){
    runWithContext {
        DisplayUtils.showLongToast(it,message)
    }
}

fun Fragment.showLongToast(@StringRes messageId: Int){
    runWithContext {
        DisplayUtils.showLongToast(it,it.getString(messageId))
    }
}

fun Fragment.showShortToast(message: String){
    runWithContext {
        DisplayUtils.showShortToast(it,message)
    }
}

fun Fragment.showShortToast(@StringRes messageId: Int){
    runWithContext {
        DisplayUtils.showShortToast(it,it.getString(messageId))
    }
}

fun runOnMainThread(task: () -> Any?,delayMs:Long=0L){
    Handler(Looper.getMainLooper()).postDelayed( { task() },delayMs)
}

fun isOnMainThread() = (Thread.currentThread() == Looper.getMainLooper().thread)

fun Activity.runOnMainThread(task: () -> Any?,delayMs:Long=0L){
    runIfActive {
        Handler(Looper.getMainLooper()).postDelayed({ task() }, delayMs)
    }
}

fun Fragment.runOnMainThread(task: () -> Any?,delayMs:Long=0L){
    runIfActive {
        Handler(Looper.getMainLooper()).postDelayed({ task() }, delayMs)
    }
}

fun Double.getCurrencyString():String{
    return NumberFormat.getCurrencyInstance().format(this).substring(1)
}

fun Long.getCurrencyString():String{
    return NumberFormat.getCurrencyInstance().format(this).substring(1)
}

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

fun Fragment.runIfActive(task:()->Any?){
    if (isAdded) {
        task()
    }
}

suspend fun <T:Any> runSuspended(task:()->T):T {
    coroutineContext().let {
        return withContext(it) {
            return@withContext async(Dispatchers.IO) { task() }.await()
        }
    }
}
suspend fun coroutineContext(): CoroutineContext = suspendCoroutine { it.resume(it.context) }


fun LifecycleOwner.runIfNotDestroyed(task:()->Any?){
    if (this.lifecycle.currentState != Lifecycle.State.DESTROYED){
        task()
    }
}

fun LifecycleOwner.runIfResumed(task:()->Any?){
    if (this.lifecycle.currentState == Lifecycle.State.RESUMED){
        task()
    }
}

fun LifecycleOwner.runIfCreated(task:()->Any?){
    if (this.lifecycle.currentState == Lifecycle.State.CREATED){
        task()
    }
}