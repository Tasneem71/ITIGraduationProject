package com.example.graduationapp.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationapp.R
import com.example.graduationapp.ui.home.Category

class OrderAdapter(var itemsList: ArrayList<Category>) :
    RecyclerView.Adapter<OrderAdapter.BagViewHolder>() {
    fun updateShopBag(bagItems: List<Category>) {
        itemsList.clear()
        itemsList.addAll(bagItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = BagViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.shop_bag_item, parent, false)
    )
    override fun getItemCount() = itemsList.size
    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }
    class BagViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.title)
        private val price = view.findViewById<TextView>(R.id.price)
        private val count = view.findViewById<TextView>(R.id.count)
        private val size = view.findViewById<TextView>(R.id.size)

        private val image = view.findViewById<ImageView>(R.id.imageItem)
        private val addCount = view.findViewById<ImageView>(R.id.addCount)
        private val favorite = view.findViewById<ImageView>(R.id.favorite)
        private val minCount = view.findViewById<ImageView>(R.id.minCount)
        fun bind(category: Category) {
            addCount.setImageResource(R.drawable.ic_add)
            favorite.setImageResource(R.drawable.ic_favorite)
            minCount.setImageResource(R.drawable.ic_remove)

            image.setImageResource(R.drawable.onlineshopping)
            name.text =category.categoryName
            price.text ="99.99 $"
            count.text = "2"
            size.text="M"

        }
    }
}