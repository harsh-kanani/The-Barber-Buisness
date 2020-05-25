package com.example.thebarberbuisness

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.appointment_custom.view.*


class AppointmentMainClass(var ctx:Activity,var arlst:ArrayList<AppointmentData>,var unm:String):RecyclerView.Adapter<AppointmentMainClass.viewholder>() {
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var nm = v.lblcusnm
        var dt = v.lbldt
        var tm = v.lbltm
        var com = v.btncomplete
        var can = v.btncancel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view = ctx.layoutInflater.inflate(R.layout.appointment_custom,parent,false)
        var vh = viewholder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return arlst.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.nm.text=arlst[position].custunm
        holder.dt.text=arlst[position].date
        holder.tm.text=arlst[position].time
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("userdata")
        val myRef2 = database.getReference("appinment")


        holder.com.setOnClickListener {
            //var arl:ArrayList<CategoryData> = arrayListOf<CategoryData>()
            var i=0
            var myref1 = myRef2.child(unm). child(arlst[position].date).child(arlst[position].time).child("bookfor")
            myref1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //arl.clear()
                    for (s in dataSnapshot.children){
                        i+=1
                        var value = s.getValue(CategoryData::class.java)
                        if (value!= null){
                            myRef.child(arlst[position].custunm).child("History").child(arlst[position].date).child(arlst[position].time).child("bookfor").child(i.toString()).setValue(value)
                            //arl.add(value)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
            var history =HistoryData(arlst[position].custunm,arlst[position].date,arlst[position].time,"Completed")
            myRef.child(arlst[position].custunm).child("History").child(arlst[position].date).child(arlst[position].time).setValue(history)
            myRef2.child(unm).child(arlst[position].date).child(arlst[position].time).removeValue()
        }
        holder.can.setOnClickListener {
            var i=0
            var myref1 = myRef2.child(unm). child(arlst[position].date).child(arlst[position].time).child("bookfor")
            myref1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //arl.clear()
                    for (s in dataSnapshot.children){
                        i+=1
                        var value = s.getValue(CategoryData::class.java)
                        if (value!= null){
                            myRef.child(arlst[position].custunm).child("History").child(arlst[position].date).child(arlst[position].time).child("bookfor").child(i.toString()).setValue(value)
                            //arl.add(value)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
                var history =HistoryData(arlst[position].custunm,arlst[position].date,arlst[position].time,"Cancelled")
                myRef.child(arlst[position].custunm).child("History").child(arlst[position].date).child(arlst[position].time).setValue(history)
                myRef2.child(unm).child(arlst[position].date).child(arlst[position].time).removeValue()
        }
    }

}