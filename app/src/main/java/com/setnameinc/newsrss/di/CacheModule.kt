package com.setnameinc.newsrss.di

import android.content.Context
import androidx.room.Room
import com.setnameinc.newsrss.cache.dao.DatabaseDao
import com.setnameinc.newsrss.cache.database.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database = Room.databaseBuilder(
        context,
        Database::class.java,
        "Database.db"
    ).allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideDatabaseDAO(database: Database):DatabaseDao = database.databaseDao()

}