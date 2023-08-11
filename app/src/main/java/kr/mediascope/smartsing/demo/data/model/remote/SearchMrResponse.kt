package kr.mediascope.smartsing.demo.data.model.remote

import com.google.gson.annotations.SerializedName

/**
 * 곡 검색 결과 API 응답
 */
data class SearchMrResponse(
    @SerializedName("mr_list") val mr_list: ArrayList<MrItem>
)

data class MrItem(
    @SerializedName("mr_id") val mr_id: Int,
    @SerializedName("title_ko") val title_ko: String,
    @SerializedName("title_en") val title_en: String,
    @SerializedName("artist_ko") val artist_ko: String,
    @SerializedName("artist_en") val artist_en: String,
    @SerializedName("original_key") val original_key: String,
    @SerializedName("mr_har_m") val mr_har_m: String,
    @SerializedName("mr_mel_m") val mr_mel_m: String,
    @SerializedName("mr_har_w") val mr_har_w: String,
    @SerializedName("mr_mel_w") val mr_mel_w: String,
    @SerializedName("mr_drum") val mr_drum: String,
    @SerializedName("match_score") val match_score: Int,
    @SerializedName("album") val album: Album
)

data class Album(
    @SerializedName("album_name") val album_name: String,
    @SerializedName("album_name_en") val album_name_en: String,
    @SerializedName("album_cover_path") val album_cover_path: String,
    @SerializedName("album_cover_path200") val album_cover_path200: String,
    @SerializedName("album_cover_path400") val album_cover_path400: String,
    @SerializedName("album_cover_path600") val album_cover_path600: String
)