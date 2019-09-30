package com.example.yhotels.presentation.screens.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yhotels.R
import com.example.yhotels.presentation.entities.Hotel
import kotlinx.android.synthetic.main.hotel_item_layout.view.*
import kotlin.collections.ArrayList

class MainScreenRecyclerViewAdapter(receivedItems:ArrayList<Hotel>, private val clickListener:(Hotel) -> Unit)
    :RecyclerView.Adapter<MainScreenRecyclerViewAdapter.HotelsViewHolder>(){

    private var items = receivedItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelsViewHolder {
        return HotelsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.hotel_item_layout, parent, false)
        )
    }

    fun getData():ArrayList<Hotel>{
        return items
    }

    fun setData(hotels:ArrayList<Hotel>){
        items = hotels
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holderHotels: HotelsViewHolder, position: Int) {
        holderHotels.bind(items[position],clickListener)
    }

    class HotelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(hotel:Hotel,clickListener: (Hotel) -> Unit){
            itemView.itemTitle.text = hotel.name
            itemView.itemInfoOnDistanceText.text = hotel.distance.toString()
            itemView.itemInfoOnSuites.text = hotel.suites?.size.toString()
            itemView.setOnClickListener{clickListener(hotel)}
        }
    }
}