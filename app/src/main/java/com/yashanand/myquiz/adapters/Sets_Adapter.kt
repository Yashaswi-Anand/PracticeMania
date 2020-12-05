package com.yashanand.myquiz.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yashanand.myquiz.R
import com.yashanand.myquiz.activity.QuestionActivity

class Sets_Adapter(var context: Context, var itemList: ArrayList<String>, val LangCount: Int) :
        RecyclerView.Adapter<Sets_Adapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.set_item_layout, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val items = itemList[position]
        holder.sets_no.text = items

        holder.cardview_item.setOnClickListener {
            //Toast.makeText(context,"${holder.sets_no.text} Clicked success!!! $position", Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, QuestionActivity::class.java).putExtra("Sets_Position", position + 1)
                    .putExtra("LangCount", LangCount))
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sets_no = itemView.findViewById<TextView>(R.id.set_number)
        val cardview_item = itemView.findViewById<CardView>(R.id.cardView)
    }
}