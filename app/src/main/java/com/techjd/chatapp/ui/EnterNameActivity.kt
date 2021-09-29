package com.techjd.chatapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import com.techjd.chatapp.R

class EnterNameActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var eT: TextInputLayout
    private lateinit var entet: Button

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)
        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE)
//        runOnUiThread {
//            if (sharedPreferences.contains("name")) {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            } else {
//
//            }
//        }


        eT = findViewById(R.id.outlinedTextField)
        entet = findViewById(R.id.enter)

        entet.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("name", eT.editText?.text.toString());
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d(TAG, "onStart: ")
//    }
//
//
//    override fun onStop() {
//        super.onStop()
//        Log.d(TAG, "onStop: ")
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG, "onDestroy: ")
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        Log.d(TAG, "onRestart: ")
//    }
}