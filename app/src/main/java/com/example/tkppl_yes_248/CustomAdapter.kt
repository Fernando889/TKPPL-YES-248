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

class CustomAdapter(private val context: Context, val hairStyleList: List<HairStyle>, val listener: (HairStyle) -> Unit) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    //returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.card_product,parent,false)
        return ViewHolder(v)
    }
    //binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(hairStyleList[position], listener)
    }

    //giving the size of the list
    override fun getItemCount(): Int {
        return hairStyleList.size
    }

    //holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHairName = itemView.findViewById(R.id.tvname) as TextView
        val textViewHairPrice = itemView.findViewById(R.id.tvprice) as TextView
        val textViewHairImage = itemView.findViewById(R.id.img_product) as ImageView

        fun bindItems(hairstyle: HairStyle, listener: (HairStyle) -> Unit){
            textViewHairImage.setImageResource(hairstyle.hairImage)
            textViewHairName.text = hairstyle.hairName
            textViewHairPrice.text = hairstyle.hairPrice
            itemView.setOnClickListener{
                listener(hairstyle)
                Log.d("HairAdapter", "Sedang Memilih ${textViewHairName.text}")
            }
        }
    }


}