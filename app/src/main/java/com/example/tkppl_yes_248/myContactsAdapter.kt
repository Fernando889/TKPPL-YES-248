package com.example.tkppl_yes_248

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkppl_yes_248.R
import kotlinx.android.synthetic.main.recycler_barber_employee.view.*

class myContactsAdapter (private val contact: List<myContacts>) : RecyclerView.Adapter<myHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        return myHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_barber_employee,parent,false)
        )
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        holder.bindContact(contact[position])
    }

    override fun getItemCount(): Int = contact.size
}

class myHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val contactName = view.tvUsername
    private val contactNumber = view.tvQuestion

    fun bindContact(tmp: myContacts) {
        contactName.text = "${contactName.text} ${tmp.nama}"
        contactNumber.text = "${contactNumber.text} ${tmp.nomorHp}"
    }
}