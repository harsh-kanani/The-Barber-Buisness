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
        Toast.makeText(this@AddCategory,unm.toString(),Toast.LENGTH_LONG).show()

        var arlst:ArrayList<CategoryData> = arrayListOf<CategoryData>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Category")
        var myRef1=myRef.child("${unm.toString()}")


        myRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                arlst.clear()
                for(s1 in dataSnapshot.children){
                    val value =s1.getValue(CategoryData::class.java)
                    if(value != null){
                        arlst.add(value)
                    }
                }



                var ad=CategoryMainClass(this@AddCategory,arlst,unm.toString())
                lstvw.adapter=ad
                lstvw.layoutManager= LinearLayoutManager(this@AddCategory,LinearLayoutManager.VERTICAL,false)
            }




            override fun onCancelled(error: DatabaseError) { // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }

        })

        btnadd.setOnClickListener {
            var categoryData = CategoryData(txtcat.text.toString(),txtprice.text.toString(),txtmin.text.toString().toInt())

            myRef1.child("${txtcat.text.toString()}").setValue(categoryData)
            Toast.makeText(this@AddCategory,"Successfully Add",Toast.LENGTH_LONG).show()
        }
    }
}
