package com.data.local.converters

import androidx.room.TypeConverter
import com.data.local.model.LocalUser
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromList(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromLocalUser(value: LocalUser): String = Gson().toJson(value)

    @TypeConverter
    fun toLocalUser(value: String): LocalUser = Gson().fromJson(value, LocalUser::class.java)
}