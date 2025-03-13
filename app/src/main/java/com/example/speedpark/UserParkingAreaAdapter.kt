//Author: Boden Kahn
//Course: CSCI 403 Capstone
package com.example.speedpark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserParkingAreaAdapter(private val context: Context, private var parkingAreas: List<ParkingArea>) : RecyclerView.Adapter<UserParkingAreaAdapter.UserParkingAreaViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserParkingAreaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_parking_area_item, parent, false)
        return UserParkingAreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserParkingAreaViewHolder, position: Int) {
        val parkingArea = parkingAreas[position]
        holder.bind(parkingArea)
    }

    override fun getItemCount(): Int = parkingAreas.size

    // Use when the admin updates an entry
    fun updateParkingAreas(newParkingAreas: List<ParkingArea>) {
        parkingAreas = newParkingAreas
        notifyDataSetChanged()
    }

    inner class UserParkingAreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parkingAreaTitle: TextView = itemView.findViewById(R.id.parkingAreaTitle)

        fun bind(parkingArea: ParkingArea) {
            parkingAreaTitle.text = parkingArea.name
        }
    }
}