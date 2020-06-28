package com.stjy.baselib.net.net2;

import com.stjy.baselib.net.AppConfig;
import com.stjy.baselib.net.net2.converterfactory.LenientGsonConverterFactory;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
	private static Retrofit retrofit;

	private synchronized static Retrofit getRestAdapter() {
		if (retrofit == null) {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			builder.addInterceptor(interceptor);
			OkHttpClient client = builder.addInterceptor(chain -> {
				Request request = chain.request();
				HttpUrl url = request.url().newBuilder().build();
				Request.Builder httpBuilder = request.newBuilder().url(url);
				httpBuilder.addHeader("Content-Type", "application/json; charset=utf-8");
				httpBuilder.addHeader("App-Type", "android");
				httpBuilder.addHeader("Version-Number", "5.2.0");
				request = httpBuilder.build();
				return chain.proceed(request);
			}).build();
			retrofit = new Retrofit.Builder()
					.addConverterFactory(LenientGsonConverterFactory.create())
					.addConverterFactory(GsonConverterFactory.create())
					.client(client)
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.baseUrl(AppConfig.Companion.getBASE_URL()).build();

		}
		return retrofit;
	}

	public static <T> T create(Class<T> service) {
		return getRestAdapter().create(service);
	}
}
