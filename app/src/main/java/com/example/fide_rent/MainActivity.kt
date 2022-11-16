package com.example.fide_rent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // No Show the Top Action Bar
        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@MainActivity,loginActivity::class.java)//The new View
            startActivity(intent)
            finish()
        },4000)
    }
}