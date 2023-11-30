package com.example.myrecipeapp.fragments


import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myrecipeapp.MainActivity
import com.example.myrecipeapp.R
import com.example.myrecipeapp.activity.CategoryRecipeActivity
import com.example.myrecipeapp.activity.RecipeActivity
import com.example.myrecipeapp.adapters.CategoriesAdapter
import com.example.myrecipeapp.adapters.MostPopularRecipeAdapter

import com.example.myrecipeapp.databinding.FragmentHomeBinding
import com.example.myrecipeapp.fragments.bottom_sheet.RecipeBottomSheetFragment
import com.example.myrecipeapp.model.Meal
import com.example.myrecipeapp.model.RecipeCategory

import com.example.myrecipeapp.viewModel.HomeViewModel
import java.util.ArrayList


class HomeFragment : Fragment() {
  private lateinit var binding:FragmentHomeBinding
  private lateinit var HomemvvM:HomeViewModel
  private lateinit var randomMeal: Meal
  private lateinit var popularRecipeAdapter:MostPopularRecipeAdapter
  private lateinit var categoryAdapter:CategoriesAdapter
  companion object{
    const val Recipe_Id ="com.example.myrecipeapp.fragments.idMeal"
    const val Recipe_Name = " com.example.myrecipeapp.fragments.nameMeal"
    const val Recipe_Thumb =" com.example.myrecipeapp.fragments.thumbMeal"
    const val Category_Name = "com.example.myrecipeapp.fragments.categoryName"
  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomemvvM = (activity as MainActivity).viewModel
        popularRecipeAdapter = MostPopularRecipeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularRecipeRecyclerView()
        HomemvvM.getRandomMeal()
        observeMeal()
        onRecipeClick()

        HomemvvM.getPopularRecipe()
        observePopularRecipeLiveData()
        onPopularRecipeClickListener()

        categoryRecyclerView()
        HomemvvM.getCategory()
        observeCategoryLiveData()
        onCategoryItemClick()
        onPopularRecipeLongClick()
        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_searchFragment)
        }
    }

    private fun onPopularRecipeLongClick() {
        popularRecipeAdapter.onLongItemClick = {
            val recipeBottomsheet = RecipeBottomSheetFragment.newInstance(it.idMeal)
            recipeBottomsheet.show(childFragmentManager,"recipe info")
        }
    }

    private fun onCategoryItemClick() {
        categoryAdapter.onCategoryRecipeClick = {category ->
        val intent = Intent(activity,CategoryRecipeActivity::class.java)
            intent.putExtra(Category_Name,category.strCategory)
            startActivity(intent)

        }
    }

    private fun categoryRecyclerView() {
       categoryAdapter = CategoriesAdapter()
        binding.recyclerViewCategory.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
             adapter = categoryAdapter
        }
    }

    private fun observeCategoryLiveData() {
        HomemvvM.observeCategoryListLiveData().observe(viewLifecycleOwner, Observer {categories->
                categoryAdapter.setCategoryList(categories)

        })
    }

    private fun onPopularRecipeClickListener() {
        popularRecipeAdapter.onItemClick = {meal->
            val intent = Intent(activity,RecipeActivity::class.java)
            intent.putExtra(Recipe_Id,meal.idMeal)
            intent.putExtra(Recipe_Name,meal.strMeal)
            intent.putExtra(Recipe_Thumb,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun popularRecipeRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularRecipeAdapter
        }
    }

    private fun observePopularRecipeLiveData() {
        HomemvvM.observerPopularRecipeLiveData()
            .observe(viewLifecycleOwner
            ) { mealList->
                popularRecipeAdapter.setMealList(mealsList = mealList as ArrayList<RecipeCategory>)
            }
    }

    private fun onRecipeClick() {
        binding.mealCardView.setOnClickListener{
            val intent = Intent(activity,RecipeActivity::class.java)
            intent.putExtra(Recipe_Id,randomMeal.idMeal)
            intent.putExtra(Recipe_Name,randomMeal.strMeal)
            intent.putExtra(Recipe_Thumb,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeMeal() {
        HomemvvM.observeMealLiveData().observe(viewLifecycleOwner,
            {meal->

                Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(binding.imgRandomMeal)
                this.randomMeal = meal
        })
    }

}