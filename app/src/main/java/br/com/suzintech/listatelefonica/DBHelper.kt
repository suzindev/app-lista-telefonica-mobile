package br.com.suzintech.listatelefonica

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.suzintech.listatelefonica.model.Contact
import br.com.suzintech.listatelefonica.model.User

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {

    val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
        "INSERT INTO users (username, password) VALUES ('admin', 'admin')",
        "CREATE TABLE contact (id INTEGER, PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, phone INT, imageId INT)",
        "INSERT INTO contact (name, email, phone, imageId) VALUES ('teste', 'teste@teste.com', 911222333, 1)",
        "INSERT INTO contact (name, email, phone, imageId) VALUES ('super', 'super@teste.com', 912345678, 2)"
    )

    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)

        val res = db.insert("users", null, contentValues)
        db.close()

        return res
    }

    fun updateUser(id: Int, username: String, password: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)

        val res = db.update("users", contentValues, "id=?", arrayOf(id.toString()))
        db.close()

        return res
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase

        val res = db.delete("users", "id=?", arrayOf(id.toString()))
        db.close()

        return res
    }

    fun login(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )

        if (c.count == 1) {
            return true
        } else {
            db.close()
            return false
        }
    }

    fun getUser(username: String): User {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=?",
            arrayOf(username)
        )

        var user = User()
        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val usernameIndex = c.getColumnIndex("username")
            val passwordIndex = c.getColumnIndex("password")

            user = User(
                id = c.getInt(idIndex),
                username = c.getString(usernameIndex),
                password = c.getString(passwordIndex)
            )
        }

        db.close()

        return user
    }

    fun inserContact(name: String, email: String, phone: Int, imageId: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("email", email)
        contentValues.put("phone", phone)
        contentValues.put("imageId", imageId)

        val res = db.insert("contact", null, contentValues)
        db.close()

        return res
    }

    fun updateContact(id: Int, name: String, email: String, phone: Int, imageId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("email", email)
        contentValues.put("phone", phone)
        contentValues.put("imageId", imageId)

        val res = db.update("contact", contentValues, "id=?", arrayOf(id.toString()))
        db.close()

        return res
    }

    fun deleteContact(id: Int): Int {
        val db = this.writableDatabase

        val res = db.delete("contact", "id=?", arrayOf(id.toString()))
        db.close()

        return res
    }

    fun getContact(id: Int): Contact {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM contact WHERE id=?",
            arrayOf(id.toString())
        )

        var contact = Contact()
        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val emailIndex = c.getColumnIndex("email")
            val phoneIndex = c.getColumnIndex("phone")
            val imageIdIndex = c.getColumnIndex("imageId")

            contact = Contact(
                id = c.getInt(idIndex),
                name = c.getString(nameIndex),
                email = c.getString(emailIndex),
                phone = c.getInt(phoneIndex),
                imageId = c.getInt(imageIdIndex)
            )
        }

        db.close()

        return contact
    }

    fun getAllContact(): List<Contact> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM contact", null)
        var listContact = ArrayList<Contact>()

        if (c.count > 0) {
            c.moveToFirst()

            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val emailIndex = c.getColumnIndex("email")
            val phoneIndex = c.getColumnIndex("phone")
            val imageIdIndex = c.getColumnIndex("imageId")

            do {
                val contact = Contact(
                    id = c.getInt(idIndex),
                    name = c.getString(nameIndex),
                    email = c.getString(emailIndex),
                    phone = c.getInt(phoneIndex),
                    imageId = c.getInt(imageIdIndex)
                )
                listContact.add(contact)
            } while (c.moveToNext())
        }

        db.close()

        return listContact
    }
}