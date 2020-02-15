package com.dasbikash.android_basic_utils.utils

class OnceSettableBoolean {
    private var status = false
    fun get():Boolean{
        return status
    }
    fun set(){
        status = true
    }
}