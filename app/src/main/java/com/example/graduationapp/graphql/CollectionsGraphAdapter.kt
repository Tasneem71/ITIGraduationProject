package com.example.graduationapp.graphql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.HomeCollectionQuery
import com.example.graduationapp.R

class CollectionsGraphAdapter(var categorys: ArrayList<HomeCollectionQuery.Edge1>,
                              var listener: OnHomeItemListener) :
        RecyclerView.Adapter<CollectionsGraphAdapter.CategoryViewHolder>() {
    private var previousPosition=0

    fun setData(newCategory: List<HomeCollectionQuery.Edge1>) {
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
        fun bind(category: HomeCollectionQuery.Edge1) {
            Glide.with(imageView.context).load(category.node.featuredImage!!.originalSrc).placeholder(R.drawable.ic_search).into(imageView)
            name.text =category.node.handle

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
        fun onImageClick(item: HomeCollectionQuery.Edge1)
    }
}