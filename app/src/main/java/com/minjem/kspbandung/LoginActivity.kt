package com.minjem.kspbandung

import android.R.attr.password
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val mAuth = FirebaseAuth.getInstance()

        bLogin.setOnClickListener {
            Log.d("Testing", "Username: ${tietUsername.text.toString()}, Password: ${tietPassword.text.toString()}")
            if (isEmpty(tietUsername.text!!)){
                tietUsername.error = "Tidak boleh kosong"
                return@setOnClickListener
            }

            if (isEmpty(tietPassword.text!!)){
                tietPassword.error = "Tidak boleh kosong"
                return@setOnClickListener
            }

            mpbLogin.visibility = View.VISIBLE

            mAuth.signInWithEmailAndPassword(tietUsername.text.toString(), tietPassword.text.toString())
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = mAuth.currentUser
                        mpbLogin.visibility = View.GONE
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(
                            "Login",
                            "signInWithEmail:failure",
                            task.exception
                        )
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        mpbLogin.visibility = View.GONE
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun isEmpty(text: Editable) : Boolean{
        return text != null && text.length <= 5
    }
}