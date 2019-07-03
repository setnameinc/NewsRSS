package com.setnameinc.newsrss.cache.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.setnameinc.newsrss.cache.converters.ModelDatabaseNewsConverter
import com.setnameinc.newsrss.cache.dao.DatabaseDao
import com.setnameinc.newsrss.entities.cache.ModelDatabaseOfNews

@androidx.room.Database(entities = [ModelDatabaseOfNews::class], version = 1)
@TypeConverters(ModelDatabaseNewsConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}
