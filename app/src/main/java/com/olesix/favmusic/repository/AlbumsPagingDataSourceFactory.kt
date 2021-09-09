package com.olesix.favmusic.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.olesix.favmusic.model.AlbumModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AlbumsPagingDataSourceFactory(
    private val repository: AlbumRepository
) : DataSource.Factory<Int, AlbumModel>(), Disposable {

    val albumsDataSourceLiveData = MutableLiveData<AlbumsPagingDataSource>()

    private val compositeDisposable = CompositeDisposable()

    override fun create(): DataSource<Int, AlbumModel> {
        val albumsPagingDataSource = AlbumsPagingDataSource(repository, compositeDisposable)
        albumsDataSourceLiveData.postValue(albumsPagingDataSource)
        return albumsPagingDataSource
    }

    override fun dispose() {
        compositeDisposable.clear()
    }

    override fun isDisposed(): Boolean {
        return compositeDisposable.isDisposed
    }
}