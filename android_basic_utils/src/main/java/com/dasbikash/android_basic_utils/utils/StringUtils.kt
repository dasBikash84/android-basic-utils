package com.dasbikash.android_basic_utils.utils

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