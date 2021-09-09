package com.olesix.favmusic.network

import com.olesix.favmusic.model.ReleasesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("releases")
    fun fetchAlbumList(
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    )
            : Single<ReleasesResponse>
}