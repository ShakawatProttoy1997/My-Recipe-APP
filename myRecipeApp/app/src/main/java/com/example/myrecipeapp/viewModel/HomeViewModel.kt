package com.example.myrecipeapp.viewModel

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.db.RecipeDatabase
import com.example.myrecipeapp.model.Categories
import com.example.myrecipeapp.model.Category
import com.example.myrecipeapp.model.CategoryList

import com.example.myrecipeapp.model.Meal
import com.example.myrecipeapp.model.RecipeCategory
import com.example.myrecipeapp.model.RecipeList
import com.example.myrecipeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private var mealDatabase: RecipeDatabase):ViewModel() {

    private var mealLiveData = MutableLiveData<Meal>()
    private var popularRecipeLiveData = MutableLiveData<List<RecipeCategory>>()
    private var categegoryListLiveData = MutableLiveData<List<Category>>()
    private var mostLikedMealsLiveData = mealDatabase.recipeDao().getAllrecipe()
    private var bottom_sheet_recipeLiveData = MutableLiveData<Meal>()
    private val searchRecipeLiveData = MutableLiveData<List<Meal>>()
    var save_State_Random_Meal:Meal?=null

    fun getRandomMeal(){
        save_State_Random_Meal?.let {
            mealLiveData.postValue(it)
            return
        }
        RetrofitInstance.recipeApi.getRandomMeal().enqueue(object: Callback<RecipeList> {
            override fun onResponse(call: Call<RecipeList>, response: Response<RecipeList>) {
                if(response.body()!=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    mealLiveData.value = randomMeal
                    save_State_Random_Meal =randomMeal
                }
                else return
            }

            override fun onFailure(call: Call<RecipeList>, t: Throwable) {
                Log.d("Home Frag", t.message.toString())
            }

        })
    }
    fun deleteRecipe(meal: Meal){
        viewModelScope.launch {
            mealDatabase.recipeDao().delete(meal)
        }
    }
    fun insertRecipe(meal: Meal){
        viewModelScope.launch {
            mealDatabase.recipeDao().upsert(meal)
        }
    }
    fun searchRecipe(searchQuery:String) = RetrofitInstance.recipeApi.searchMealByName(searchQuery)
        .enqueue(object: Callback<RecipeList>{
        override fun onResponse(call: Call<RecipeList>, response: Response<RecipeList>) {
            response.body()?.meals?.let {
                searchRecipeLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<RecipeList>, t: Throwable) {
            d("Err: ",t.message.toString())
        }

    })

    fun observeSearchRecipeLiveData():LiveData<List<Meal>> = searchRecipeLiveData

    fun getCategory(){
        RetrofitInstance.recipeApi.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categegoryListLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               Log.d("err: ",t.message.toString())
            }

        })
    }
    fun getRecipeById(id:String){
        RetrofitInstance.recipeApi.getMealById(id).enqueue(object:Callback<RecipeList>{
            override fun onResponse(call: Call<RecipeList>, response: Response<RecipeList>) {
                val recipe = response.body()?.meals?.first()
                recipe?.let {
                    bottom_sheet_recipeLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<RecipeList>, t: Throwable) {
                d("Err: ",t.message.toString())
            }

        })
    }
    fun observeMealLiveData(): LiveData<Meal> {
         return mealLiveData
    }
    fun getPopularRecipe(){
        RetrofitInstance.recipeApi.getMealsByCategory("Seafood").enqueue(object: Callback<Categories>{
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                      if(response.body()!=null) {
                          popularRecipeLiveData.value = response.body()!!.meals
                      }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                Log.d("Error: ",t.message.toString())
            }

        })
    }
    fun observerPopularRecipeLiveData():LiveData<List<RecipeCategory>>{
        return popularRecipeLiveData
    }
    fun observeCategoryListLiveData():LiveData<List<Category>>{
        return categegoryListLiveData
    }
    fun observeMostLikedRecipeLiveData():LiveData<List<Meal>>{
        return mostLikedMealsLiveData
    }
    fun observeBottomSheetRecipeLiveData():LiveData<Meal> = bottom_sheet_recipeLiveData
}