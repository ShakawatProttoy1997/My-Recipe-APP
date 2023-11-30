package com.example.myrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipeapp.databinding.RecipeItemBinding
import com.example.myrecipeapp.model.Category
import com.example.myrecipeapp.model.RecipeCategory

class CategoryRecipeAdapter:RecyclerView.Adapter<CategoryRecipeAdapter.CategoryRecipeViewHolder>() {
    private var recipeList = ArrayList<RecipeCategory>()

    fun setRecipeByCategory(recipeList: List<RecipeCategory>){
        this.recipeList = recipeList as ArrayList<RecipeCategory>
        notifyDataSetChanged()
    }

    inner class CategoryRecipeViewHolder(val binding:RecipeItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecipeViewHolder {
        return CategoryRecipeViewHolder(
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryRecipeViewHolder, position: Int) {
        Glide.with(holder.itemView).load(recipeList[position].strMealThumb).into(holder.binding.imageRecipe)
        holder.binding.recipeName.text = recipeList[position].strMeal
       
    }
    override fun getItemCount(): Int {
       return recipeList.size
    }


}