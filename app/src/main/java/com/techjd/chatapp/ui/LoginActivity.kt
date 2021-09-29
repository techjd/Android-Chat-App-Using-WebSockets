package com.techjd.chatapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.techjd.chatapp.api.RetrofitInstance
import com.techjd.chatapp.api.token
import com.techjd.chatapp.databinding.ActivityLoginBinding
import com.techjd.chatapp.models.ReturnedRes.UserRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE)

        binding.login.setOnClickListener {
            makeCall()
        }

        binding.navigateToRegister.setOnClickListener {
            val navigate = Intent(this, RegisterActivity::class.java)
            startActivity(navigate)
        }
    }

    private fun makeCall() {
        val service = RetrofitInstance()

        service.getService().loginUser(
            email = binding.outlinedTextFieldEmail.editText?.text.toString(),
            password = binding.outlinedTextFieldPassword.editText?.text.toString()
        ).enqueue(object : Callback<UserRes> {

            override fun onResponse(call: Call<UserRes>, response: Response<UserRes>) {
                val editor = sharedPreferences.edit()
                editor.putString("token", response.body()?.token);
                editor.putString("id", response.body()?.user?._id);
                editor.putString("name", response.body()?.user?.name);
                editor.commit()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<UserRes>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Server Side Error ! TRY Again later",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}