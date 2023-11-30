package com.example.myrecipeapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnytoString(attribute:Any?):String{
        if(attribute == null) return ""
        return attribute as String
    }
    @TypeConverter
    fun StringtoAny(attribute: String?):Any{
        if(attribute == null) return ""
        return attribute
    }
}