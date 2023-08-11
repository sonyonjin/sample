package kr.mediascope.smartsing.demo.data.datasource.remote

import kr.mediascope.smartsing.demo.data.model.remote.SearchArtistResponse
import kr.mediascope.smartsing.demo.data.model.remote.SearchMrResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 싱잇 서비스 API
 */
interface SingItService {

    @GET("/search/artist")
    suspend fun searchArtist(
        @Query("keyword") keyword: String,
        @Query("index") index: Int,
        @Query("perpage") perpage: Int
    ): SearchArtistResponse

    @GET("/search/mr")
    suspend fun searchMr(
        @Query("keyword") keyword: String,
        @Query("index") index: Int,
        @Query("perpage") perpage: Int
    ): SearchMrResponse

}