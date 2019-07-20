package nl.zoostation.fsd.api.interceptor

import android.app.Application
import nl.zoostation.fsd.app.SystemUtils
import nl.zoostation.fsd.exception.NetworkUnavailableException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkAvailabilityInterceptor(
    private val application: Application
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (SystemUtils.isNetworkConnected(application)) {
            throw NetworkUnavailableException()
        }
        return chain.proceed(chain.request())
    }
}