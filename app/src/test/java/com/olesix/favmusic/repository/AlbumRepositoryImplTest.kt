package com.olesix.favmusic.repository

import com.olesix.favmusic.local.AlbumDao
import com.olesix.favmusic.model.AlbumModel
import com.olesix.favmusic.model.Pagination
import com.olesix.favmusic.model.ReleasesResponse
import com.olesix.favmusic.model.Urls
import com.olesix.favmusic.network.RetrofitApi
import io.reactivex.Completable
import io.reactivex.Single

import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.*
import java.io.IOException

class AlbumRepositoryImplTest {


    private val albumService: RetrofitApi = mock { }
    private val albumDao: AlbumDao = mock {
        on { insertAlbumList(any()) } doReturn Completable.complete()
    }

    private val subject = AlbumRepositoryImpl(
        albumService, albumDao
    )

    @Test
    fun getAlbumList_returnsFromNetwork() {
        val releasesResponse = ReleasesResponse(
            pagination = Pagination(
                page = 1,
                pages = 50,
                perPage = 50,
                items = 250,
                urls = Urls(
                    next = "next",
                    last = "last"
                )
            ),
            releases = listOf(createAlbumModel())
        )
        whenever(albumService.fetchAlbumList(any(), any())) doReturn Single.just(releasesResponse)

        subject.getAlbumList(1)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { response ->
                assertEquals(releasesResponse.releases, response.albumList)
                assertEquals(DataSourceType.NETWORK, response.dataSourceType)
                assertEquals(releasesResponse.pagination.page, response.page)
                true
            }
        verify(albumService).fetchAlbumList(any(), any())
        verify(albumDao).insertAlbumList(any())
        verify(albumDao, never()).getAlbumList(any(), any())
    }

    @Test
    fun getAlbumList_returnsFromDb() {
        val releases = listOf(createAlbumModel())
        whenever(albumService.fetchAlbumList(any(), any())) doReturn Single.error(IOException())
        whenever(albumDao.getAlbumList(any(), any())) doReturn Single.just(releases)

        subject.getAlbumList(1)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { response ->
                assertEquals(releases, response.albumList)
                assertEquals(DataSourceType.DB, response.dataSourceType)
                assertEquals(0, response.page)
                true
            }

        verify(albumService).fetchAlbumList(any(), any())
        verify(albumDao, never()).insertAlbumList(any())
        verify(albumDao).getAlbumList(any(), any())
    }

    private fun createAlbumModel(): AlbumModel {
        return AlbumModel(
            id = 134531L,
            title = "title",
            label = "label",
            year = 2012,
            thumb = "thumb"
        )
    }
}