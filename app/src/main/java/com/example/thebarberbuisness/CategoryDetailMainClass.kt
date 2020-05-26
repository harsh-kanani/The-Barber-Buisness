package com.example.thebarberbuisness

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_detail_custom.view.*

class CategoryDetailMainClass(var ctx:Activity,var arlst:ArrayList<CategoryData>):RecyclerView.Adapter<CategoryDetailMainClass.viewholder>() {
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var nm= v.txtcatdetails
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view = ctx.layoutInflater.inflate(R.layout.category_detail_custom,parent,false)
        var vh =viewholder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return arlst.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.nm.text = arlst[position].Name
    }
}