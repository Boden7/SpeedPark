/*
 * Author: Boden Kahn
 * Course: CSCI 403 Capstone
 * Description: This is the adapter for the user page that displays the name of
 * the parking areas and allows users to click on them.
*/
package com.example.speedpark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserParkingAreaAdapter(private val context: Context, private var parkingAreas: List<ParkingArea>, private val listener: OnItemClickListener) : RecyclerView.Adapter<UserParkingAreaAdapter.UserParkingAreaViewHolder>(){
    interface OnItemClickListener {
        fun onItemClick(parkingArea: ParkingArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserParkingAreaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_parking_area_item, parent, false)
        return UserParkingAreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserParkingAreaViewHolder, position: Int) {
        val parkingArea = parkingAreas[position]
        holder.bind(parkingArea, listener)
    }

    // Get the size of the parking area list
    override fun getItemCount(): Int = parkingAreas.size

    // Use to initially display the rv when the client views the page
    fun updateParkingAreas(newParkingAreas: List<ParkingArea>) {
        parkingAreas = newParkingAreas
        notifyDataSetChanged()
    }

    class UserParkingAreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parkingAreaName: TextView = itemView.findViewById(R.id.parkingAreaTitle)

        fun bind(parkingArea: ParkingArea, listener: OnItemClickListener) {
            parkingAreaName.text = parkingArea.name

            // If an item is clicked notify the listener and go to it's details page
            itemView.setOnClickListener {
                listener.onItemClick(parkingArea) // Notify the listener of the click event
            }
        }
    }
}