package com.techjd.chatapp.ui

import com.techjd.chatapp.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.techjd.chatapp.ChatApplication
import com.techjd.chatapp.adapters.UsersAdapter
import com.techjd.chatapp.databinding.ActivityMainBinding
import com.techjd.chatapp.ui.fragment.ChatViewModelProviderFactory
import io.socket.client.Socket
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var socketId: String
    private lateinit var userObj: JSONObject
    private lateinit var gson: Gson
    private lateinit var adapter: UsersAdapter
    lateinit var mSocket: Socket
    private lateinit var token: String
    val TAG = "LIFECYCLE"
    lateinit var chatViewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(TAG, "onCreate: ")

        val navController = findNavController(R.id.fragment)
        binding.bottomNavigation.setupWithNavController(navController)

        showBottom()
//        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)
//
//        if (savedInstanceState == null) {
//            Log.d("SAVED INSTANCE", "NULL")
//            supportFragmentManager.beginTransaction().replace(
//                binding.fragmentContainer.id,
//                ChatsFragment()
//            ).commit()
//        }
        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE)

        val app: ChatApplication = application as ChatApplication
        mSocket = app.socket!!
        mSocket.connect()
//        Log.d("CHECKING ID", mSocket.id())
        chatViewModel = ViewModelProvider(
            this,
            ChatViewModelProviderFactory(mSocket)
        ).get(ChatViewModel::class.java)
        Log.d("USER ID", sharedPreferences.getString("id", null).toString())
        chatViewModel.getSocketId(sharedPreferences.getString("id", null))
        chatViewModel.socketIdLD.observe(this, { id ->
            Log.d("ID FROM VIEWMODEL", id)
        })
//        mSocket.connect()
//        userObj = JSONObject()

        userObj = JSONObject()


//        mSocket.on(Socket.EVENT_CONNECT) {
//            // Binding TextView to SocketId
////            binding.socketId.text = mSocket.id().toString()
//
//            // Logging
//            Log.d("SOCKET ID", mSocket.id().toString())
//            // Assigning SocketId to var
//            socketId = mSocket.id().toString()
//            // Putting User Id and SocketId into userObj
//            userObj.put("id", sharedPreferences.getString("id", null))
//            userObj.put("socketId", mSocket.id().toString())
//            // Emitting
//            mSocket.emit("userJoined", userObj)
//            // Putting SocketId into Shared Preferences
//            val editor = sharedPreferences.edit()
//            editor.putString("socketId", mSocket.id().toString());
//            editor.commit()
//        }

//        gson = Gson()
//        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE)
//        val app: ChatApplication = application as ChatApplication
//        mSocket = app.socket!!
//
//        token = sharedPreferences.getString("token", null)!!
//
//        makeCall()
////        binding.userName.text = name
//
//
//
//
////        uri = URI.create("http://192.168.2.4:3000/")
////        options = IO.Options.builder()
////            .setForceNew(false)
////            .setMultiplex(true)
////            .build()
////        socket = IO.socket(uri, options)
//
//        mSocket.connect()
//        userObj = JSONObject()
//
//
//        mSocket.on(Socket.EVENT_CONNECT) {
//            // Binding TextView to SocketId
//            binding.socketId.text = mSocket.id().toString()
//            // Logging
//            Log.d("SOCKET ID", mSocket.id().toString())
//            // Assigning SocketId to var
//            socketId = mSocket.id().toString()
//            // Putting User Id and SocketId into userObj
//            userObj.put("id", sharedPreferences.getString("id", null))
//            userObj.put("socketId", mSocket.id().toString())
//            // Emitting
//            mSocket.emit("userJoined", userObj)
//            // Putting SocketId into Shared Preferences
//            val editor = sharedPreferences.edit()
//            editor.putString("socketId", mSocket.id().toString());
//            editor.commit()
//        }
//
////        socket.on("id") { id ->
////
////
////        }
//
////        socket.emit("userJoined", userObj)
//        println(userObj.toString())
//        val socketId = sharedPreferences.getString("socketId", null)
//        println(binding.socketId.text.toString() + " String ")
//        adapter = UsersAdapter(this)
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//
//        var use: ArrayList<Users>
//        val listType = object : TypeToken<List<Users>>() {}.type
//        mSocket.on("users") { user ->
//            use = Gson().fromJson(user[0].toString(), listType)
//            runOnUiThread {
//                adapter.setData(use)
//            }
//            println(use.toString())
//        }
////        socket.emit("userJoined", )


    }

    fun hideBottom() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottom() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

//    private fun makeCall() {
//        val service = RetrofitInstance()
//
//        service.getService().getLoggedInUserInfo(
//            token
//        ).enqueue(object : Callback<User> {
//
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                binding.userName.text = response.body()?.name
//                val editor = sharedPreferences.edit()
//                editor.putString("id", response.body()?._id);
//                editor.commit()
//            }
//
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                Toast.makeText(
//                    this@MainActivity,
//                    "Server Side Error ! TRY Again later",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
//    }

//    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            var selectedFragment: Fragment? = null
//            when (item.getItemId()) {
//                R.id.chats -> selectedFragment = ChatsFragment()
//                R.id.onlineusers -> selectedFragment = OnlineUsersFragment()
//                R.id.profile -> selectedFragment = ProfileFragment()
//            }
//            if (selectedFragment != null) {
//                supportFragmentManager.beginTransaction().replace(
//                    binding.fragmentContainer.id,
//                    selectedFragment
//                ).commit()
//            }
//            true
//        }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")

//        val socId = JSONObject()
//        socId.put("socketId", mSocket.id())
//        mSocket.emit("userLeft", socId)
//        mSocket.close()
////        Log.d("SOCKETID", "onDestroy: ${mSocket.id()}")
//        mSocket.disconnect()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        val socId = JSONObject()
        socId.put("socketId", mSocket.id())
        mSocket.emit("userLeft", socId)
        Log.d("SOCKETID", "onDestroy: ${mSocket.id()}")
        mSocket.disconnect()
        mSocket.off()
        mSocket.close()
        finish()
//        finishAndRemoveTask()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }


}