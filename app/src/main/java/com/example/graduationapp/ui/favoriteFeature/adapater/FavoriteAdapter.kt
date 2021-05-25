package com.example.graduationapp.ui.favoriteFeature.adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.R

class FavoriteAdapter(
    private var favorites: List<Favorite>,
    private var listener : OnEditFavoriteListener
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        holder.title.text=favorites[position].title
        holder.price.text=favorites[position].price.toString()
//        context?.let {
//            Glide.with(it).load(favorites[position].image).into(holder.image)
//        }
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
        }
        override fun onClick(p0: View?) {
            when(p0){
                addTocart->{
                    listener.onAddToCartClick(favorites[adapterPosition])
                }
                removeIcon->{
                    listener.onRemoveFavoriteClick(favorites[adapterPosition])
                }
            }
        }
    }
    interface OnEditFavoriteListener
    {
        fun onRemoveFavoriteClick(item: Favorite)
        fun onAddToCartClick(item: Favorite)
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
