package com.example.graduationapp.ui.category

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.graduationapp.R
import com.example.graduationapp.data.Products
import com.example.graduationapp.ui.productPageFeature.ProductDetails
import java.util.*

class CategoryAdapter :RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    private lateinit var data  : ArrayList<Products>
    private lateinit var context: Context
    init {
        data = ArrayList<Products>()
    }
    fun setData(data: ArrayList<Products>, context: Context){
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size?:0;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= data?.get(position)
        holder.categoryName.setText(item?.title)
        //item?.image?.let { holder.categoryImage.setImageResource(item?.images[0].src) };
        context?.let {
            Glide.with(it).load(item?.images[0].src).into(holder.categoryImage)
        }

        holder.categoryImage.setOnClickListener(View.OnClickListener {
            val intent= Intent(context, ProductDetails::class.java)
            intent.putExtra("product_id",item?.id.toString())
            Log.i("TAG", "onBindViewHolder: mohamed abdallah")
            context.startActivity(intent)
        })

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView
        var categoryImage: ImageView

        init {
            categoryName=itemView.findViewById(R.id.title)
            categoryImage=itemView.findViewById(R.id.thumbnail)

        }

    }
}