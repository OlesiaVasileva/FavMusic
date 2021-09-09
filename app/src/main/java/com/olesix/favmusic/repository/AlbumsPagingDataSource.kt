package com.olesix.favmusic.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.olesix.favmusic.model.AlbumModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AlbumsPagingDataSource(
    private val repository: AlbumRepository,
    private val compositeDisposable: CompositeDisposable
) :
    PageKeyedDataSource<Int, AlbumModel>() {

    var state: MutableLiveData<State> = MutableLiveData()

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AlbumModel>
    ) {
        compositeDisposable.add(
            repository.getAlbumList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        callback.onResult(response.albumList, null, response.page + 1)
                        if (response.dataSourceType == DataSourceType.DB) {
                            updateState(State.ERROR)
                        }
                    },
                    {
                        updateState(State.ERROR)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AlbumModel>) {
        compositeDisposable.add(
            repository.getAlbumList(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(
                            response.albumList,
                            params.key + 1
                        )
                    },
                    {
                        updateState(State.ERROR)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AlbumModel>) {
    }
}