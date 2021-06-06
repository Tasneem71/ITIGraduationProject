package com.example.graduationapp.ui.me

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationapp.R
import com.example.graduationapp.data.orders.Orders

class orderAdapter(var orderList: ArrayList<Orders>) :
    RecyclerView.Adapter<orderAdapter.OrderViewHolder>() {

    fun updateList(newCategory: List<Orders>) {

        orderList.clear()
        orderList.addAll(newCategory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = OrderViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false))
    override fun getItemCount() = orderList.size
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])


    }


    class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val details = view.findViewById<TextView>(R.id.details)
        private val dateTime = view.findViewById<TextView>(R.id.date_time)
        private val id = view.findViewById<TextView>(R.id.order_title)
        fun bind(order: Orders) {
            details.text =order.line_items.map { it.title }.reduce { acc, s -> acc + s }
            dateTime.text =order.total_price.toString()
            id.text =order.id

        }
    }
}