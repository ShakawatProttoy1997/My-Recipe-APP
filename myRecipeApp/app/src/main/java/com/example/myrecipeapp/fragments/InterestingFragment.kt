package com.example.myrecipeapp.fragments


import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipeapp.MainActivity
import com.example.myrecipeapp.activity.RecipeActivity

import com.example.myrecipeapp.adapters.MostLikedRecipeAdapter
import com.example.myrecipeapp.databinding.FragmentInterestingBinding
import com.example.myrecipeapp.model.Meal

import com.example.myrecipeapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class InterestingFragment : Fragment() {
   private lateinit var binding:FragmentInterestingBinding
   private lateinit var homeviewModel:HomeViewModel
   private lateinit var mostLikedRecipeAdapter: MostLikedRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeviewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInterestingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViewForMostLikedRecipe()
        observeMostLikedItemLiveData()
        onMostLikedItemClick()
        var itemTouchedHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                 val deletedMeal = mostLikedRecipeAdapter.differences.currentList[position]
                homeviewModel.deleteRecipe(deletedMeal)
                Snackbar.make(requireView(),"recipe deleted",Snackbar.LENGTH_LONG).setAction(
                    "undo",
                    View.OnClickListener {
                        homeviewModel.insertRecipe(deletedMeal)
                    }
                ).show()
            }

        }
        ItemTouchHelper(itemTouchedHelper).attachToRecyclerView(binding.rvMostLikedRecipe)
    }

    private fun onMostLikedItemClick() {
        mostLikedRecipeAdapter.onItemClick = {
            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra(HomeFragment.Recipe_Id,it.idMeal)
            intent.putExtra(HomeFragment.Recipe_Name,it.strMeal)
            intent.putExtra(HomeFragment.Recipe_Thumb,it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerViewForMostLikedRecipe() {
        mostLikedRecipeAdapter = MostLikedRecipeAdapter()
        binding.rvMostLikedRecipe.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
           adapter = mostLikedRecipeAdapter
        }
    }

    private fun observeMostLikedItemLiveData() {
        homeviewModel.observeMostLikedRecipeLiveData().observe(viewLifecycleOwner, Observer {meals->
            mostLikedRecipeAdapter.differences.submitList(meals)
        })



}

    }
