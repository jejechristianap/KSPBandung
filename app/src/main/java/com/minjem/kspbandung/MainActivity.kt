package com.minjem.kspbandung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.ajalt.timberkt.Timber.d
import com.github.ajalt.timberkt.Timber.e
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        userId = FirebaseAuth.getInstance().currentUser!!.uid


        val ktp = "9515652626"
        val timeStamp = SimpleDateFormat("yyyyMMdd").format(Date())
        val tgl = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date())
        d{"user $ktp"}

        bWrite.setOnClickListener {
            val user = User(ktp, "Ailee", tgl,"Jl. BKN", "FIDAC", "fotoktp1212", "fotoselfi32344")
            database.child("KSP").child(userId).child(timeStamp).child(ktp).setValue(user)
                .addOnSuccessListener {
                    d{"upload success"}
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    e{"$it exception"}
                }
        }

    }
}