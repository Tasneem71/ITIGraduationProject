package com.example.graduationapp.ui.me

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.R
import com.example.graduationapp.ui.productPageFeature.ProductDetails

class MeAdapter(var wishList: ArrayList<Favorite>) :
    RecyclerView.Adapter<MeAdapter.CategoryViewHolder>() {

    fun updateList(newCategory: List<Favorite>) {

        wishList.clear()
        wishList= newCategory as ArrayList<Favorite>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false))
    override fun getItemCount() = wishList.size
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(wishList[position])


    }


    class CategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.title)
        private val price = view.findViewById<TextView>(R.id.price)
        private val imageView = view.findViewById<ImageView>(R.id.thumbnail)
        fun bind(category: Favorite) {
            Glide.with(imageView.context).load(category.image).placeholder(R.drawable.ic_search).into(imageView)
            name.text =category.title
            imageView.setOnClickListener(View.OnClickListener {
                val intent= Intent(it.context, ProductDetails::class.java)
                intent.putExtra("product_id",category.id.toString())
                Log.i("TAG", "onBindViewHolder: mohamed abdallah")
                it.context.startActivity(intent)
            })

        }
    }
}