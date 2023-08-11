package kr.mediascope.smartsing.demo.data.datasource.remote

import kr.mediascope.smartsing.demo.data.model.remote.SearchMrResponse

/**
 * 싱잇 리모트 데이터 소스 구현체
 */
class SingItRemoteDataSourceImpl(
    private val retrofitClient: RetrofitClient = RetrofitClient.retrofitClient()
) : SingItRemoteDataSource {

    override suspend fun searchMr(
        keyword: String,
        index: Int,
        perpage: Int
    ): Result<SearchMrResponse> {
        val singItService = retrofitClient.getSingItApi(SingItService::class.java, USER_KEY)
        return try {
            val response = singItService.searchMr(keyword, index, perpage)
            Result.Success(response)
        } catch(e: Exception) {
            Result.Error(e)
        }
    }

    companion object {
        private const val USER_KEY = "1"
    }
}