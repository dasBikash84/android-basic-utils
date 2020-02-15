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
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SharedPreferenceUtils(private val SP_FILE_KEY:String){

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SP_FILE_KEY, Context.MODE_PRIVATE)

    private fun getSpEditor(context: Context): SharedPreferences.Editor =
        getSharedPreferences(context).edit()

    /**
     * Supports Long,Int,Float,String and Boolean data storing
     * */
    fun <T : Any> saveData(context: Context, data: T, key: String) {
        getSpEditor(context).apply{
            GlobalScope.launch(Dispatchers.IO) {
                when (data) {
                    is Long     -> putLong(key, data as Long)
                    is Int      -> putInt(key, data as Int)
                    is Float    -> putFloat(key, data as Float)
                    is Boolean  -> putBoolean(key, data as Boolean)
                    is Double  -> putFloat(key, data as Float)
                    else        -> putString(key, data.toString())
                }
                apply()
            }
        }
    }

    /**
     * Supports Long,Int,Float,String and Boolean data storing
     * Has to provide default data of esired type
     * */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getData(context: Context, defaultValue: DefaultValues, key: String): Any {

        return getSharedPreferences(context).let {
            return@let when (defaultValue.value) {
                is Long     -> it.getLong(key, defaultValue.value)
                is Int      -> it.getInt(key, defaultValue.value)
                is Float    -> it.getFloat(key, defaultValue.value)
                is Boolean  -> it.getBoolean(key, defaultValue.value)
                else        -> it.getString(key, defaultValue.value as String)!!
            }
        }
    }

    fun getStringData(context: Context, key: String):String
            = getData(context,DefaultValues.DEFAULT_STRING,key) as String

    fun getLongData(context: Context, key: String):Long
            = getData(context,DefaultValues.DEFAULT_LONG,key) as Long

    fun getIntData(context: Context, key: String):Int
            = getData(context,DefaultValues.DEFAULT_INT,key) as Int

    fun getFloatData(context: Context, key: String):Float
            = getData(context,DefaultValues.DEFAULT_FLOAT,key) as Float

    fun getBooleanData(context: Context, key: String):Boolean
            = getData(context,DefaultValues.DEFAULT_BOOLEAN,key) as Boolean

    fun getDoubleData(context: Context, key: String):Double
            = (getData(context,DefaultValues.DEFAULT_FLOAT,key) as Float).toDouble()

    fun removeKey(context: Context,key: String)
            = getSpEditor(context).remove(key).apply()

    fun checkIfExists(context: Context, key: String):Boolean
            = getSharedPreferences(context).contains(key)

    fun clearAll(context: Context):Boolean = getSpEditor(context).clear().commit()

    fun registerOnChangeListener(context: Context,
                                 listener: SharedPreferences.OnSharedPreferenceChangeListener)
            = getSharedPreferences(context).registerOnSharedPreferenceChangeListener(listener)

    fun unRegisterOnChangeListener(context: Context,
                                   listener: SharedPreferences.OnSharedPreferenceChangeListener)
            = getSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(listener)

    companion object{
        private enum class DefaultValues(val value: Any) {
            DEFAULT_STRING(""),
            DEFAULT_LONG(0L),
            DEFAULT_INT(0),
            DEFAULT_FLOAT(0F),
            DEFAULT_BOOLEAN(false)
        }

        private val DEFAULT_SP_FILE_KEY:String =
            "com.dasbikash.android_basic_utils.utils.SP_FILE_KEY"

        fun getInstance(SP_FILE_KEY:String = DEFAULT_SP_FILE_KEY)
            = SharedPreferenceUtils(SP_FILE_KEY)
    }
}
