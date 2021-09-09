package com.olesix.favmusic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.olesix.favmusic.model.AlbumModel
import com.olesix.favmusic.repository.AlbumPagingDataSourceBuilder
import com.olesix.favmusic.repository.State

import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.*

class AlbumViewModelTest {

    private val liveData = MutableLiveData<PagedList<AlbumModel>>()
    private val albumPagingDataSourceBuilder: AlbumPagingDataSourceBuilder = mock {
        on { createLiveData(any()) } doReturn liveData
    }
    private val subject = AlbumViewModel(albumPagingDataSourceBuilder)

    @Test
    fun getState_returns_loading() {
        whenever(albumPagingDataSourceBuilder.albumsDataSourceLiveData) doReturn MutableLiveData(
            State.LOADING
        )

        val state = subject.getState()
        assertEquals(State.LOADING, state.value)
    }

    @Test
    fun getState_returns_done() {
        whenever(albumPagingDataSourceBuilder.albumsDataSourceLiveData) doReturn MutableLiveData(
            State.DONE
        )

        val state = subject.getState()
        assertEquals(State.DONE, state.value)
    }

    @Test
    fun getState_returns_error() {
        whenever(albumPagingDataSourceBuilder.albumsDataSourceLiveData) doReturn MutableLiveData(
            State.ERROR
        )

        val state = subject.getState()
        assertEquals(State.ERROR, state.value)
    }
}