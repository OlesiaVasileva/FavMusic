package com.olesix.favmusic.local

import androidx.room.*

import com.olesix.favmusic.model.AlbumModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface AlbumDao {

    @Query("SELECT * FROM Albums LIMIT :pageSize OFFSET :pageIndex * :pageSize")
    fun getAlbumList(pageIndex: Int, pageSize: Int): Single<List<AlbumModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbumList(albumList: List<AlbumModel>): Completable

    @Delete
    fun deleteAlbums(albumList: List<AlbumModel>?)
}