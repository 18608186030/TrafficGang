package com.stjy.baselib.net.net2

import com.stjy.baselib.net.AppConfig
import com.stjy.baselib.net.net2.converterfactory.LenientGsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    @get:Synchronized
    private val restAdapter: Retrofit
        private get() {
            val builder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
            val client = builder.addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().build()
                val httpBuilder = request.newBuilder().url(url)
                //此处添加请求头
                httpBuilder.addHeader("Content-Type", "application/json; charset=utf-8")
                request = httpBuilder.build()
                chain.proceed(request)
            }.build()
            return Retrofit.Builder()
                    .addConverterFactory(LenientGsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(AppConfig.BASE_URL).build()
        }

    fun <T> create(service: Class<T>?): T {
        return restAdapter.create(service)
    }
}