package com.stjy.baselib.net.net2

import com.stjy.baselib.net.AppConfig
import com.stjy.baselib.net.net2.converterfactory.LenientGsonConverterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private var retrofit: Retrofit? = null

    @get:Synchronized
    private val restAdapter: Retrofit?
        private get() {
            if (retrofit == null) {
                val builder = OkHttpClient.Builder()
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
                val client = builder.addInterceptor { chain: Interceptor.Chain ->
                    var request:Request = chain.request()
                     var url:HttpUrl = request.url().newBuilder().build()
                    val httpBuilder = request.newBuilder().url(url)
                    httpBuilder.addHeader("X-LC-SESSION", "")
                    httpBuilder.addHeader("CJS-Pro-Session", "")
                    httpBuilder.addHeader("Content-Type", "application/json; charset=utf-8")
                    httpBuilder.addHeader("App-Type", "android")
                    httpBuilder.addHeader("Version-Number", "5.2.0")
                    request = httpBuilder.build()
                    chain.proceed(request)
                }.build()
                retrofit = Retrofit.Builder()
                        .addConverterFactory(LenientGsonConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(AppConfig.BASE_URL).build()
            }
            return retrofit
        }

    fun <T> create(service: Class<T>?): T {
        return restAdapter!!.create(service)
    }
}