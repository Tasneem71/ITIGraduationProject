package com.example.graduationapp.ui.me

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.R
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.ui.cart.adapter.CartAdapter

class orderAdapter(var orderList: ArrayList<Orders>,
                   private var listener : orderAdapter.OnCancelOrderListener
) :
    RecyclerView.Adapter<orderAdapter.OrderViewHolder>() {

    fun updateList(newCategory: List<Orders>) {
        orderList.clear()
        orderList.addAll(newCategory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = OrderViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false))
    override fun getItemCount() = orderList.size
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])


    }


  inner  class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener{
        private val details = view.findViewById<TextView>(R.id.details)
        private val dateTime = view.findViewById<TextView>(R.id.date_time)
        private val id = view.findViewById<TextView>(R.id.order_title)
       private val cancel = view.findViewById<ImageView>(R.id.edit_btn)
        fun bind(order: Orders) {
            details.text =order.line_items.map { it.title }.reduce { acc, s -> acc + s }
            dateTime.text =order.total_price.toString()
            id.text =order.id

        }
      init {
          cancel.setOnClickListener(this)

      }
      override fun onClick(p0: View?) {
          when(p0){
              cancel->{
                  listener.onCancelClick(orderList[adapterPosition])
              }

          }
      }
    }

    interface OnCancelOrderListener
    {
        fun onCancelClick(item: Orders)

    }

}