package com.example.intelliguess

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converter {
    //Convert the map to a string using @TypeConverter annotation
    @TypeConverter
    fun fromMutableMap(value: MutableMap<String, String>?): String? {
        return Gson().toJson(value)
    }

    //Convert the string to a map using @TypeConverter annotation
    @TypeConverter
    fun toMutableMap(value: String?): MutableMap<String, String>? {
        val mapType = object : TypeToken<MutableMap<String, String>>() {}.type
        return Gson().fromJson(value, mapType)
    }
}