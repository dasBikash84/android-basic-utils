package com.dasbikash.android_basic_utils.utils

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Helper class for Shared Preference related operations.
 *
 * @author Bikash Das(das.bikash.dev@gmail.com)
 * */
class SharedPreferenceUtils(private val SP_FILE_KEY:String){

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SP_FILE_KEY, Context.MODE_PRIVATE)

    private fun getSpEditor(context: Context): SharedPreferences.Editor =
        getSharedPreferences(context).edit()

    /**
     * Save object on Shared Preference
     * Saves equivalent literal value for Long,Int,Float,Double & Boolean
     * Saves String equivalent for other data type
     *
     * @param context Android Context
     * @param data subject object that is to be saved
     * @param key unique key to the object to be saved
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

    /**
     * Retrieves String data from Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * @return saved String if key found else empty String
     * */
    fun getStringData(context: Context, key: String):String
            = getData(context,DefaultValues.DEFAULT_STRING,key) as String

    /**
     * Retrieves Long data from Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * @return saved Long value if key found else 0L
     * */
    fun getLongData(context: Context, key: String):Long
            = getData(context,DefaultValues.DEFAULT_LONG,key) as Long

    /**
     * Retrieves Int data from Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * @return saved Int value if key found else 0
     * */
    fun getIntData(context: Context, key: String):Int
            = getData(context,DefaultValues.DEFAULT_INT,key) as Int

    /**
     * Retrieves Float data from Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * @return saved Float value if key found else 0.0F
     * */
    fun getFloatData(context: Context, key: String):Float
            = getData(context,DefaultValues.DEFAULT_FLOAT,key) as Float

    /**
     * Retrieves Boolean data from Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * @return saved Boolean value if key found else false
     * */
    fun getBooleanData(context: Context, key: String):Boolean
            = getData(context,DefaultValues.DEFAULT_BOOLEAN,key) as Boolean

    /**
     * Retrieves Double data from Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * @return saved Double value if key found else 0.0
     * */
    fun getDoubleData(context: Context, key: String):Double
            = (getData(context,DefaultValues.DEFAULT_FLOAT,key) as Float).toDouble()

    /**
     * Removes object with given key from Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * */
    fun removeKey(context: Context,key: String)
            = getSpEditor(context).remove(key).apply()

    /**
     * Checks whwather object with given key exists on Shared Preferences
     *
     * @param context Android Context
     * @param key unique key to the saved object
     * @return true if found else false
     * */
    fun checkIfExists(context: Context, key: String):Boolean
            = getSharedPreferences(context).contains(key)

    /**
     * Clears all saved data from subject Shared Preferences
     *
     * @param context Android Context
     * */
    fun clearAll(context: Context):Boolean = getSpEditor(context).clear().commit()

    /**
     * Registers Shared Preference Change Listener     *
     *
     * */
    fun registerOnChangeListener(context: Context,
                                 listener: SharedPreferences.OnSharedPreferenceChangeListener)
            = getSharedPreferences(context).registerOnSharedPreferenceChangeListener(listener)

    /**
     * Un-registers Shared Preference Change Listener     *
     *
     * */
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

        /**
         * Returns class instance for given Shared Preferences storage file
         *
         * @param SP_FILE_KEY Shared Preferences storage file name
         * @return instance of SharedPreferenceUtils
         * */
        @JvmStatic
        fun getInstance(SP_FILE_KEY:String = DEFAULT_SP_FILE_KEY) = SharedPreferenceUtils(SP_FILE_KEY)


        /**
         * Returns class instance for default Shared Preferences storage file
         *
         * @return instance of SharedPreferenceUtils that points to default file.
         * */
        @JvmStatic
        fun getDefaultInstance() = SharedPreferenceUtils(DEFAULT_SP_FILE_KEY)
    }
}
