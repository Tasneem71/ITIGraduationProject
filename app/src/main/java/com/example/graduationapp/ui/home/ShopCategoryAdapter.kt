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
import com.example.graduationapp.ui.productPageFeature.ProductDetails

class ShopCategoryAdapter(var categorys: ArrayList<Products>) :
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


    class CategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.title)
        private val price = view.findViewById<TextView>(R.id.price)
        private val imageView = view.findViewById<ImageView>(R.id.thumbnail)
        //private val addCart = view.findViewById<ImageView>(R.id.add_card)
        fun bind(category: Products) {
            Glide.with(imageView.context).load(category.images[0].src).placeholder(R.drawable.ic_search).into(imageView)

            //imageView.setImageResource(R.drawable.ic_search)
            //addCart.setImageResource(R.drawable.ic_cart)

            name.text =category.title
            //price.text ="18 LE"
                imageView.setOnClickListener(View.OnClickListener {
                val intent= Intent(it.context, ProductDetails::class.java)
                intent.putExtra("product_id",category.id.toString())
                Log.i("TAG", "onBindViewHolder: mohamed abdallah")
                it.context.startActivity(intent)
            })


        }
    }
}