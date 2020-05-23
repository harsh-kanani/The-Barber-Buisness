package com.example.thebarberbuisness

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.category_custom.view.*


class CategoryMainClass(var ctx:Activity,var arlst:ArrayList<CategoryData>,var unm:String): RecyclerView.Adapter<CategoryMainClass.Viewholder>()
{

    inner class Viewholder(v:View):RecyclerView.ViewHolder(v){
        var nm = v.txtcatprice
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

        holder.nm.setOnClickListener {
            var dialog = Dialog(ctx)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.view_category_dialog)
            var cnm = dialog.findViewById<TextView>(R.id.lblcategoryname)
            cnm.setText("Name : "+arlst[position].Name.toString())
            var prc=dialog.findViewById<TextView>(R.id.lblcategoryprice)
            prc.setText("Price : "+ arlst[position].Price.toString())
            var tm= dialog.findViewById<TextView>(R.id.lblcategorytime)
            tm.setText("Minutes : "+arlst[position].Minute.toString())
            var can = dialog.findViewById<Button>(R.id.btnok)
            can.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


        holder.edit.setOnClickListener {
            var dialog = Dialog(ctx)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.category_edit)
            var cnm = dialog.findViewById<TextView>(R.id.lblnm)
            cnm.setText(arlst[position].Name.toString())
            var prc=dialog.findViewById<EditText>(R.id.txtcatprice)
            prc.setText(arlst[position].Price.toString())
            var tm= dialog.findViewById<EditText>(R.id.txtcatminute)
            tm.setText(arlst[position].Minute.toString())
            var can = dialog.findViewById<Button>(R.id.btncancel)
            can.setOnClickListener {
                dialog.dismiss()
            }

            var editbutton = dialog.findViewById<Button>(R.id.btnedt)
            editbutton.setOnClickListener {
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("Category")
                var category = CategoryData(cnm.text.toString(),prc.text.toString(),tm.text.toString().toInt(),arlst[position].Status)
                myRef.child(unm).child(cnm.text.toString()).setValue(category).addOnCompleteListener{
                    Toast.makeText(ctx,"Data SuccessFully Change",Toast.LENGTH_LONG).show()
                }

                dialog.dismiss()
            }
            dialog.show()

        }

        holder.del.setOnCheckedChangeListener { buttonView, isChecked ->
            var msg = if(isChecked) "On" else "Off"

            myRef1.child(arlst[position].Name).child("status").setValue(msg).addOnCompleteListener {
                Toast.makeText(ctx,arlst[position].Name.toString() + " is $msg",Toast.LENGTH_LONG).show()
            }

        }
    }



}