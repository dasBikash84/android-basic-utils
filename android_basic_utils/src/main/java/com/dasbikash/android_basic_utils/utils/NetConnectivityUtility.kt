package com.dasbikash.android_basic_utils.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/**
 * Helper class for network state detection
 *
 * 'fun initialize(context: Context)' must be called
 * on app start to to activate network state listener
 *
 * @author Bikash Das
 */
object NetConnectivityUtility : BroadcastReceiver() {

    private var mNoInternertToastShown = false
    private var mReceiverRegistered = false
    private const val NO_INTERNET_TOAST_MESSAGE = "No internet connection!!!"
    private var mCurrentNetworkType = NETWORK_TYPE.UN_INITIALIZED

    private enum class NETWORK_TYPE {
        MOBILE, WIFI, WIMAX, ETHERNET, BLUETOOTH, DC, OTHER, UN_INITIALIZED
    }

    private fun generateNetworkAvailableBroadcast(context: Context) {
        val broadcastIntent = Intent(NETWORK_AVAILABLE_BROADCAST)
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent)
    }

    /**
     * Callback Method | Will be called on network state change
     *
     * @param enabled for enable/disable logging.
     * @param tag for setting logger tag preamble.
     * */
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null && intent.action != null &&
                intent.action!!.equals(CONNECTIVITY_CHANGE_FILTER, ignoreCase = true)) {
            refreshNetworkType(context)
        }
    }

    @Suppress("DEPRECATION")
    private fun refreshNetworkType(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                mCurrentNetworkType = when (activeNetworkInfo.type) {
                    ConnectivityManager.TYPE_WIFI -> NETWORK_TYPE.WIFI
                    ConnectivityManager.TYPE_MOBILE,
                    ConnectivityManager.TYPE_MOBILE_DUN,
                    ConnectivityManager.TYPE_MOBILE_HIPRI,
                    ConnectivityManager.TYPE_MOBILE_MMS,
                    ConnectivityManager.TYPE_MOBILE_SUPL -> NETWORK_TYPE.MOBILE
                    ConnectivityManager.TYPE_BLUETOOTH -> NETWORK_TYPE.BLUETOOTH
                    ConnectivityManager.TYPE_ETHERNET -> NETWORK_TYPE.ETHERNET
                    ConnectivityManager.TYPE_WIMAX -> NETWORK_TYPE.WIMAX
                    else -> NETWORK_TYPE.OTHER
                }
            } else {
                val network = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false ||
                        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) ?: false) {
                    mCurrentNetworkType =
                        NETWORK_TYPE.WIFI
                } else if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)  ?: false) {
                    mCurrentNetworkType =
                        NETWORK_TYPE.MOBILE
                } else if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)  ?: false) {
                    mCurrentNetworkType =
                        NETWORK_TYPE.BLUETOOTH
                } else if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)  ?: false) {
                    mCurrentNetworkType =
                        NETWORK_TYPE.ETHERNET
                } else {
                    mCurrentNetworkType =
                        NETWORK_TYPE.OTHER
                }
            }
            mNoInternertToastShown = false
            generateNetworkAvailableBroadcast(
                context
            )
        } else {
            mCurrentNetworkType =
                NETWORK_TYPE.DC
        }
        LoggerUtils.debugLog(
            "Current Network Type: ${mCurrentNetworkType.name}",
            this::class.java
        )
    }

    private val NETWORK_AVAILABLE_BROADCAST = "NetConnectivityUtility.net_available"

    /**
     * Provides intent filter for getting registered into
     * 'Network Available Broadcast Receiver'
     *
     * @return IntentFilter
     * */
    val intentFilterFor: IntentFilter
        get() = IntentFilter(NETWORK_AVAILABLE_BROADCAST)

    private val CONNECTIVITY_CHANGE_FILTER = "android.net.conn.CONNECTIVITY_CHANGE"

    private val intentFilterForConnectivityChangeBroadcastReceiver: IntentFilter
        get() = IntentFilter(CONNECTIVITY_CHANGE_FILTER)

    /**
     * To check if connected to mobile network
     *
     * @return true if connected to mobile network else false
     * */
    val isOnMobileDataNetwork: Boolean
        get() {
            return mCurrentNetworkType == NETWORK_TYPE.MOBILE
        }

    /**
     * To check if connected to Wify network
     *
     * @return true if connected to mobile network else false
     * */
    val isOnWify: Boolean
        get() {
            return mCurrentNetworkType == NETWORK_TYPE.WIFI
        }

    /**
     * To check network connectivity status
     *
     * @return true if connected else false
     * */
    val isConnected: Boolean
        get() {
            return (mCurrentNetworkType != NETWORK_TYPE.DC) &&
                    (mCurrentNetworkType != NETWORK_TYPE.UN_INITIALIZED)
        }

    /**
     * Method to initialize class. Should be called on app start up.
     *
     * @param context Android Context
     * */
    fun initialize(context: Context) {
        if (!mReceiverRegistered) {
            context.registerReceiver(this,
                intentFilterForConnectivityChangeBroadcastReceiver
            )
            mReceiverRegistered = true
        }
    }

    /**
     * Will show no internet message toast
     * if no internet and toast not shown already.
     *
     * @param context Android Context
     * */
    fun showNoInternetToast(context: Context) {
        if (!isConnected && !mNoInternertToastShown) {
            mNoInternertToastShown = true
            Toast.makeText(context,
                NO_INTERNET_TOAST_MESSAGE, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Will show no internet message toast if no internet.
     *
     * @param context Android Context
     * */
    fun showNoInternetToastAnyWay(context: Context) {
        if (!isConnected) {
            mNoInternertToastShown = true
            Toast.makeText(context,
                NO_INTERNET_TOAST_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }
}
