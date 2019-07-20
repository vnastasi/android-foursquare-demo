package nl.zoostation.fsd.api.interceptor

import nl.zoostation.fsd.api.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response

class VersionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val url = oldRequest.url().newBuilder()
            .addQueryParameter(ApiConstants.PARAM_VERSION, ApiConstants.VERSION_VALUE.toString())
            .build()
        val newRequest = oldRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}
