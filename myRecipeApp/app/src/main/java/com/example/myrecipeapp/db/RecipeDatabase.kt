package com.example.myrecipeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myrecipeapp.model.Meal

@Database(entities = [Meal::class], version = 3, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class RecipeDatabase:RoomDatabase() {

    abstract fun recipeDao():RecipeDao
    companion object{
        @Volatile
        var dbInstance:RecipeDatabase?=null
        @Synchronized
        fun getDbInstance(context: Context):RecipeDatabase{
            if(dbInstance == null){
                dbInstance = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration().build()
            }
            return dbInstance as RecipeDatabase
        }
    }
}