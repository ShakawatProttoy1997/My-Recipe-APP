package com.example.myrecipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipeapp.model.Meal

@Dao
interface RecipeDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun upsert(meal: Meal)
   @Delete
   suspend fun delete(meal: Meal)
   @Query("SELECT * FROM recipeInformation")
   fun getAllrecipe():LiveData<List<Meal>>
}