package ru.belenkov.myapplication.controller.acitivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_contact.view.*
import ru.belenkov.myapplication.R
import ru.belenkov.myapplication.controller.adapter.ContactListAdapter
import ru.belenkov.myapplication.model.api.DbHelper
import ru.belenkov.myapplication.model.entity.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var contactListAdapter: ContactListAdapter
    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initDbHelper()
        initFab()
        initContactRecycler()
    }

    private fun initFab() {
        contactFab.setOnClickListener {
            initContactDialog()
        }
    }

    private fun initContactRecycler() {
        contactListAdapter = ContactListAdapter()
        contactListRecycler.layoutManager = LinearLayoutManager(this)
        contactListRecycler.adapter = contactListAdapter
        contactListRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        refreshUserList()
    }

    private fun initContactDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_contact, null)
        dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(getString(R.string.add_user))
        dialogBuilder.setView(dialogView)

        dialogBuilder.setNegativeButton(getString(R.string.button_cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        dialogBuilder.setPositiveButton(getString(R.string.button_done)) { dialog, _ ->
            dbHelper.insertContact(Contact(dialogView.nameInput.text.toString(), dialogView.phoneInput.text.toString()))
            refreshUserList()
            Toast.makeText(this, getString(R.string.toast_user_added), Toast.LENGTH_SHORT).show()
        }

        dialogBuilder.show()
    }

    private fun refreshUserList() {
        contactListAdapter.contactList.clear()
        contactListAdapter.contactList.addAll(dbHelper.getAllContacts())
        contactListAdapter.notifyDataSetChanged()
    }

    private fun initDbHelper() {
        dbHelper = DbHelper(this)
    }
}
