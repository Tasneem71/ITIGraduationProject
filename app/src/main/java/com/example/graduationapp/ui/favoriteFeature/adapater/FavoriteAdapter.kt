package com.example.graduationapp.ui.favoriteFeature.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R

class FavoriteAdapter(
    private var favorites: List<Favorite>,
    private var listener : OnEditFavoriteListener
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var previousPosition=0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        holder.title.text=favorites[position].title
        holder.price.text=favorites[position].price.toString()+" LE"
        Glide.with(holder.image.context).load(favorites[position].image).placeholder(R.drawable.bag1).into(holder.image)

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
        val view = layoutInflater.inflate(R.layout.favorite_row
            , parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        var removeIcon: ImageView = view.findViewById(R.id.favorite_remove_img)
        var addTocart: ImageView = view.findViewById(R.id.favorite_add_tocart_img)
        var image: ImageView = view.findViewById(R.id.favorite_thumbnail)
        var title: TextView = view.findViewById(R.id.favorite_title)
        var price: TextView = view.findViewById(R.id.favorite_price)

        init {
            removeIcon.setOnClickListener(this)
            addTocart.setOnClickListener(this)
            image.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            when(p0){
                addTocart->{
                    listener.onAddToCartClick(favorites[adapterPosition])
                }
                removeIcon->{
                    listener.onRemoveFavoriteClick(favorites[adapterPosition])
                }
                image->{
                    listener.onImageClick(favorites[adapterPosition])
                }
            }
        }
    }
    interface OnEditFavoriteListener
    {
        fun onRemoveFavoriteClick(item: Favorite)
        fun onAddToCartClick(item: Favorite)
        fun onImageClick(item: Favorite)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun setData(list: List<Favorite>) {
        favorites = list
        notifyDataSetChanged()

    }

    fun getData() : List<Favorite>{
        return favorites
    }
}
