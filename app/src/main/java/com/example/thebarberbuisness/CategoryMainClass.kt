package com.example.thebarberbuisness

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.category_custom.view.*


class CategoryMainClass(var ctx:Activity,var arlst:ArrayList<CategoryData>,var unm:String): RecyclerView.Adapter<CategoryMainClass.Viewholder>()
{

    inner class Viewholder(v:View):RecyclerView.ViewHolder(v){
        var nm = v.lblcatnm
        var edit = v.btncatedit
        var del = v.btncatdel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view=ctx.layoutInflater.inflate(R.layout.category_custom,parent,false)
        var vh = Viewholder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return arlst.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.nm.text = arlst[position].Name.toString()
        holder.edit.setOnClickListener {
            Toast.makeText(ctx,arlst[position].Name.toString(),Toast.LENGTH_LONG).show()
        }
        holder.del.setOnClickListener {
            // Write a message to the database
            // Write a message to the database

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Category")

            myRef.child("$unm").child("${arlst[position].Name.toString()}").removeValue()
        }
    }



}