package com.setnameinc.newsrss.cache.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.setnameinc.newsrss.entities.cache.ModelDatabaseModelOfNews
import com.setnameinc.newsrss.entities.cache.ModelDatabaseOfNews

class ModelDatabaseNewsConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromString(value: String): ModelDatabaseModelOfNews =
            Gson().fromJson(value, object : TypeToken<ModelDatabaseModelOfNews>() {}.type)

        @TypeConverter
        @JvmStatic
        fun toString(value: ModelDatabaseModelOfNews): String = Gson().toJson(value)

    }

}
