package ru.belenkov.myapplication.model.api

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.belenkov.myapplication.model.entity.Contact
import ru.belenkov.myapplication.util.Database.Companion.DB_NAME
import ru.belenkov.myapplication.util.Database.Companion.DB_VERSION
import ru.belenkov.myapplication.util.SQL.Companion.COL_NAME
import ru.belenkov.myapplication.util.SQL.Companion.COL_PHONE
import ru.belenkov.myapplication.util.SQL.Companion.CREATE_TABLE
import ru.belenkov.myapplication.util.SQL.Companion.DROP_TABLE
import ru.belenkov.myapplication.util.SQL.Companion.SELECT_ALL
import ru.belenkov.myapplication.util.SQL.Companion.TABLE_NAME

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun insertContact(contact: Contact) {
        val db = writableDatabase
        val cv = ContentValues()

        cv.put(COL_NAME, contact.name)
        cv.put(COL_PHONE, contact.phone)

        db.insert(TABLE_NAME, null, cv)
    }

    fun getAllContacts(): ArrayList<Contact> {
        val db = readableDatabase
        val cursor = db.rawQuery(SELECT_ALL, null)

        val contactList = ArrayList<Contact>()
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val contact = Contact(
                    name = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    phone = cursor.getString(cursor.getColumnIndex(COL_PHONE))
                )
                contactList.add(contact)

                cursor.moveToNext()
            }
        }
        return contactList
    }
}