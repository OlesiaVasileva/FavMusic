package com.olesix.favmusic.model

import com.olesix.favmusic.repository.DataSourceType

data class Response(
    val albumList: List<AlbumModel>, val page: Int,
    val dataSourceType: DataSourceType
)