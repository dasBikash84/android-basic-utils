package com.dasbikash.android_basic_utils.utils

import android.content.Context
import java.io.File
import java.util.*


/**
 * Helper class for File related operations.
 *
 * @author Bikash Das(das.bikash.dev@gmail.com)
 * */
object FileUtils {

    /**
     * Method for saving file on internal storage
     *
     * @param file | Subject file
     * @param context | Android Context
     * @param desiredFileName | File name for saving
     * */
    fun saveFileOnInternalStorage(file:File,context: Context,desiredFileName:String?=null){

        val fileName:String
        if (desiredFileName==null){
            fileName = context.filesDir.absolutePath + "/" + UUID.randomUUID().toString()
        }else{
            fileName = context.filesDir.absolutePath + "/" + desiredFileName
        }
        val newFile = File(fileName)
        file.copyTo(newFile)
    }

    /**
     * Method for reading file from internal storage
     *
     * @param fileName | Subject file name
     * @param context | Android Context
     * @return File if found else null
     * */
    fun readFileFromInternalStorage(context: Context,fileName:String):File?{

        val dir = File(context.filesDir.absolutePath)
        if (dir.exists() && dir.isDirectory){
            dir.list()?.asSequence()?.forEach {
                if (it==fileName){
                    return File(context.filesDir.absolutePath + "/" +fileName)
                }
            }
        }
        return null
    }
}