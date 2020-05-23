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
        var nm = v.txtcatprc
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
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Category")
        var myRef1=myRef.child(unm.toString())


        var status=arlst[position].Status
        if(status == "On"){
            holder.del.isChecked=true
        }


        holder.edit.setOnClickListener {
            Toast.makeText(ctx,arlst[position].Name.toString(),Toast.LENGTH_LONG).show()
        }

        holder.del.setOnCheckedChangeListener { buttonView, isChecked ->
            var msg = if(isChecked) "On" else "Off"

            myRef1.child(arlst[position].Name).child("status").setValue(msg).addOnCompleteListener {
                Toast.makeText(ctx,arlst[position].Name.toString() + " is $msg",Toast.LENGTH_LONG).show()
            }

        }
    }



}