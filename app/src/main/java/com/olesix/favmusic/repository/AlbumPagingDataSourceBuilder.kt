package com.olesix.favmusic.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.olesix.favmusic.model.AlbumModel
import io.reactivex.disposables.CompositeDisposable

interface AlbumPagingDataSourceBuilder {

    val albumsDataSourceLiveData: LiveData<State>

    fun createLiveData(compositeDisposable: CompositeDisposable): LiveData<PagedList<AlbumModel>>
}
