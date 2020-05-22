package com.example.thebarberbuisness

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*


class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        lbllogin.setOnClickListener {
            startActivity(Intent(this@Registration,Login::class.java))
            finish()
        }
        btnreg.setOnClickListener {
            var shopData=ShopData(txtusernm.text.toString(),txtmail.text.toString(),txtmno.text.toString()," "," ",txtpass.text.toString()," "," ","Open"," ")
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Shop")

            myRef.child("${txtusernm.text.toString()}").setValue(shopData)
            Toast.makeText(this@Registration,"Successfully Register",Toast.LENGTH_LONG).show()
            startActivity(Intent(this@Registration,Login::class.java))
            finish()
        }
    }
}
