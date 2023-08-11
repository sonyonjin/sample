package kr.mediascope.smartsing.demo.data.datasource.remote

import kr.mediascope.smartsing.demo.data.model.remote.SearchMrResponse

/**
 * 싱잇 리모트 데이터 소스
 */
interface SingItRemoteDataSource {

    suspend fun searchMr(
        keyword: String,
        index: Int,
        perpage: Int
    ): Result<SearchMrResponse>

}
