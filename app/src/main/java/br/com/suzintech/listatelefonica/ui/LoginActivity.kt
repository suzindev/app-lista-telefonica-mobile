package br.com.suzintech.listatelefonica.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.suzintech.listatelefonica.DBHelper
import br.com.suzintech.listatelefonica.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        if (username != null) {
            if (username.isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val logged = binding.checkboxLogged.isChecked

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (db.login(username, password)) {
                    if (logged) {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                    }

                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Login error",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.editUsername.setText("")
                    binding.editPassword.setText("")
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.textSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.textRecoverPassword.setOnClickListener {}
    }
}