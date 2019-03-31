package ru.belenkov.myapplication.controller.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.belenkov.myapplication.R
import ru.belenkov.myapplication.controller.viewholder.ItemContactVh
import ru.belenkov.myapplication.model.entity.Contact

class ContactListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val contactList = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_contact, null)

        return ItemContactVh(view)
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contact = contactList[position]
        (holder as ItemContactVh).bind(contact)
    }
}