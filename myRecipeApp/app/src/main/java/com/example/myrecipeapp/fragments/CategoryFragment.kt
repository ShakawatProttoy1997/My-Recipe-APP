package com.example.myrecipeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myrecipeapp.MainActivity
import com.example.myrecipeapp.activity.CategoryRecipeActivity
import com.example.myrecipeapp.activity.RecipeActivity
import com.example.myrecipeapp.adapters.CategoriesAdapter

import com.example.myrecipeapp.adapters.CategoryRecipeAdapter
import com.example.myrecipeapp.databinding.FragmentCategoryBinding
import com.example.myrecipeapp.model.Category
import com.example.myrecipeapp.model.Meal
import com.example.myrecipeapp.viewModel.HomeViewModel


class CategoryFragment : Fragment() {
   private lateinit var binding:FragmentCategoryBinding
   private lateinit var categoriesAdapter: CategoriesAdapter
   lateinit var categoryRecipeAdapter: CategoryRecipeAdapter
   private lateinit var homeViewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
        categoryRecipeAdapter = CategoryRecipeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViewForCategory()
        observeRecipeCategories()
        onCategoryTotalRecipeClick()
       // onCategoryRecipeClick()
    }



    private fun onCategoryTotalRecipeClick() {
        categoriesAdapter.onCategoryRecipeClick = {
            val intent = Intent(activity,CategoryRecipeActivity::class.java)
            intent.putExtra(HomeFragment.Category_Name,it.strCategory)
            startActivity(intent)
        }
    }


    private fun observeRecipeCategories() {
        homeViewModel.observeCategoryListLiveData().observe(viewLifecycleOwner,object : Observer<List<Category>>{
            override fun onChanged(value: List<Category>) {

                categoriesAdapter.setCategoryList(value)

            }
        })

    }

    private fun setUpRecyclerViewForCategory() {
        categoriesAdapter = CategoriesAdapter()
        binding.categoryRV.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }
}