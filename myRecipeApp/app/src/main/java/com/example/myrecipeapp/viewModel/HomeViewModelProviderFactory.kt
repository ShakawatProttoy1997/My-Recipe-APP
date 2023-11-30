package com.example.myrecipeapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myrecipeapp.db.RecipeDatabase

class HomeViewModelProviderFactory(private var recipeDb:RecipeDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(recipeDb) as T
    }
}