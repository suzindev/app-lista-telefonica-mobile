package br.com.suzintech.listatelefonica

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.suzintech.listatelefonica.model.User

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {

    val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
        "INSERT INTO users (username, password) VALUES ('admin', 'admin')"
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
}