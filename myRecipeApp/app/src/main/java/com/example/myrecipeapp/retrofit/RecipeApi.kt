package com.example.myrecipeapp.retrofit

import com.example.myrecipeapp.model.Categories
import com.example.myrecipeapp.model.CategoryList
import com.example.myrecipeapp.model.RecipeCategory
import com.example.myrecipeapp.model.RecipeList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {


    @GET ("random.php")
    fun getRandomMeal():Call<RecipeList>

    @GET("lookup.php")
   fun getMealById(@Query("i") id:String):Call<RecipeList>
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String):Call<Categories>
    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getALLRecipeByCategory(@Query("c") categoryName:String):Call<Categories>
    @GET("search.php")
    fun searchMealByName(@Query("s") searchQuery:String):Call<RecipeList>
}