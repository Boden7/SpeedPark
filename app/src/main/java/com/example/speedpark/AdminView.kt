// Author: Boden Kahn
// Course: CSCI 403 Capstone
// Description: This activity is what admins uses to view the parking area
// list and add or remove parking areas
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
    private lateinit var parkingAreaNameBox: EditText
    private lateinit var parkingAreaURLBox: EditText
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
        parkingAreaNameBox = findViewById(R.id.parkingAreaName)
        parkingAreaURLBox = findViewById(R.id.parkingAreaURL)
        parkingAreaDatabaseHelper = ParkingAreaDatabaseHelper.getInstance(this)

        // On click listener for the add parking area button to add a parking area and update the RV
        addParkingAreaButton.setOnClickListener{
            // Get the parking area's name from the user's input
            val parkingAreaName = parkingAreaNameBox.text.toString().trim()
            val parkingAreaURL = parkingAreaURLBox.text.toString().trim()
            // Ensure both fields were filled out
            if (parkingAreaName.isNotEmpty() && parkingAreaURL.isNotEmpty()) {
                //Add the parking area to the database
                parkingAreaDatabaseHelper.addParkingArea(parkingAreaName, parkingAreaURL)
                //Update the parking area list
                parkingAreas = parkingAreaDatabaseHelper.getAllParkingAreas().toMutableList()
                //Update the RV
                parkingAreaAdapter.updateParkingAreas(parkingAreas)
                //Clear the text boxes to prepare for the next input
                parkingAreaNameBox.text.clear()
                parkingAreaURLBox.text.clear()
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

        // Make the log out button
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