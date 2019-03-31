package ru.belenkov.myapplication.util

class Database {
    companion object {
        const val DB_NAME = "Phonebook.db"
        const val DB_VERSION = 1
    }
}

class SQL {
    companion object {
        const val TABLE_NAME = "Contacts"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_PHONE = "phone"
        const val CREATE_TABLE = """create table $TABLE_NAME(
            $COL_ID integer primary key autoincrement,
            $COL_NAME text not null,
            $COL_PHONE text not null);"""
        const val SELECT_ALL = "select * from $TABLE_NAME"
        const val DROP_TABLE = "drop table $TABLE_NAME"
    }
}
