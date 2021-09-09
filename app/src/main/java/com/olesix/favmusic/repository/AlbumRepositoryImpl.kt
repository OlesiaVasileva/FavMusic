package com.olesix.favmusic.repository

import com.olesix.favmusic.local.AlbumDao
import com.olesix.favmusic.model.Response
import com.olesix.favmusic.network.RetrofitApi
import io.reactivex.Completable
import io.reactivex.Single

const val PAGE_SIZE = 50

class AlbumRepositoryImpl(
    private val albumService: RetrofitApi,
    private val albumDao: AlbumDao
) : AlbumRepository {

    override fun getAlbumList(page: Int): Single<Response> {
        return getAlbumListFromServer(page)
            .doOnSuccess { response -> insertAlbumList(response).subscribe() }
            .onErrorResumeNext { getAlbumListFromDB(page) }
    }

    private fun getAlbumListFromServer(page: Int): Single<Response> {
        return albumService.fetchAlbumList(page, PAGE_SIZE)
            .map { serverResponse ->
                Response(
                    serverResponse.releases,
                    serverResponse.pagination.page,
                    DataSourceType.NETWORK
                )
            }
    }

    private fun getAlbumListFromDB(page: Int): Single<Response> {
        return albumDao.getAlbumList(page, PAGE_SIZE)
            .map { albumList -> Response(albumList, albumList.size / PAGE_SIZE, DataSourceType.DB) }
    }

    private fun insertAlbumList(response: Response): Completable {
        return albumDao.insertAlbumList(response.albumList)
    }
}