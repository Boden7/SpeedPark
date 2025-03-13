package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserView : AppCompatActivity() {
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



        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

        //Creating the RV and its adapter
        userParkingAreaRecyclerView = findViewById(R.id.rvData)
        userParkingAreaAdapter = UserParkingAreaAdapter(this, parkingAreas)
        userParkingAreaRecyclerView.adapter = userParkingAreaAdapter
        userParkingAreaRecyclerView.layoutManager = LinearLayoutManager(this)
        parkingAreas = parkingAreaDatabaseHelper.getAllParkingAreas().toMutableList()
        userParkingAreaAdapter.updateParkingAreas(parkingAreas)
    }
}