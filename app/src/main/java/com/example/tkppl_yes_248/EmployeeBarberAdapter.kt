package com.example.tkppl_yes_248

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tkppl_yes_248.R

class EmployeeBarberAdapter (private val DiscussionList: ArrayList<EmployeeBarber>,
                             private val onEmployeeBarberClickListener: OnExploreClickListener): RecyclerView.Adapter<EmployeeBarberAdapter.ViewHolder>(){
    interface OnExploreClickListener{
        fun onExploreClicked(position: Int,item: EmployeeBarber)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgAccount : ImageView
        var tvUsername : TextView
        var tvQuestion : TextView

        init {
            imgAccount = itemView.findViewById(R.id.imgAccount)
            tvUsername = itemView.findViewById(R.id.tvUsername)
            tvQuestion = itemView.findViewById(R.id.tvQuestion)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_barber_employee,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemdiscussion = DiscussionList[position]
        holder.imgAccount.setImageResource(itemdiscussion.ImageProfile)
        holder.tvUsername.text = itemdiscussion.Username
        holder.tvQuestion.text = itemdiscussion.DiscussionContent
        holder.itemView.setOnClickListener {
            onEmployeeBarberClickListener.onExploreClicked(position,itemdiscussion)
        }
    }

    override fun getItemCount(): Int {
        return DiscussionList.size
    }

}