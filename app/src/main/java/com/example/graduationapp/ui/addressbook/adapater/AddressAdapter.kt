package com.example.graduationapp.ui.addressbook.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.ui.favoriteFeature.adapater.FavoriteAdapter

class AddressAdapter(
    private var addressList: List<Addresse>,
    private var listener : OnClickAddressListener
) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var previousPosition=0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        holder.name.text=addressList[position].name
        holder.phone.text=addressList[position].phone
        holder.city.text=addressList[position].city
        holder.country.text=addressList[position].country
        holder.address.text=addressList[position].address1
        when(addressList[position].default){
            true -> holder.checkbox.isChecked=true
            false -> holder.checkbox.isChecked=false
        }

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
        val view = layoutInflater.inflate(R.layout.address_item
            , parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        var deleteAddress: ImageView = view.findViewById(R.id.address_delete)
        var name: TextView = view.findViewById(R.id.address_name)
        var phone: TextView = view.findViewById(R.id.address_phone)
        var city: TextView = view.findViewById(R.id.address_city)
        var country: TextView = view.findViewById(R.id.address_country)
        var address: TextView = view.findViewById(R.id.address_address)
        var checkbox: CheckBox = view.findViewById(R.id.address_check_box)

        init {
            deleteAddress.setOnClickListener(this)
            name.setOnClickListener(this)
            address.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            when(p0){
                deleteAddress->{
                    listener.onDeleteAddressClick(addressList[adapterPosition])
                }
                name->{
                    listener.onNameAddressClick(addressList[adapterPosition])
                }
                address->{
                    listener.onAddressClick(addressList[adapterPosition])
                }
                checkbox->{
                    listener.onCheckBoxChange(addressList[adapterPosition])
                }
            }
        }
    }
    interface OnClickAddressListener
    {
        fun onDeleteAddressClick(item: Addresse)
        fun onNameAddressClick(item: Addresse)
        fun onAddressClick(item: Addresse)
        fun onCheckBoxChange(item: Addresse)
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    fun setData(list: List<Addresse>) {
        addressList = list
        notifyDataSetChanged()

    }

    fun getData() : List<Addresse>{
        return addressList
    }
}
