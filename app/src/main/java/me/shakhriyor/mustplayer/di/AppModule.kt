package me.shakhriyor.mustplayer.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.shakhriyor.mustplayer.data.source.MusicDatabase
import me.shakhriyor.mustplayer.data.source.dao.MusicDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun appDatabase(context: Application): MusicDatabase {
        return MusicDatabase.getMusicDbInstance(context)
    }


    @Provides
    @Singleton
    fun musicsDao(appDatabase: MusicDatabase): MusicDao {
        return appDatabase.getMusicsDao()
    }

}