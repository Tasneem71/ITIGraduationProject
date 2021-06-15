package com.example.graduationapp.ui.me

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationapp.R
import com.example.graduationapp.data.orders.Orders
import java.text.DateFormat
import java.text.SimpleDateFormat

class orderAdapter(
    var orderList: ArrayList<Orders>,
    private var listener: orderAdapter.OnCancelOrderListener, context: Context
) :RecyclerView.Adapter<orderAdapter.OrderViewHolder>() {

    val context=context
    fun updateList(newCategory: List<Orders>) {
        orderList.clear()
        orderList.addAll(newCategory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = OrderViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
    )
    override fun getItemCount() = orderList.size
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])


    }


  inner  class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener{
      private val seemore = view.findViewById<TextView>(R.id.details)
      private val dateTime = view.findViewById<TextView>(R.id.date_time)
      private val id = view.findViewById<TextView>(R.id.order_title)
      private val price = view.findViewById<TextView>(R.id.price)
      private val date = view.findViewById<TextView>(R.id.date)
      private val cancel = view.findViewById<ImageView>(R.id.edit_btn)
      private val orderStatus = view.findViewById<ImageView>(R.id.order_status)
        fun bind(order: Orders) {
            dateTime.text=order.financial_status
            id.text =order.line_items.get(0).name
            price.text=order.total_price.toString()+ " LE"
            date.text=order.created_at.substring(0,10)
            if(order.financial_status=="paid"){
                orderStatus.setImageResource(R.drawable.check)
                dateTime.setTextColor(ContextCompat.getColor(context, R.color.green))
            }else{
                orderStatus.setImageResource(R.drawable.exclamation)
                dateTime.setTextColor(ContextCompat.getColor(context, R.color.red))
            }

        }
      init {
          cancel.setOnClickListener(this)
          seemore.setOnClickListener(this)

      }
      override fun onClick(p0: View?) {
          when(p0){
              cancel -> {
                  listener.onCancelClick(orderList[adapterPosition])
              }
              seemore -> {
                  listener.onSeeMoreClick(orderList[adapterPosition])
              }

          }
      }
    }

    interface OnCancelOrderListener
    {
        fun onCancelClick(item: Orders)
        fun onSeeMoreClick(item: Orders)

    }

}