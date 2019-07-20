package nl.zoostation.fsd.app

import android.content.Context
import android.net.ConnectivityManager

object SystemUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo?.isConnected ?: false
    }
}