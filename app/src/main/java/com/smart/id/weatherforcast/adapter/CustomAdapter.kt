package com.smart.id.weatherforcast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smart.id.weatherforcast.R

class CustomAdapter(): RecyclerView.Adapter<CustomAdapter.AvailableRideClass>(){

    companion object {
        var mClickListener: CustomAdapter.AvailableRidesAdapterListener? = null
    }

    class AvailableRideClass(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.activity_main, parent, false)) {

        //private var mPickUpPointPrimary: TextView? = null


        init {

        }
        fun bindData(data: String){

//            Glide.with(itemView.context).
//            load(data.driverInformation.image_url).
//            placeholder(R.drawable.ic_place_holder_for_all)
//                .into(mImage!!)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableRideClass {
        val inflater = LayoutInflater.from(parent.context)
        return CustomAdapter.AvailableRideClass(inflater, parent)
    }

    override fun onBindViewHolder(holder: AvailableRideClass, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }
    open interface AvailableRidesAdapterListener{
        fun getPosition(index:Int)
        fun getLastRideIndex(index:Int)
    }
}