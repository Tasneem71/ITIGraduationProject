package com.example.graduationapp.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.GetProductsByCollectionIDQuery
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.graphql.GraphViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class categoryGraphAdapter (var categorys: ArrayList<GetProductsByCollectionIDQuery.Edge>,
                            var listener: OnHomeItemListener, viewModel: GraphViewModel
) :
    RecyclerView.Adapter<categoryGraphAdapter.CategoryViewHolder>() {
    private var previousPosition=0
    var viewModel: GraphViewModel = viewModel

    fun setData(newCategory: List<GetProductsByCollectionIDQuery.Edge>) {
        categorys.clear()
        categorys.addAll(newCategory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.graph_card, parent, false)
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
        private val fav = view.findViewById<ImageView>(R.id.addToFav)
        private val cart = view.findViewById<ImageView>(R.id.addTocart)

        fun bind(category: GetProductsByCollectionIDQuery.Edge) {
            Glide.with(imageView.context).load(category.node.featuredImage!!.originalSrc).placeholder(
                R.drawable.bag1).into(imageView)
            name.text =category.node.title
            price.text = category.node.variants.edges.get(0).node.price.toString()+" LE"
            if (!SharedPref.getUserStatus()){
                fav.visibility=View.GONE
                cart.visibility=View.GONE
            }
            GlobalScope.launch(Dispatchers.IO) {
                var result=viewModel.isFavorite(category.node.legacyResourceId.toString().toLong(),
                    SharedPref.getUserID().toString())
                withContext(Dispatchers.Main){
                    when(result){
                        0 -> {
                            fav.setImageResource(R.drawable.favorite)
                            fav.setTag(R.drawable.favorite)
                        }
                        1 -> {
                            fav.setImageResource(R.drawable.favorite2)
                            fav.setTag(R.drawable.favorite2)
                        }
                    }
                }

            }
            GlobalScope.launch(Dispatchers.IO) {
                var result=viewModel.isCart(category.node.legacyResourceId.toString().toLong(),
                    SharedPref.getUserID().toString())
                withContext(Dispatchers.Main){
                    when(result){
                        0 -> {
                            cart.setImageResource(R.drawable.bag1)
                            cart.setTag(R.drawable.bag1)
                        }
                        1 -> {
                            cart.setImageResource(R.drawable.bag2)
                            cart.setTag(R.drawable.bag2)
                        }
                    }
                }

            }

        }
        init {
            imageView.setOnClickListener(this)
            fav.setOnClickListener(this)
            cart.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            when(p0){
                imageView -> {
                    listener.onImageClick(categorys[adapterPosition])
                }
                fav -> {
                    if (fav.tag!=R.drawable.favorite2) {
                        listener.onFavImageClick(categorys[adapterPosition])
                        fav.setImageResource(R.drawable.favorite2)
                        fav.setTag(R.drawable.favorite2)
                    } else {
                        listener.onFavDeleImageClick(categorys[adapterPosition])
                        fav.setImageResource(R.drawable.favorite)
                        fav.setTag(R.drawable.favorite)
                    }
                }

                cart -> {
                    if (cart.tag!=R.drawable.bag2) {
                        listener.oncartImageClick(categorys[adapterPosition])
                        cart.setImageResource(R.drawable.bag2)
                        cart.setTag(R.drawable.bag2)
                    } else {
                        listener.oncartDeleImageClick(categorys[adapterPosition])
                        cart.setImageResource(R.drawable.bag1)
                        cart.setTag(R.drawable.bag1)
                    }
                }
            }
        }
    }
    interface OnHomeItemListener
    {
        fun onImageClick(item: GetProductsByCollectionIDQuery.Edge)
        fun onFavImageClick(item: GetProductsByCollectionIDQuery.Edge)
        fun onFavDeleImageClick(item: GetProductsByCollectionIDQuery.Edge)
        fun oncartImageClick(item: GetProductsByCollectionIDQuery.Edge)
        fun oncartDeleImageClick(item: GetProductsByCollectionIDQuery.Edge)
    }
}