/*
 * Author: Boden Kahn
 * Course: CSCI 403 Capstone
 * Description: This activity is what the user uses to view the parking area
 * list and calculate/view the number of spaces for the desired areas
*/
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
import com.google.firebase.auth.FirebaseAuth

class UserView : AppCompatActivity(), UserParkingAreaAdapter.OnItemClickListener {
    private lateinit var parkingAreaDatabaseHelper: ParkingAreaDatabaseHelper
    private lateinit var userParkingAreaAdapter: UserParkingAreaAdapter
    private lateinit var userParkingAreaRecyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
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

        auth = FirebaseAuth.getInstance()

        parkingAreaDatabaseHelper = ParkingAreaDatabaseHelper.getInstance(this)

        // Create the log out button
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Log out and go back to the initial page
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            auth.signOut()
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
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
        Toast.makeText(this, "Clicked: ${parkingArea.name}", Toast.LENGTH_SHORT).show()
        val myIntent = Intent(this, ParkingAreaDetailsActivity::class.java)
        myIntent.putExtra("NAME", parkingArea.name)
        startActivity(myIntent)
    }
}