package com.dasbikash.android_basic_utils.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import com.dasbikash.android_basic_utils.R
import com.dasbikash.android_basic_utils.exceptions.NoInternertConnectionException

/**
 * Helper class for general display related operations.
 *
 * @author Bikash Das(das.bikash.dev@gmail.com)
 * */
object DisplayUtils {

    /**
     * Method for Dp to Pixel conversion
     *
     * @param context | Android Context
     * @param dp | Dp in Int
     * @return returns pixels in float
     * */
    fun dpToPx(dp: Int, context: Context): Float =
            (dp * context.getResources().getDisplayMetrics().density)

    /**
     * Method for Pixel to Dp conversion
     *
     * @param context | Android Context
     * @param px | pixels in float
     * @return returns Dp in float
     * */
    fun pxToDp(px: Int, context: Context): Float =
            (px / context.getResources().getDisplayMetrics().density)

    /**
     * Method to display short toast message
     *
     * @param context | Android Context
     * @param message | message for toast
     * */
    fun showShortToast(context: Context, message: String) {
        runOnMainThread (task =  {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
    }

    /**
     * Method to display short toast message
     *
     * @param context | Android Context
     * @param messageId | string resource Id for toast
     * */
    fun showShortToast(context: Context, @StringRes messageId: Int):Toast {
        val toast = Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT)
        runOnMainThread (task =  {
            toast.show()
        })
        return toast
    }

    /**
     * Method to display long toast message
     *
     * @param context | Android Context
     * @param message | message for toast
     * */
    fun showLongToast(context: Context, message: String):Toast {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        runOnMainThread (task =  {
            toast.show()
        })
        return toast
    }

    /**
     * Method to process exception.
     * Will show toast for NoInternertConnectionException
     * else will just forward the exception instance
     *
     * @param context | Android Context
     * @param exception | subject throwable
     * @return null if NoInternertConnectionException else will just forwards input exception instance
     * */
    fun processException(context: Context,exception: Throwable):Throwable?{
        if (exception is NoInternertConnectionException){
            NetConnectivityUtility.showNoInternetToast(context)
            return null
        }
        return exception
    }

    /**
     * Method to display Html text on textview.
     *
     * @param textView | Subject textView
     * @param text | text containing Html tags
     * */
    fun displayHtmlText(textView: TextView, text: String) {
        textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    /**
     * Method to display generic error message on alert dialog.
     * If input param 'message' is null then will show default message
     * Else will show 'message'
     *
     * @param context | Android Context
     * @param message | Error message
     * */
    fun showUnknownErrorDialog(context: Context,message:String?=null) {
        DialogUtils.showAlertDialog(context, DialogUtils.AlertDialogDetails(
            message = message ?: context.getString(R.string.unknown_error_message),
            negetiveButtonText = ""
        ))
    }
}

/**
 * Extension Method to display Html text on textview.
 *
 * @param text | text containing Html tags
 * */
fun TextView.displayHtmlText(text: String) {
    this.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}