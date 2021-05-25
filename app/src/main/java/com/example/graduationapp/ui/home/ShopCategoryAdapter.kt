package com.example.graduationapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationapp.R
import com.example.graduationapp.data.Products
import kotlin.collections.ArrayList

class ShopCategoryAdapter(var categorys: ArrayList<Products>) :
        RecyclerView.Adapter<ShopCategoryAdapter.CategoryViewHolder>() {
    fun updateCategory(newCategory: List<Products>) {
        categorys.clear()
        categorys.addAll(newCategory)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
    )
    override fun getItemCount() = categorys.size
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categorys[position])
    }
    class CategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.title)
        private val price = view.findViewById<TextView>(R.id.price)
        private val imageView = view.findViewById<ImageView>(R.id.thumbnail)
        private val favorite = view.findViewById<ImageView>(R.id.favorite)
        private val addCart = view.findViewById<ImageView>(R.id.add_card)
        fun bind(category: Products) {
            imageView.setImageResource(R.drawable.ic_search)
            favorite.setImageResource(R.drawable.ic_favorite)
            addCart.setImageResource(R.drawable.ic_cart)

            name.text =category.title
            price.text ="18 LE"

        }
    }
}