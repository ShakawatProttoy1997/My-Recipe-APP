package com.example.myrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipeapp.databinding.RecipeItemBinding
import com.example.myrecipeapp.model.Meal
import com.example.myrecipeapp.model.RecipeCategory

class MostLikedRecipeAdapter: RecyclerView.Adapter<MostLikedRecipeAdapter.MostLikedRecipeViewHolder>() {
    lateinit var onItemClick:((Meal)->Unit)

    inner class MostLikedRecipeViewHolder(val binding: RecipeItemBinding): RecyclerView.ViewHolder(binding.root)
     private val diffUtil = object:DiffUtil.ItemCallback<Meal>(){
         override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
             return oldItem.idMeal == newItem.idMeal
         }

         override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
             return oldItem == newItem
         }

     }
    val differences = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostLikedRecipeViewHolder {
        return MostLikedRecipeViewHolder(
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MostLikedRecipeViewHolder, position: Int) {
         val meal = differences.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imageRecipe)
        holder.binding.recipeName.text = meal.strMeal
        holder.itemView.setOnClickListener{
            onItemClick.invoke(meal)
        }

    }

    override fun getItemCount(): Int {
        return differences.currentList.size
    }


}