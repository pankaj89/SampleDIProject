package com.master.samplediproject.ws

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.master.samplediproject.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WSModule {

    @Singleton
    @Provides
    fun provideOkHttp(headerInterceptor: HeaderInterceptor?): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        builder.connectTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
        builder.readTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
        if (headerInterceptor != null)
            builder.addInterceptor(headerInterceptor)

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideWebServiceRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.client(okHttpClient)
            .baseUrl(WebService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideWSApiService(restAdapter: Retrofit): WebService {
        return restAdapter.create<WebService>(WebService::class.java)
    }
}


abstract class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder1 = original.newBuilder()
        for (stringStringEntry in getHeaders().entries) {
            builder1.header(stringStringEntry.key, stringStringEntry.value)
        }
        builder1.method(original.method, original.body)
        val request = builder1.build()
        return chain.proceed(request)
    }

    abstract fun getHeaders(): Map<String, String>
}