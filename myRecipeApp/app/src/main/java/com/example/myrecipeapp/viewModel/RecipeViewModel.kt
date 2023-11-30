package com.example.myrecipeapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.db.RecipeDatabase
import com.example.myrecipeapp.model.Meal
import com.example.myrecipeapp.model.RecipeList
import com.example.myrecipeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(var recipeDatabase:RecipeDatabase):ViewModel() {
    private val MealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetailsById(id: String) {
        RetrofitInstance.recipeApi.getMealById(id).enqueue(object:Callback<RecipeList> {
            override fun onResponse(call: Call<RecipeList>, response: Response<RecipeList>) {
                if(response.body()!=null){
                    MealDetailsLiveData.value = response.body()!!.meals[0]

                }
                else return
            }

            override fun onFailure(call: Call<RecipeList>, t: Throwable) {
               Log.d("Recipe Activity",t.message.toString())
            }


        })

    }
    fun observeMealDetailsLiveData(): LiveData<Meal>{
        return MealDetailsLiveData
    }


    }

