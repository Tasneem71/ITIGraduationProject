package com.example.graduationapp.ui.cart.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R


class CartAdapter(
    private var carts: List<Favorite>,
    private var listener : OnCartItemListener
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var previousPosition=0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        holder.title.text=carts[position].title
        holder.price.text=carts[position].price.toString() +" .LE"
        holder.quentity.text=carts[position].count.toString()
        Glide.with(holder.image.context).load(carts[position].image).placeholder(R.drawable.ic_search).into(holder.image)

        if (position > previousPosition) { //scrolling DOWN
            RecyclerViewAnimation.animate(holder, true)
        } else { // scrolling UP
            RecyclerViewAnimation.animate(holder, false)
        }
        previousPosition = position
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // TODO createView

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.shop_bag_item
            , parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        var image: ImageView = view.findViewById(R.id.imageItem)
        var title: TextView = view.findViewById(R.id.titile)
        var price: TextView = view.findViewById(R.id.price)
        var quentity :TextView = view.findViewById(R.id.count)

        private val addCount = view.findViewById<ImageView>(R.id.addCount)
        private val minCount = view.findViewById<ImageView>(R.id.minCount)

        init {
            addCount.setOnClickListener(this)
            minCount.setOnClickListener(this)
            image.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            when(p0){
                minCount->{
                    listener.onDecreaseCountClick(carts[adapterPosition])
                }
                addCount->{
                    listener.onIncreaseCountClick(carts[adapterPosition])
                }
                image->{
                    listener.onImageCountClick(carts[adapterPosition])
                }
            }
        }
    }
    interface OnCartItemListener
    {
        fun onIncreaseCountClick(item: Favorite)
        fun onDecreaseCountClick(item: Favorite)
        fun onImageCountClick(item: Favorite)
    }
    override fun getItemCount(): Int {
        return carts.size
    }
    fun setData(list: List<Favorite>) {
        carts = list
        notifyDataSetChanged()
    }
    fun getData() : List<Favorite>{
        return carts
    }
}
