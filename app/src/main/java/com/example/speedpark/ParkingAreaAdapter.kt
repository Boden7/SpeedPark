//Author: Boden Kahn
//Course: CSCI 403 Capstone
package com.example.speedpark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParkingAreaAdapter(private val context: Context, private var parkingAreas: List<ParkingArea>, private var onDeleteClick: (ParkingArea) -> Unit) : RecyclerView.Adapter<ParkingAreaAdapter.ParkingAreaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingAreaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.parking_area_item, parent, false)
        return ParkingAreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParkingAreaViewHolder, position: Int) {
        val parkingArea = parkingAreas[position]
        holder.bind(parkingArea)
    }

    override fun getItemCount(): Int = parkingAreas.size

    // Use when the admin updates an entry
    fun updateParkingAreas(newParkingAreas: List<ParkingArea>) {
        parkingAreas = newParkingAreas
        notifyDataSetChanged()
    }

    inner class ParkingAreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parkingAreaTitle: TextView = itemView.findViewById(R.id.parkingAreaTitle)
        private val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        fun bind(parkingArea: ParkingArea) {
            parkingAreaTitle.text = parkingArea.name

            deleteButton.setOnClickListener {
                onDeleteClick(parkingArea)
            }
        }
    }
}