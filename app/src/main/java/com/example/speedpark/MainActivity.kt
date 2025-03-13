package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var signupBut: Button
    private lateinit var loginBut: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.HomePage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        signupBut.setOnClickListener{

            val intent = Intent(this,CreateAccount::class.java)
            startActivity(intent)
        }

        loginBut.setOnClickListener{

            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }





        }
    }
