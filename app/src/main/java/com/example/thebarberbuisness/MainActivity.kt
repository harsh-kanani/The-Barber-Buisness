package com.example.thebarberbuisness

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zoom_in= AnimationUtils.loadAnimation(this,R.anim.zoom_in)
        val imageView= findViewById(R.id.imageView) as ImageView
        imageView.startAnimation(zoom_in)

        val zoom_logo= AnimationUtils.loadAnimation(this,R.anim.zoom_logo)
        val imagelogo= findViewById(R.id.imagelogo) as ImageView
        imagelogo.startAnimation(zoom_logo)



        Handler().postDelayed({
            startActivity(Intent(this@MainActivity,Login::class.java))
            finish()
        },2500)
    }
}
