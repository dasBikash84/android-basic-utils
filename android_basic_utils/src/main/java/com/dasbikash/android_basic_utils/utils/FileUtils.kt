package com.dasbikash.android_basic_utils.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
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

    fun saveBitmap(context: Context,bitmap: Bitmap,desiredFileName:String){
        val fileName = context.filesDir.absolutePath + "/" + desiredFileName
        val file = File(fileName)
        val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.close()
    }

    fun fileToBitmap(file: File):Bitmap?{
        return BitmapFactory.decodeFile(file.absolutePath)
    }
}