package com.dasbikash.android_basic_utils.utils

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * Helper class for Shared Preference related operations.
 * Any Serializable object can be saved on shared preference
 *
 * @author Bikash Das(das.bikash.dev@gmail.com)
 * */
class SharedPreferenceUtils(private val SP_FILE_KEY:String){

    fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SP_FILE_KEY, Context.MODE_PRIVATE)

    fun getSpEditor(context: Context): SharedPreferences.Editor =
        getSharedPreferences(context).edit()

    /**
     * Method to save(blocking) Serializable object on Shared Preference
     *
     * @param context Android Context
     * @param data subject Serializable object that is to be saved
     * @param key unique key to the object to be saved
     * */
    fun saveDataSync(context: Context, data: Serializable, key: String) {
        saveData(getSpEditor(context),data, key)
    }

    /**
     * Method to save(async) Serializable object on Shared Preference
     *
     * @param context Android Context
     * @param data subject Serializable object that is to be saved
     * @param key unique key to the object to be saved
     * */
    fun saveData(context: Context, data: Serializable, key: String) {
        GlobalScope.launch(Dispatchers.IO) {
            saveData(getSpEditor(context),data, key)
        }
    }

    /**
     * Method to save(suspended) Serializable object on Shared Preference
     *
     * @param context Android Context
     * @param data subject Serializable object that is to be saved
     * @param key unique key to the object to be saved
     * */
    suspend fun saveDataSuspended(context: Context, data: Serializable, key: String) {
        runSuspended {
            saveData(getSpEditor(context),data, key)
        }
    }

    private fun saveData(editor: SharedPreferences.Editor,data: Serializable, key: String){
        /*
                when (data) {
                    is Long     -> putLong(key, data as Long)
                    is Int      -> putInt(key, data as Int)
                    is Float    -> putFloat(key, data as Float)
                    is Boolean  -> putBoolean(key, data as Boolean)
                    is Double  -> putFloat(key, data as Float)
                    else        -> putString(key, data.toString())
                }
        * */
        editor.putString(key,data.toByteArray().toSerializedString())
        editor.apply()
    }

    /**
     * Method to read serializable object from Shared Preference
     *
     * @param context Android Context
     * @param key unique key to the object to be saved
     * @param exampleObj example object of subject type
     * */
    fun <T : Serializable> getData(context: Context, key: String,exampleObj:T): T? =
        getData(context,key,exampleObj.javaClass)

    /**
     * Method to read serializable object from Shared Preference
     *
     * @param context Android Context
     * @param key unique key to the object to be saved
     * @param type subject class type
     * */
    fun <T : Serializable> getData(context: Context, key: String,type:Class<T>): T? {

        getSharedPreferences(context).let {
            if (it.contains(key)){
                try {
                    return it.getString(key,"")!!.deserialize().toSerializable(type)
                }catch (ex:Throwable){
                    ex.printStackTrace()
                }
            }
        }
        return null
    }

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
