package com.yashanand.myquiz.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yashanand.myquiz.R
import com.yashanand.myquiz.activity.SetActivity
import com.yashanand.myquiz.models.LangaugesList

class ProgramTypeAdapter(var context: Context, var itemList: ArrayList<LangaugesList>) :
        RecyclerView.Adapter<ProgramTypeAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item_layout, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val items = itemList[position]
        holder.p_name.text = items.LangName
        Picasso.get().load(items.LangImage).error(R.drawable.ic_launcher_background).into(holder.Image_icon)

        holder.relative_item.setOnClickListener {
            //Toast.makeText(context,"${holder.p_name.text} Clicked success!!! $position", Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, SetActivity::class.java).putExtra("Position", position + 1)
                    .putExtra("language_name", holder.p_name.text))
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Image_icon = itemView.findViewById<ImageView>(R.id.img_icon)
        var p_name = itemView.findViewById<TextView>(R.id.tv_title)
        val relative_item = itemView.findViewById<LinearLayout>(R.id.rel_item)
    }
}