package com.bnvs.metaler.network

import com.bnvs.metaler.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val client: RetrofitInterface
    private const val BASE_URL = "http://metaler.kr/"

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                if (original.url.encodedPath.equals("/users/check", true)
                    || original.url.encodedPath.equals("/users/join", true)
                    || original.url.encodedPath.equals("/users/join", true)
                ) {
                    chain.proceed(original)
                } else {
                    chain.proceed(original.newBuilder().apply {
                        addHeader("Authorization", "")
                    }.build())
                }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        client = retrofit.create(
            RetrofitInterface::class.java
        )
    }
}