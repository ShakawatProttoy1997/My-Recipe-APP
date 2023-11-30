package com.example.myrecipeapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myrecipeapp.R
import com.example.myrecipeapp.databinding.ActivityRecipeBinding
import com.example.myrecipeapp.db.RecipeDatabase
import com.example.myrecipeapp.fragments.HomeFragment
import com.example.myrecipeapp.model.Meal
import com.example.myrecipeapp.viewModel.HomeViewModel
import com.example.myrecipeapp.viewModel.RecipeViewModel
import com.example.myrecipeapp.viewModel.RecipeViewModelProviderFactory

class RecipeActivity : AppCompatActivity() {
    var recipeDatabase = RecipeDatabase.getDbInstance(this)
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding:ActivityRecipeBinding
    private lateinit var recipeVm:RecipeViewModel
    private var homeVm = HomeViewModel(recipeDatabase)
    private lateinit var youtube:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var recipeDatabase = RecipeDatabase.getDbInstance(this)
        var recipeViewModelProviderFactory = RecipeViewModelProviderFactory(recipeDatabase)
        recipeVm = ViewModelProvider(this,recipeViewModelProviderFactory)[RecipeViewModel::class.java]
        loadingState()
        getMeal_from_Intent()
        setInformation()
        recipeVm.getMealDetailsById(mealId)
        observerRecipeDetailLiveData()
        onYoutubeClick()
       onMostLikedRecipeClick()
    }

    private fun onMostLikedRecipeClick() {
        binding.btnSave.setOnClickListener{
            recipeToSave?.let {
                homeVm.insertRecipe(it)
                Toast.makeText(this,"recipe saved successfully..",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtube))
            startActivity(intent)
        }
    }
     private var recipeToSave:Meal?=null
    private fun observerRecipeDetailLiveData() {
        recipeVm.observeMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseState()
                val recipe = value
                recipeToSave = recipe
                binding.tvCategoryInfo.text = "category: ${recipe!!.strCategory}"
                binding.tvArea.text = "Area: ${recipe.strArea}"
                binding.tvInstructions.text = recipe.strInstructions
                youtube = recipe.strYoutube.toString()
            }
        })
    }

    private fun setInformation() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(androidx.constraintlayout.widget.R.color.accent_material_light))
    }

    private fun getMeal_from_Intent() {
         val intent = intent
        mealId = intent.getStringExtra(HomeFragment.Recipe_Id)!!.toString()
        mealName = intent.getStringExtra(HomeFragment.Recipe_Name)!!.toString()
        mealThumb = intent.getStringExtra(HomeFragment.Recipe_Thumb)!!.toString()
    }
     private fun loadingState(){
         binding.progressBar.visibility = View.VISIBLE
         binding.btnSave.visibility = View.INVISIBLE
         binding.tvInstructions.visibility = View.INVISIBLE
         binding.tvArea.visibility = View.INVISIBLE
         binding.tvCategoryInfo.visibility = View.INVISIBLE
         binding.imgYoutube.visibility = View.INVISIBLE
     }
    private fun onResponseState(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}