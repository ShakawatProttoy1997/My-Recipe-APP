package com.example.myrecipeapp.fragments.bottom_sheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myrecipeapp.MainActivity
import com.example.myrecipeapp.R
import com.example.myrecipeapp.activity.RecipeActivity
import com.example.myrecipeapp.databinding.FragmentRecipeBottomSheetBinding
import com.example.myrecipeapp.fragments.HomeFragment
import com.example.myrecipeapp.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


const val RECIPE_ID = "param1"



class RecipeBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding:FragmentRecipeBottomSheetBinding
    private var recipeID: String? = null
    private var recipeName:String? = null
    private var recipeThumb:String? = null
    private lateinit var homeViewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeID = it.getString(RECIPE_ID)

        }
        homeViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeID?.let {
            homeViewModel.getRecipeById(it) }
        observeBottomSheetRecipe()
        onBottomsheetDialogClick()
    }

    private fun onBottomsheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
                if(recipeName != null && recipeThumb != null){
                    val intent = Intent(activity,RecipeActivity::class.java)
                    intent.apply {
                        putExtra(HomeFragment.Recipe_Id,recipeID)
                        putExtra(HomeFragment.Recipe_Name,recipeName)
                        putExtra(HomeFragment.Recipe_Thumb,recipeThumb)
                    }
                    startActivity(intent)
                }
        }
    }


    private fun observeBottomSheetRecipe() {
        homeViewModel.observeBottomSheetRecipeLiveData().observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it.strMealThumb).into(binding.bottomSheetImage)
            binding.bottomSheetArea.text = it.strArea
            binding.bottomSheetCategory.text = it.strCategory
            binding.bottomSheetRecipeName.text = it.strMeal
            recipeName  = it.strMeal
            recipeThumb = it.strMealThumb
        })
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String) =
            RecipeBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(RECIPE_ID, param1)

                }
            }
    }
}