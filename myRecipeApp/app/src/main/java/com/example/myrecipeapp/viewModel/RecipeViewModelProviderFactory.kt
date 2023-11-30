package com.example.myrecipeapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myrecipeapp.db.RecipeDatabase

class RecipeViewModelProviderFactory(private var recipeDb:RecipeDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeViewModel(recipeDb) as T
    }
}