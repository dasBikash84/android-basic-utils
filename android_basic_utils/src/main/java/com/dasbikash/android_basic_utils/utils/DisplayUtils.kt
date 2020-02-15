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

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import com.dasbikash.android_basic_utils.R
import com.dasbikash.android_basic_utils.exceptions.NoInternertConnectionException

object DisplayUtils {

    fun dpToPx(dp: Int, context: Context): Float =
            (dp * context.getResources().getDisplayMetrics().density)

    fun pxToDp(px: Int, context: Context): Float =
            (px / context.getResources().getDisplayMetrics().density)

    fun showShortToast(context: Context, message: String) {
        runOnMainThread (task =  {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
    }

    fun showShortToast(context: Context, @StringRes messageId: Int):Toast {
        val toast = Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT)
        runOnMainThread (task =  {
            toast.show()
        })
        return toast
    }

    fun showLongToast(context: Context, message: String):Toast {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        runOnMainThread (task =  {
            toast.show()
        })
        return toast
    }

    fun showShortMessage(context: Context, message: String) {
        if (isOnMainThread()) {
            runOnMainThread (task =  {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    fun processException(context: Context,exception: Throwable):Throwable?{
        if (exception is NoInternertConnectionException){
            NetConnectivityUtility.showNoInternetToast(context)
            return null
        }
        return exception
    }

    fun displayHtmlText(textView: TextView, text: String) {
        textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun showUnknownErrorDialog(context: Context,message:String?=null) {
        DialogUtils.showAlertDialog(context, DialogUtils.AlertDialogDetails(
            message = message ?: context.getString(R.string.unknown_error_message),
            negetiveButtonText = ""
        ))
    }
}