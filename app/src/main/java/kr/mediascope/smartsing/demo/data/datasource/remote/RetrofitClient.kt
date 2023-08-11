package kr.mediascope.smartsing.demo.data.datasource.remote

import android.util.Log.d
import kr.mediascope.smartsing.demo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 클라이언트
 */
class RetrofitClient {
    fun <T> getSingItApi(service: Class<T>, userKey: String): T {
        val client: OkHttpClient

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message: String? ->
                d(
                    "SINGIT_HTTP_API",
                    message!!
                )
            }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        client = builder
            .addInterceptor(HeaderInterceptorSingIt(userKey))
            .connectTimeout(SINGIT_CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(SINGIT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(service)
    }

    companion object {
        private const val SINGIT_URL = "https://bemily.sing-it.app"

        private const val SINGIT_CONNECT_TIME_OUT = 60
        private const val SINGIT_READ_TIME_OUT = 60

        private var INSTANCE: RetrofitClient = RetrofitClient()

        @JvmStatic
        fun retrofitClient(): RetrofitClient {
            return INSTANCE
        }
    }
}