package com.example.myrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipeapp.databinding.CategoryRecipeBinding
import com.example.myrecipeapp.model.Category
import com.example.myrecipeapp.model.CategoryList
import java.util.ArrayList

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categoryList = ArrayList<Category>()
    var onCategoryRecipeClick:((Category)->Unit)?=null
    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    inner class CategoryViewHolder(val binding: CategoryRecipeBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryRecipeBinding.
        inflate(LayoutInflater.from(parent.context))
        )
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).
        load(categoryList[position].strCategoryThumb).into(holder.binding.imageCategory)
        holder.binding.tvcategoryName.text = categoryList[position].strCategory
        holder.itemView.setOnClickListener{
            onCategoryRecipeClick?.invoke(categoryList[position])
        }
    }
    override fun getItemCount(): Int {
       return categoryList.size
    }


}