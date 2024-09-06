package br.com.suzintech.listatelefonica.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.suzintech.listatelefonica.DBHelper
import br.com.suzintech.listatelefonica.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.buttonSignup.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordConfirm = binding.editConfirmPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
                if (password.equals(passwordConfirm)) {
                    val res = db.insertUser(username, password)

                    if (res > 0) {
                        Toast.makeText(
                            applicationContext,
                            "OK",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "ERROR",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.editUsername.setText("")
                        binding.editPassword.setText("")
                        binding.editConfirmPassword.setText("")
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "As senhas não coincidem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }

            finish()
        }
    }
}