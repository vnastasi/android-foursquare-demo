package nl.zoostation.fsd.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import nl.zoostation.fsd.BuildConfig
import nl.zoostation.fsd.api.ApiConstants
import nl.zoostation.fsd.api.client.RawVenueApiClient
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.api.client.impl.VenueApiClientImpl
import nl.zoostation.fsd.api.interceptor.CredentialsInterceptor
import nl.zoostation.fsd.api.interceptor.NetworkAvailabilityInterceptor
import nl.zoostation.fsd.api.interceptor.VersionInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache =
        Cache(application.cacheDir, ApiConstants.CACHE_SIZE)

    @Provides
    @Singleton
    fun provideHttpClient(
        application: Application? = null,
        cache: Cache
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(ApiConstants.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(ApiConstants.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(ApiConstants.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            cache(cache)
            if (application != null) addInterceptor(NetworkAvailabilityInterceptor(application))
            addInterceptor(CredentialsInterceptor())
            addInterceptor(VersionInterceptor())
            if (BuildConfig.DEBUG) addInterceptor(createHttpLoggingInterceptor())
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideVenueApiClient(retrofit: Retrofit): VenueApiClient =
        VenueApiClientImpl(retrofit.create(RawVenueApiClient::class.java))

    private fun createHttpLoggingInterceptor(): Interceptor {
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return HttpLoggingInterceptor().apply {
            this.level = level
        }
    }
}