package ru.belenkov.myapplication.controller.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_contact.view.*
import ru.belenkov.myapplication.model.entity.Contact

class ItemContactVh(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.name
    val phone = view.phone

    fun bind(contact: Contact) {
        name.text = contact.name
        phone.text = contact.phone
    }
}