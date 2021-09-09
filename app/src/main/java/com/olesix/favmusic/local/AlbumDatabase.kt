package com.olesix.favmusic.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olesix.favmusic.model.AlbumModel

@Database(entities = [AlbumModel::class], version = 2, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

}