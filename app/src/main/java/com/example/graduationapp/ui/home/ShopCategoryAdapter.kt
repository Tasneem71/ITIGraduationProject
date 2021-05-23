package com.example.graduationapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationapp.R
import kotlin.collections.ArrayList

class ShopCategoryAdapter(var categorys: ArrayList<Category>) :
        RecyclerView.Adapter<ShopCategoryAdapter.CategoryViewHolder>() {
    fun updateCategory(newCategory: List<Category>) {
        categorys.clear()
        categorys.addAll(newCategory)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shop_category_item, parent, false)
    )
    override fun getItemCount() = categorys.size
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categorys[position])
    }
    class CategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.item_name)
        private val imageView = view.findViewById<ImageView>(R.id.logo)
        fun bind(category: Category) {
            imageView.setImageResource(R.drawable.ic_search)
            name.text =category.categoryName
        }
    }
}