package com.example.graduationapp.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationapp.R
import java.util.*

class CategoryAdapter :RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    private lateinit var data  : ArrayList<Data>
    private lateinit var context: Context
    init {
        data = ArrayList<Data>()
    }
    fun setData(data: ArrayList<Data>, context: Context){
        this.data = data
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.sub_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size?:0;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= data?.get(position)
        holder.categoryName.setText(item?.txt)
        item?.imageId?.let { holder.categoryImage.setImageResource(it) };
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView
        var categoryImage: ImageView

        init {
            categoryName=itemView.findViewById(R.id.category_name)
            categoryImage=itemView.findViewById(R.id.category_img)

        }

    }
}