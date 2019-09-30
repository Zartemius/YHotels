package com.example.yhotels.presentation.screens.mainscreen

import androidx.recyclerview.widget.DiffUtil
import com.example.yhotels.presentation.entities.Hotel

class HotelDiffUtilCallBack(receivedOldList:List<Hotel>,receivedNewList:List<Hotel>):DiffUtil.Callback() {

    private val oldList:List<Hotel> = receivedOldList
    private val newList:List<Hotel> = receivedNewList

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHotel = oldList[oldItemPosition]
        val newHotel = newList[newItemPosition]
        return oldHotel.id == newHotel.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHotel = oldList[oldItemPosition]
        val newHotel = newList[newItemPosition]
        return oldHotel.name!!.equals(newHotel.name)
                && oldHotel.address!!.equals(newHotel.address)
    }
}