package me.shakhriyor.mustplayer.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "musics")
data class Music(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "musicName")
    val musicName: String? = null,
    @ColumnInfo(name = "musicTitle")
    val musicTitle: String? = null,
    @ColumnInfo(name = "musicDescription")
    val musicDescription: String? = null
)