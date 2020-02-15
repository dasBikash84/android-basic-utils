package com.dasbikash.android_basic_utils.utils

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog

/**
 * Helper class for Alert Dialog related operations.
 *
 * @author Bikash Das(das.bikash.dev@gmail.com)
 * */
object DialogUtils {

    private const val DEFAULT_POS_BUTTON_TEXT = "Ok"
    private const val DEFAULT_NEG_BUTTON_TEXT = "Cancel"
    private const val DEFAULT_NEUTRAL_BUTTON_TEXT = ""

    /**
     * Method for creating and showing Alert Dialog
     *
     * @param context | Android Context
     * @param alertDialogDetails | AlertDialogDetails for subject dialog
     * @return returns subject AlertDialog
     * */
    fun showAlertDialog(context: Context,alertDialogDetails: AlertDialogDetails):AlertDialog{
        val dialog =  createAlertDialog(context,alertDialogDetails)
        dialog.show()
        return dialog
    }

    /**
     * Method for creating Alert Dialog
     *
     * @param context | Android Context
     * @param alertDialogDetails | AlertDialogDetails for subject dialog
     * @return returns subject AlertDialog
     * */
    fun createAlertDialog(context: Context,alertDialogDetails: AlertDialogDetails):AlertDialog{
        val dialog =  getAlertDialogBuilder(context,alertDialogDetails).create()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)?.isAllCaps = false
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE)?.isAllCaps = false
        dialog.getButton(DialogInterface.BUTTON_NEUTRAL)?.isAllCaps = false
        return dialog
    }

    private fun getAlertDialogBuilder(context: Context, alertDialogDetails: AlertDialogDetails):AlertDialog.Builder{
        val dialogBuilder = AlertDialog.Builder(context)

        if (alertDialogDetails.title.isNotBlank()){
            dialogBuilder.setTitle(alertDialogDetails.title.trim())
        }
        if (alertDialogDetails.message.isNotBlank()){
            dialogBuilder.setMessage(alertDialogDetails.message.trim())
        }
        dialogBuilder.setPositiveButton(alertDialogDetails.positiveButtonText.trim(),
                { _: DialogInterface?, _: Int -> alertDialogDetails.doOnPositivePress() })

        dialogBuilder.setNegativeButton(alertDialogDetails.negetiveButtonText.trim(),
                { _: DialogInterface?, _: Int -> alertDialogDetails.doOnNegetivePress() })

        dialogBuilder.setNeutralButton(alertDialogDetails.neutralButtonText.trim(),
                { _: DialogInterface?, _: Int -> alertDialogDetails.doOnNeutralPress() })

        dialogBuilder.setCancelable(alertDialogDetails.isCancelable)
        alertDialogDetails.view?.let {
            dialogBuilder.setView(it)
        }
        return dialogBuilder
    }

    /**
     * class to hold parameters regarding Alert Dialog
     * */
    class AlertDialogDetails(
        val title:String = "",
        val message:String = "",
        val positiveButtonText:String = DEFAULT_POS_BUTTON_TEXT,
        val negetiveButtonText:String = DEFAULT_NEG_BUTTON_TEXT,
        val neutralButtonText:String = DEFAULT_NEUTRAL_BUTTON_TEXT,
        val isCancelable:Boolean = true,
        val doOnPositivePress:()->Unit = {},
        val doOnNegetivePress:()->Unit = {},
        val doOnNeutralPress:()->Unit = {},
        val view:View? = null
    )

}