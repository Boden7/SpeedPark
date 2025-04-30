/*
 * Author: Boden Kahn
 * Course: CSCI 403 Capstone
 * Description: This activity is what admins uses to view the parking area
 * list and add or remove parking areas
*/
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.auth.FirebaseAuth

class AdminView : AppCompatActivity() {
    private lateinit var parkingAreaDatabaseHelper: ParkingAreaDatabaseHelper
    private lateinit var adminParkingAreaAdapter: AdminParkingAreaAdapter
    private lateinit var parkingAreaRecyclerView: RecyclerView
    private lateinit var addParkingAreaButton: Button
    private lateinit var parkingAreaNameBox: EditText
    //private lateinit var auth: FirebaseAuth
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

        //auth = FirebaseAuth.getInstance()

        addParkingAreaButton = findViewById(R.id.addButton)
        parkingAreaNameBox = findViewById(R.id.parkingAreaName)
        parkingAreaDatabaseHelper = ParkingAreaDatabaseHelper.getInstance(this)

        // On click listener for the add parking area button to add a parking area and update the RV
        addParkingAreaButton.setOnClickListener{
            // Get the parking area's name from the user's input
            val parkingAreaName = parkingAreaNameBox.text.toString().trim()
            // Ensure both fields were filled out
            if (parkingAreaName.isNotEmpty()) {
                //Add the parking area to the database
                parkingAreaDatabaseHelper.addParkingArea(parkingAreaName)
                //Update the parking area list
                parkingAreas = parkingAreaDatabaseHelper.getAllParkingAreas().toMutableList()
                //Update the RV
                adminParkingAreaAdapter.updateParkingAreas(parkingAreas)
                //Clear the text boxes to prepare for the next input
                parkingAreaNameBox.text.clear()
                // Let the user know it was a success
                Toast.makeText(this, "Area added", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }

        }

        //Function to delete a parking area, remove it from the parking area list, & update the RV
        fun delete(parkingArea: ParkingArea){
            // Delete the parking area from the RV
            parkingAreaDatabaseHelper.deleteParkingArea(parkingArea.id)
            // Remove it from the parking area list
            parkingAreas.remove(parkingArea)
            // Update the RV
            adminParkingAreaAdapter.updateParkingAreas(parkingAreas)
            // Let the user know it was a success
            Toast.makeText(this, "Area removed", Toast.LENGTH_SHORT).show()
        }

        // Make the log out button
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Log out and go back to the initial page
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            //auth.signOut()
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        //Creating the RV and its adapter
        parkingAreaRecyclerView = findViewById(R.id.rvData)
        adminParkingAreaAdapter = AdminParkingAreaAdapter(this, parkingAreas, { parkingArea -> delete(parkingArea)})
        parkingAreaRecyclerView.adapter = adminParkingAreaAdapter
        parkingAreaRecyclerView.layoutManager = LinearLayoutManager(this)
        parkingAreas = parkingAreaDatabaseHelper.getAllParkingAreas().toMutableList()
        adminParkingAreaAdapter.updateParkingAreas(parkingAreas)
    }
}