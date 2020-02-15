package com.dasbikash.android_basic_utils.utils

import android.content.Context
import java.io.File
import java.util.*


object FileUtils {

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