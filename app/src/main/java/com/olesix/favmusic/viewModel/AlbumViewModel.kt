package com.olesix.favmusic.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.olesix.favmusic.model.AlbumModel
import com.olesix.favmusic.repository.*
import io.reactivex.disposables.CompositeDisposable


class AlbumViewModel(
    private val albumPagingDataSourceBuilder: AlbumPagingDataSourceBuilder
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    var albumList: LiveData<PagedList<AlbumModel>> = albumPagingDataSourceBuilder
        .createLiveData(compositeDisposable)

    fun getState(): LiveData<State> = albumPagingDataSourceBuilder.albumsDataSourceLiveData

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
