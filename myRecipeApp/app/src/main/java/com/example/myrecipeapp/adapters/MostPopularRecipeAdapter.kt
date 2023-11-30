package com.example.myrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipeapp.databinding.PopularItemsBinding
import com.example.myrecipeapp.model.Categories
import com.example.myrecipeapp.model.Meal
import com.example.myrecipeapp.model.RecipeCategory

class MostPopularRecipeAdapter():RecyclerView.Adapter<MostPopularRecipeAdapter.PopularRecipeViewHolder>(){

    private var mealsList: List<RecipeCategory> = ArrayList()
     lateinit var onItemClick:((RecipeCategory)->Unit)
      var onLongItemClick: ((RecipeCategory)->Unit)?=null
   fun setMealList(mealsList: List<RecipeCategory>) {
       this.mealsList = mealsList
       notifyDataSetChanged()
   }



    class PopularRecipeViewHolder(val binding:PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): PopularRecipeViewHolder{
        return PopularRecipeViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularRecipeViewHolder, position: Int) {

            Glide.with(holder.itemView)
                .load(mealsList[position].strMealThumb)
                .into(holder.binding.imgPopularMeal)



        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])

        }

        holder.itemView.setOnLongClickListener{
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}

//interface OnItemClick{
  //  fun onItemClick(meal:Meal)
//}

//interface OnLongItemClick{
  //  fun onItemLongClick(meal:Meal)
//}
//}