package com.example.tkppl_yes_248

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tkppl_yes_248.R

class FilterAdapter(private val context: Context, val hairFilterList: List<HairFilter>, val listener: (HairFilter) -> Unit) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    //returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_filter,parent,false)
        return ViewHolder(v)
    }
    //binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(hairFilterList[position], listener)
    }

    //giving the size of the list
    override fun getItemCount(): Int {
        return hairFilterList.size
    }

    //holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHairName = itemView.findViewById(R.id.tvFilter) as TextView
        val textViewHairImage = itemView.findViewById(R.id.img_filter) as ImageView

        fun bindItems(hairfilter: HairFilter, listener: (HairFilter) -> Unit){
            textViewHairImage.setImageResource(hairfilter.hairFilter)
            textViewHairName.text = hairfilter.hairName
            itemView.setOnClickListener{
                listener(hairfilter)
                Log.d("FilterAdapter", "Sedang Memilih Filter ${textViewHairName.text}")}
        }
    }
}