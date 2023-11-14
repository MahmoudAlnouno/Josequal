package com.josequal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.josequal.R
import com.josequal.model.DrawerItem

class DrawerAdapter(
    private val drawerItems: List<DrawerItem>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<DrawerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: DrawerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = drawerItems[position]
        holder.bind(item, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return drawerItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(item: DrawerItem, listener: OnItemClickListener) {
            titleTextView.text = item.title
            imageView.setImageResource(item.imageResourceId)
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }
}
