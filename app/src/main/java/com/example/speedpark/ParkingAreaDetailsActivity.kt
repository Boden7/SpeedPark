// Author: Boden Kahn
// Course: CSCI 403 Capstone
// Description: This activity displays the details of a parking area that are
// passed in from the user view activity.
package com.example.speedpark

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ParkingAreaDetailsActivity : AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_parking_area_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.parkingAreaDetails)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the back button
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, UserView::class.java)
            startActivity(intent)
        }

        val nameDisplay: TextView = findViewById(R.id.parkingAreaName)
        val numberOfSpaces: TextView = findViewById(R.id.numberOfSpaces)
        // Get the extra values passed in from the user view
        val name = intent.getStringExtra("NAME")
        val numSpaces = intent.getIntExtra("NUMSPACES", 0)
        // Display the info grabbed
        nameDisplay.text = "Area Name: $name"
        numberOfSpaces.text = "Available spaces: $numSpaces"
    }
}