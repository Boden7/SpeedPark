//Author: Boden Kahn
//Course: CSCI 380
//Due: 11/15/24
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminView : AppCompatActivity() {
    private lateinit var parkingAreaDatabaseHelper: ParkingAreaDatabaseHelper
    private lateinit var parkingAreaAdapter: ParkingAreaAdapter
    private lateinit var parkingAreaRecyclerView: RecyclerView
    private lateinit var addParkingAreaButton: Button
    private lateinit var parkingAreaEditText: EditText
    private var parkingAreas = mutableListOf<ParkingArea>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addParkingAreaButton = findViewById(R.id.addButton)
        parkingAreaEditText = findViewById(R.id.editText)
        parkingAreaDatabaseHelper = ParkingAreaDatabaseHelper.getInstance(this)

        //On click listener for the add parking area button to add a parking area and update the RV
        addParkingAreaButton.setOnClickListener{
            //Get the parking area's name from the user's input
            val parkingArea = parkingAreaEditText.text.toString().trim()
            if (parkingArea.isNotEmpty()) {
                //Add the parking area to the database
                parkingAreaDatabaseHelper.addParkingArea(parkingArea)
                //Update the parking area list
                parkingAreas = parkingAreaDatabaseHelper.getAllParkingAreas().toMutableList()
                //Update the RV
                parkingAreaAdapter.updateParkingAreas(parkingAreas)
                //Clear the text to prepare for the next input
                parkingAreaEditText.text.clear()
            }
        }

        //Function to delete a parking area, remove it from the parking area list, & update the RV
        fun delete(parkingArea: ParkingArea){
            //Delete the parking area from the RV
            parkingAreaDatabaseHelper.deleteParkingArea(parkingArea.id)
            //Remove it from the parking area list
            parkingAreas.remove(parkingArea)
            //Update the RV
            parkingAreaAdapter.updateParkingAreas(parkingAreas)
        }

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

        //Creating the RV and its adapter
        parkingAreaRecyclerView = findViewById(R.id.rvData)
        parkingAreaAdapter = ParkingAreaAdapter(this, parkingAreas, {parkingArea -> delete(parkingArea)})
        parkingAreaRecyclerView.adapter = parkingAreaAdapter
        parkingAreaRecyclerView.layoutManager = LinearLayoutManager(this)
        parkingAreas = parkingAreaDatabaseHelper.getAllParkingAreas().toMutableList()
        parkingAreaAdapter.updateParkingAreas(parkingAreas)
    }
}