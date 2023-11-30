package com.example.myrecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myrecipeapp.db.RecipeDatabase
import com.example.myrecipeapp.viewModel.HomeViewModel
import com.example.myrecipeapp.viewModel.HomeViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel:HomeViewModel by lazy{
        var recipeDatabase = RecipeDatabase.getDbInstance(this)
        var homeViewModelProviderFactory = HomeViewModelProviderFactory(recipeDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = Navigation.findNavController(this,R.id.hostFrag)
        NavigationUI.setupWithNavController(bottomNav,navController)

    }
}