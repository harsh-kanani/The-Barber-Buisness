package com.example.thebarberbuisness

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            startActivity(Intent(this@MainActivity,Login::class.java))
            finish()
        },3000)
    }
}
