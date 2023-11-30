package com.example.myrecipeapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myrecipeapp.adapters.CategoryRecipeAdapter
import com.example.myrecipeapp.databinding.ActivityCategoryRecipeBinding
import com.example.myrecipeapp.fragments.HomeFragment
import com.example.myrecipeapp.model.Category
import com.example.myrecipeapp.viewModel.CategoryViewModel
import java.util.ArrayList

class CategoryRecipeActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryRecipeBinding
    lateinit var categoryViewModel:CategoryViewModel
    lateinit var recipeAdapter: CategoryRecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryViewModel.getCategories(intent.getStringExtra(HomeFragment.Category_Name)!!)
        categoryViewModel.observeCategories().observe(this, Observer {mealsList->
            binding.categoryCount.text = mealsList.size.toString()
            recipeAdapter.setRecipeByCategory(mealsList)
        })
    }

    private fun setUpRecyclerView() {
        recipeAdapter = CategoryRecipeAdapter()
        binding.rvRecipe.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = recipeAdapter
        }
    }

}