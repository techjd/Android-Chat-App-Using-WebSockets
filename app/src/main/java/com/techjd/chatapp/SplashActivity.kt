package com.techjd.chatapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.techjd.chatapp.ui.MainActivity
import com.techjd.chatapp.ui.RegisterActivity

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH: Long = 1000

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        Handler().postDelayed({
            if (token.isNullOrEmpty()) {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_DISPLAY_LENGTH)


    }
}