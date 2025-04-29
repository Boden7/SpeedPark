/*
 * Author: Boden Kahn
 * Course: CSCI 403 Capstone
 * Description: This activity displays the details of a parking area that are
 * passed in from the user view activity.
*/
package com.example.speedpark

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

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
        val numberOfSpacesTaken: TextView = findViewById(R.id.numberOfSpacesTaken)

        // Get the extra value passed in from the user view
        val name = intent.getStringExtra("NAME")

        // Display the info grabbed
        val periodIndex = name.toString().indexOf('.')
        val lotID = name?.substring(0, periodIndex)
        val displayName = name?.substring(periodIndex + 1, name.length)
        nameDisplay.text = "Area Name: $displayName"

        // Retrieve saved token from SharedPreferences
        val sharedPref = getSharedPreferences("speedpark_prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("jwt_token", null)

        // Send a request to the server with the name of the parking lot
        if (token != null) {
            val authHeader = "Bearer $token"
            RetrofitClient.api.getParkingAvailability(authHeader, lot = lotID.toString())
                .enqueue(object : Callback<ParkingAvailabilityResponse> {
                    override fun onResponse(
                        call: Call<ParkingAvailabilityResponse>,
                        response: Response<ParkingAvailabilityResponse>
                    ) {
                        if (response.isSuccessful) {
                            val available = response.body()?.data?.free_spots ?: "?"
                            numberOfSpaces.text = "Available spaces: $available"
                            val not_available = response.body()?.data?.not_free_spots ?: "?"
                            numberOfSpacesTaken.text = "Unvailable spaces: $not_available"
                        } else {
                            Log.e("API", "Response error: ${response.errorBody()?.string()}")
                            numberOfSpaces.text = "Error loading availability"

                        }
                    }

                    override fun onFailure(call: Call<ParkingAvailabilityResponse>, t: Throwable) {
                        Log.e("API", "Network failure: ${t.message}")
                        numberOfSpaces.text = "Error: ${t.message}"
                    }
                })
        }
        else {
            Log.e("ParkingDetails", "JWT token not found")
            numberOfSpaces.text = "Error: Not authenticated"
        }
    }
}