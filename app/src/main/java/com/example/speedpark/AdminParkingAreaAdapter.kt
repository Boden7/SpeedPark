/*
 * Author: Boden Kahn
 * Course: CSCI 403 Capstone
 * Description: This is the adapter for the admin page that displays the name
 * of the parking areas and allows admins to delete them or add new ones.
*/
package com.example.speedpark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminParkingAreaAdapter(private val context: Context, private var parkingAreas: List<ParkingArea>, private var onDeleteClick: (ParkingArea) -> Unit) : RecyclerView.Adapter<AdminParkingAreaAdapter.ParkingAreaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingAreaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.admin_parking_area_item, parent, false)
        return ParkingAreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParkingAreaViewHolder, position: Int) {
        val parkingArea = parkingAreas[position]
        holder.bind(parkingArea)
    }

    override fun getItemCount(): Int = parkingAreas.size

    // Used to update the rv when the admin updates an entry
    fun updateParkingAreas(newParkingAreas: List<ParkingArea>) {
        parkingAreas = newParkingAreas
        notifyDataSetChanged()
    }

    inner class ParkingAreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Create text box and delete button
        private val parkingAreaTitle: TextView = itemView.findViewById(R.id.parkingAreaTitle)
        private val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        fun bind(parkingArea: ParkingArea) {
            // Set the value of the text box to the parking area name
            parkingAreaTitle.text = parkingArea.name

            // Set the listener to detect delete button clicks and delete the item
            deleteButton.setOnClickListener {
                onDeleteClick(parkingArea)
            }
        }
    }
}