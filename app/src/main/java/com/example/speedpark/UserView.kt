// Author: Boden Kahn
// Course: CSCI 403 Capstone
// Description: This activity is what the user uses to view the parking area
// list and calculate/view the number of spaces for the desired areas
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserView : AppCompatActivity(), UserParkingAreaAdapter.OnItemClickListener {
    private lateinit var parkingAreaDatabaseHelper: ParkingAreaDatabaseHelper
    private lateinit var userParkingAreaAdapter: UserParkingAreaAdapter
    private lateinit var userParkingAreaRecyclerView: RecyclerView
    private var parkingAreas = mutableListOf<ParkingArea>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.userView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        parkingAreaDatabaseHelper = ParkingAreaDatabaseHelper.getInstance(this)

        // Create the log out button
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

        //Create the RV and its adapter
        userParkingAreaRecyclerView = findViewById(R.id.rvData)
        userParkingAreaAdapter = UserParkingAreaAdapter(this, parkingAreas, this)
        userParkingAreaRecyclerView.adapter = userParkingAreaAdapter
        userParkingAreaRecyclerView.layoutManager = LinearLayoutManager(this)
        parkingAreas = parkingAreaDatabaseHelper.getAllParkingAreas().toMutableList()
        userParkingAreaAdapter.updateParkingAreas(parkingAreas)
    }

    override fun onItemClick(parkingArea: ParkingArea) {
        // Handle the click event (e.g., show a Toast or start a new Activity)
        Toast.makeText(this, "Clicked: ${parkingArea.name}", Toast.LENGTH_SHORT).show()
        val myIntent = Intent(this, ParkingAreaDetailsActivity::class.java)
        // get number of available spots and put it as extra

        val numberOfSpaces = 5 // temporary
        myIntent.putExtra("NAME", parkingArea.name)
        myIntent.putExtra("NUMSPACES", numberOfSpaces)
        startActivity(myIntent)
    }
}