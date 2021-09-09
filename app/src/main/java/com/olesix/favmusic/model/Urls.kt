package com.olesix.favmusic.model

import retrofit2.http.Query

data class Urls(@Query("before") val last: String, @Query("after") val next: String)