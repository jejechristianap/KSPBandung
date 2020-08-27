package com.minjem.kspbandung

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        initOnclick()

    }

    override fun onStart() {
        super.onStart()
        val mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initOnclick(){
        bLogin.setOnClickListener {
            Log.d("Testing", "Username: ${tietUsername.text.toString()}, Password: ${tietPassword.text.toString()}")
            if (TextUtils.isEmpty(tietUsername.text!!)){
                tilUsername.error = "Masukkan email admin"
                return@setOnClickListener
            } else {
                tilUsername.error = null
            }

            if (TextUtils.isEmpty(tietPassword.text!!)){
                tilPassword.error = "Masukkan password"
                return@setOnClickListener
            } else {
                tietPassword.error = null
            }
            mpbLogin.visibility = View.VISIBLE
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            login()


        }
    }

    private fun login(){
        mAuth.signInWithEmailAndPassword(tietUsername.text.toString(), tietPassword.text.toString())
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    val user = mAuth.currentUser
                    mpbLogin.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
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