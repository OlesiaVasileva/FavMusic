package com.olesix.favmusic.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.olesix.favmusic.model.AlbumModel
import io.reactivex.disposables.CompositeDisposable

class AlbumPagingDataSourceBuilderImpl(
    private val albumsPagingDataSourceFactory: AlbumsPagingDataSourceFactory
) : AlbumPagingDataSourceBuilder {

    override val albumsDataSourceLiveData: LiveData<State> =
        Transformations.switchMap(
            albumsPagingDataSourceFactory.albumsDataSourceLiveData, AlbumsPagingDataSource::state
        )

    override fun createLiveData(
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<AlbumModel>> {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
        compositeDisposable.add(albumsPagingDataSourceFactory)
        return LivePagedListBuilder(albumsPagingDataSourceFactory, config).build()
    }
}