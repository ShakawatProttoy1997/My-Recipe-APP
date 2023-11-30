package com.example.myrecipeapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecipeapp.model.Categories
import com.example.myrecipeapp.model.Category
import com.example.myrecipeapp.model.CategoryList
import com.example.myrecipeapp.model.RecipeCategory
import com.example.myrecipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel:ViewModel() {
    private var mealsLiveData = MutableLiveData<List<RecipeCategory>>()


    fun getCategories(categoryName: String) {
        RetrofitInstance.recipeApi.getALLRecipeByCategory(categoryName)
            .enqueue(object : Callback<Categories> {
                override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                    response.body()?.let { mealList ->
                        mealsLiveData.postValue(mealList.meals)
                    }
                }


                override fun onFailure(call: Call<Categories>, t: Throwable) {
                    Log.d("Err: ", t.message.toString())
                }

            })
    }


        fun observeCategories(): LiveData<List<RecipeCategory>> {
            return mealsLiveData
        }



}