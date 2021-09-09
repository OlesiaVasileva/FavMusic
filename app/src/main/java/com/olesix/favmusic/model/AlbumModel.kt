package com.olesix.favmusic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Albums")
data class AlbumModel(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "label") val label: String?,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "thumb") val thumb: String?
)