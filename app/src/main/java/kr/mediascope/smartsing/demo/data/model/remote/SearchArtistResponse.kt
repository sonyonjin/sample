package kr.mediascope.smartsing.demo.data.model.remote

import com.google.gson.annotations.SerializedName

/**
 * 아티스트(가수) 검색 API 응답
 */
data class SearchArtistResponse(
    @SerializedName("artist_list") val artist_list: ArrayList<ArtistList>
)

data class ArtistList(
    @SerializedName("artist_id") val artist_id: Int,
    @SerializedName("artist_name_origin") val artist_name_origin: String,
    @SerializedName("artist_name_en") val artist_name_en: String,
    @SerializedName("match_score") val match_score: Int
)