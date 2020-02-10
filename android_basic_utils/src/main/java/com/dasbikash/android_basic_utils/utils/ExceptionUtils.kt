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