package com.example.myrecipeapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipeapp.MainActivity
import com.example.myrecipeapp.R
import com.example.myrecipeapp.adapters.MostLikedRecipeAdapter
import com.example.myrecipeapp.databinding.FragmentSearchBinding
import com.example.myrecipeapp.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
private lateinit var binding: FragmentSearchBinding
private lateinit var homeViewModel: HomeViewModel
private lateinit var searchRecipeRecyclerViewAdpapter:MostLikedRecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViewForSearchRecipe()
        binding.searchArrow.setOnClickListener { searchRecipe() }
        observeSearchRecipeLiveData()
        var search_job:Job?=null
        binding.searchBox.addTextChangedListener{ searchQuery->
            search_job?.cancel()
            search_job = lifecycleScope.launch{
                delay(500)
                homeViewModel.searchRecipe(searchQuery.toString())
            }

        }
    }

    private fun observeSearchRecipeLiveData() {
        homeViewModel.observeSearchRecipeLiveData().observe(viewLifecycleOwner, Observer {
            searchRecipeRecyclerViewAdpapter.differences.submitList(it)
        })
    }

    private fun searchRecipe() {
     val search_recipe = binding.searchBox.text.toString()
        if(search_recipe.isNotEmpty()){
            homeViewModel.searchRecipe(search_recipe)
        }

    }

    private fun setUpRecyclerViewForSearchRecipe() {
        searchRecipeRecyclerViewAdpapter = MostLikedRecipeAdapter()
        binding.searchedRecipeRV.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchRecipeRecyclerViewAdpapter

        }
    }

}