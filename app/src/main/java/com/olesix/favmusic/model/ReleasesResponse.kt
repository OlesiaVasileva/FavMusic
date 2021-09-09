package com.olesix.favmusic.model

data class ReleasesResponse(
    val pagination: Pagination,
    val releases: List<AlbumModel>
)
