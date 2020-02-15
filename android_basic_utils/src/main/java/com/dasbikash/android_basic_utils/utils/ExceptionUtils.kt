package com.dasbikash.android_basic_utils.utils

import com.dasbikash.android_basic_utils.exceptions.NoInternertConnectionException
import com.dasbikash.android_basic_utils.exceptions.NotOnMainThreadException
import com.dasbikash.android_basic_utils.exceptions.OnMainThreadException
import java.util.*

object ExceptionUtils {

    private fun throwExceptionIfOnMainThred(){
        if (isOnMainThread()) {
            throw OnMainThreadException()
        }
    }

    fun thowExceptionIfNotOnMainThred(){
        if (!isOnMainThread()) {
            throw NotOnMainThreadException()
        }
    }
    private fun throwExceptionIfNoInternetConnection(){
        if (!NetConnectivityUtility.isConnected) {
            throw NoInternertConnectionException();
        }
    }

    fun checkRequestValidityBeforeNetworkAccess(){
        throwExceptionIfNoInternetConnection()
    }

    fun checkRequestValidityBeforeDatabaseAccess(){
        throwExceptionIfOnMainThred()
    }

    fun checkRequestValidityBeforeLocalDiskAccess() {
        throwExceptionIfOnMainThred()
    }

    fun getStackTraceAsString(ex:Throwable):String{
        val stackTracebuilder = StringBuilder("")
        Arrays.asList(ex.stackTrace).asSequence().forEach {
            it.iterator().forEach {
                stackTracebuilder.append(it.toString()).append("\n")
            }
        }
        return stackTracebuilder.toString()
    }
}