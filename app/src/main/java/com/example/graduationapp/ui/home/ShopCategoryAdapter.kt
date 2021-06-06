package com.example.graduationapp.ui.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R
import com.example.graduationapp.data.Products
import com.example.graduationapp.ui.cart.adapter.CartAdapter
import com.example.graduationapp.ui.productPageFeature.ProductDetails

class ShopCategoryAdapter(var categorys: ArrayList<Products>, var listener: OnHomeItemListener) :
        RecyclerView.Adapter<ShopCategoryAdapter.CategoryViewHolder>() {
    private var previousPosition=0
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
        if (position > previousPosition) { //scrolling DOWN
            RecyclerViewAnimation.animate(holder, true)
        } else { // scrolling UP
            RecyclerViewAnimation.animate(holder, false)
        }
        previousPosition = position
    }
    inner class CategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        private val name = view.findViewById<TextView>(R.id.title)
        private val price = view.findViewById<TextView>(R.id.price)
        private val imageView = view.findViewById<ImageView>(R.id.thumbnail)
        //private val addCart = view.findViewById<ImageView>(R.id.add_card)
        fun bind(category: Products) {
            Glide.with(imageView.context).load(category.images[0].src).placeholder(R.drawable.ic_search).into(imageView)
            name.text =category.title

        }
        init {
            imageView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            when(p0){
                imageView->{
                    listener.onImageClick(categorys[adapterPosition])
                }
            }
        }
    }
    interface OnHomeItemListener
    {
        fun onImageClick(item: Products)
    }
}