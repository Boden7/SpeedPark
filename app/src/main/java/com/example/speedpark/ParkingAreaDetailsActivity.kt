/*
 * Author: Boden Kahn
 * Course: CSCI 403 Capstone
 * Description: This activity displays the details of a parking area that are
 * passed in from the user view activity.
*/
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ParkingAreaDetailsActivity : AppCompatActivity(){
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
        val image: ImageView = findViewById(R.id.lotImage)
        // Get the extra value passed in from the user view
        val name = intent.getStringExtra("NAME")
        // Send a request to the server with the name of the parking lot

        // Receive the response from the server with the labeled image

        // Display the info grabbed
        nameDisplay.text = "Area Name: $name"
        //numberOfSpaces.text = "Available spaces: $numSpaces"
        //image.setImageResource(null)
    }
}