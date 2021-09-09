package com.olesix.favmusic.repository

import com.olesix.favmusic.model.Response
import io.reactivex.Single

interface AlbumRepository {

    fun getAlbumList(page: Int): Single<Response>
}