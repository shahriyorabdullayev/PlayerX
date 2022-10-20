package me.shakhriyor.mustplayer.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.shakhriyor.mustplayer.data.model.Music
import me.shakhriyor.mustplayer.data.source.dao.MusicDao


@Database(entities = [Music::class], version = 1)
abstract class MusicDatabase: RoomDatabase() {

    abstract fun getMusicsDao(): MusicDao

    companion object {
        private var DB_INSTANCE: MusicDatabase? = null

        fun getMusicDbInstance(context: Context): MusicDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    "MyMusics.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return DB_INSTANCE!!
        }
    }


}