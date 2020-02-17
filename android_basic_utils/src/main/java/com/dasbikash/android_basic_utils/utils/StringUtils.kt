package com.dasbikash.android_basic_utils.utils

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object StringUtils {
    private val pTags = Pair("<p>","</p>")
    private val ulTags = Pair("<ul>","</ul>")
    private val olTags = Pair("<ol>","</ol>")
    private val listTags = Pair("<li>","</li>")
    private val strongTags = Pair("<strong>","</strong>")

    fun iterableToOl(list:Iterable<String>):String{
        return iterableToHtmlList(
            list,
            olTags
        )
    }
    fun iterableToUl(list:Iterable<String>):String{
        return iterableToHtmlList(
            list,
            ulTags
        )
    }
    private fun iterableToHtmlList(list:Iterable<String>,tags:Pair<String,String>):String{
        val htmlListBuilder = StringBuilder(pTags.first).append(tags.first)
        list.asSequence().forEach {
            htmlListBuilder
                .append(listTags.first)
                .append(it)
                .append(listTags.second)
                .append("<br><br>")
        }
        htmlListBuilder.append(tags.second).append(pTags.second)
        return htmlListBuilder.toString()
    }
}


fun ByteArray.toCharArray():CharArray{
    val charArray = CharArray(this.size)
    for (i in 0..size-1){
        charArray.set(i,get(i).toChar())
    }
    return charArray
}

fun CharArray.byteArray():ByteArray{
    val bytes = ByteArray(this.size)
    for (i in 0..size-1){
        bytes.set(i,get(i).toByte())
    }
    return bytes
}

internal fun ByteArray.toSerializedString():String = String(toCharArray())
internal fun String.deserialize():ByteArray = toCharArray().byteArray()

fun java.io.Serializable.toByteArray():ByteArray{
    val buffer = ByteArrayOutputStream()
    val oos = ObjectOutputStream(buffer)
    oos.writeObject(this)
    oos.close()
    return buffer.toByteArray()
}

fun <T:java.io.Serializable> ByteArray.toSerializable(type:Class<T>):T{
    return ObjectInputStream(ByteArrayInputStream(this)).readObject() as T
}