package com.example.graduationapp.ui.cart


import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.R

class CartAdapter(var itemsList: ArrayList<Favorite>, var cartViewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.BagViewHolder>() {

    fun updateShopBag(bagItems: List<Favorite>) {
        itemsList.clear()
        itemsList.addAll(bagItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = BagViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.shop_bag_item, parent, false)
    )
    override fun getItemCount() = itemsList.size

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        holder.bind(itemsList[position],cartViewModel)

    }
    fun deleteItem(pos: Int) {
        val i=( itemsList[pos].price *itemsList[pos].count)
        Log.i("Menna",(itemsList[pos].price).toString()+"***count***"+(itemsList[pos].count).toString())
        val old = cartViewModel.sumOfItems.value!!
        val sum = old - i
        cartViewModel.sumOfItems.postValue(sum)
        //*******************************************
        cartViewModel.deleteFromFavorite(itemsList[pos])
        itemsList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    class BagViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.titile)
        private val price = view.findViewById<TextView>(R.id.price)
        private val count = view.findViewById<TextView>(R.id.count)
        private val size = view.findViewById<TextView>(R.id.size)
        private val image = view.findViewById<ImageView>(R.id.imageItem)
        private val addCount = view.findViewById<ImageView>(R.id.addCount)
        private val minCount = view.findViewById<ImageView>(R.id.minCount)
        var quentity = 0


        fun bind(cart: Favorite,cartViewModel: CartViewModel) {
            addCount.setImageResource(R.drawable.ic_add)
            minCount.setImageResource(R.drawable.ic_remove)
            Glide.with(image.context).load(cart.image).placeholder(R.drawable.ic_search).into(image)
            name.text =cart.title
            price.text = cart.price.toString()
            count.text =cart.count.toString()
            quentity = cart.count
            size.text="M"

            minCount.setOnClickListener(View.OnClickListener {

                if (quentity >1){
                    --quentity
                    Log.i("Menna","-------------------------"+quentity.toString())
                    count.text = quentity.toString()
                   // price.text = (cart.price * quentity).toString()
                    cartViewModel.updateCount(cart.id,quentity)
                    val i =(cart.price * 1)
                    val old = cartViewModel.sumOfItems.value!!
                    val sum = old - i
                    cartViewModel.sumOfItems.postValue(sum)

                }
            })
            addCount.setOnClickListener(View.OnClickListener {
                if (quentity >0){
                    ++quentity
                    Log.i("Menna","++++++++++"+quentity.toString())
                    count.text = quentity.toString()
                   // price.text = (cart.price * quentity).toString()
                    cartViewModel.updateCount(cart.id,quentity)
                    val i =(cart.price * 1)
                    val old = cartViewModel.sumOfItems.value!!
                    val sum = old + i
                    cartViewModel.sumOfItems.postValue(sum)
                }

            })

        }
    }
}