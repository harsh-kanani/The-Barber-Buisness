package com.example.thebarberbuisness

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_category.*


class AddCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        var sp=getSharedPreferences("MySp",Activity.MODE_PRIVATE)
        var unm=sp.getString("unm","null")

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Category")
        var myRef1=myRef.child("${unm.toString()}")
        btnadd.setOnClickListener {
            var categoryData = CategoryData(txtcat.text.toString(),txtprice.text.toString(),txtmin.text.toString().toInt(),"On")

            myRef1.child("${txtcat.text.toString()}").setValue(categoryData)
            Toast.makeText(this@AddCategory,"Successfully Add",Toast.LENGTH_LONG).show()
        }
    }
}
