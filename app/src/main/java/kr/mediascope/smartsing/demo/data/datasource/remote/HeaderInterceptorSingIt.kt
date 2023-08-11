package kr.mediascope.smartsing.demo.data.datasource.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptorSingIt constructor(private val userKey: String?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("service-key", "ebfb34de34222f66141207d7b3c13e39")
                .addHeader("User-Agent", "Android/Bemily")

        userKey?.let {
            request.addHeader("user-key", it)
        }
        return chain.proceed(request.build())
    }
}