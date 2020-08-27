package com.minjem.kspbandung

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_dialog_pinjaman.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var userId: String
    private lateinit var ktp: String
    private lateinit var timeStamp: String
    private lateinit var tgl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActivity()
        initOnclick()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initActivity(){
        toolbarMain.title = "KSP-DDT"
        database = Firebase.database.reference
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        ktp = "9515652626"
        timeStamp = SimpleDateFormat("yyyyMMdd").format(Date())
        tgl = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date())
        cetJumlahPinjaman.setDecimals(false)
        cetJumlahPinjaman.setSeparator(".")
        val tenor = listOf("1 Bulan", "2 Bulan", "3 Bulan")
        val adapter = ArrayAdapter(this, R.layout.dropdown_menu_tenor, tenor)
        atvTenor.setAdapter(adapter)

    }

    private fun initOnclick(){
        toolbarMain.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.actionRiwayat -> {
                    Toast.makeText(this, "Riwayat", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.actionLogout -> {
                    logout()
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }

        bHitung.setOnClickListener {
            dialogKalkulasiPinjaman()
        }


        bWrite.setOnClickListener {
            val user = User(ktp, "Ailee", tgl,"Jl. BKN", "FIDAC", "fotoktp1212", "fotoselfi32344")
            database.child("KSP").child(userId).child(ktp).child(timeStamp).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun dialogKalkulasiPinjaman(){
        val view = layoutInflater.inflate(R.layout.bottom_dialog_pinjaman, null)
        val dialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

        view.bBatal.setOnClickListener {
            dialog.dismiss()
        }
    }




    private fun logout(){
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }

}