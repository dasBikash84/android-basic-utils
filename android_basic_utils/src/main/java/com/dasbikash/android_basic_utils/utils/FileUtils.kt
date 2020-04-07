package com.dasbikash.android_basic_utils.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
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
    fun saveFileOnInternalStorage(file:File,context: Context,
                                  desiredFileName:String?=null,overwrite: Boolean = false){

        val fileName:String
        if (desiredFileName==null){
            fileName = context.filesDir.absolutePath + "/" + UUID.randomUUID().toString()
        }else{
            fileName = context.filesDir.absolutePath + "/" + desiredFileName
        }
        val newFile = File(fileName)
        file.copyTo(newFile,overwrite)
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

    suspend fun uriToFile(activity: Activity, uri: Uri, filename:String?=null):File?{
        return runSuspended {
            try {
                val fName = filename ?: UUID.randomUUID().toString().take(12)
                val tempFile = File.createTempFile(fName,"")
                val fos = FileOutputStream(tempFile)
                activity.getContentResolver().openInputStream(uri)?.let {
                    it.copyTo(fos)
                    it.close()
                }
                fos.close()
                fos.flush()
                return@runSuspended tempFile
            }catch (ex:Throwable){
                ex.printStackTrace()
            }
            return@runSuspended null
        }
    }
}

suspend fun Activity.uriToFile(uri: Uri, filename:String?=null):File? =
    FileUtils.uriToFile(this,uri, filename)