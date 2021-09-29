package com.techjd.chatapp.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techjd.chatapp.ChatApplication
import com.techjd.chatapp.adapters.ChatAdapter
import com.techjd.chatapp.api.ChatMessageItem
import com.techjd.chatapp.databinding.ActivityChatBinding
import com.techjd.chatapp.ui.fragment.ChatViewModelProviderFactory
import io.socket.client.Socket
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var mSocket: Socket
    private lateinit var socketId: String
    private lateinit var username: String
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var name: String
    private lateinit var to: String
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        chatAdapter = ChatAdapter()
        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE)
//        name = sharedPreferences.getString("name", null)!!
//        chatViewModel = ViewModelProvider(
//            this,
//            ChatViewModelProviderFactory(mSocket)
//        ).get(ChatViewModel::class.java)
//
//        mSocket = chatViewModel.socket
//        mainActivity = MainActivity()
////        chatViewModel = mainActivity.chatViewModel
//        mSocket = mainActivity.mSocket
        val intent = intent
        socketId = intent.getStringExtra("socket").toString()
        username = intent.getStringExtra("name").toString()
        to = intent.getStringExtra("to").toString()
        Log.d("TO ID", "To Id is $to")

        val app: ChatApplication = application as ChatApplication
        mSocket = app.socket!!
//
//        mSocket.connect()

        println(mSocket.id())

        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding.recyclerViewMessages.layoutManager = layoutManager
        binding.recyclerViewMessages.adapter = chatAdapter

        val gson = Gson()


        var use: ArrayList<ChatMessageItem>
        val listType = object : TypeToken<List<ChatMessageItem>>() {}.type
        mSocket.on("message") { message ->
            runOnUiThread {
                if (message[0]::class.java.simpleName == "JSONArray") {
                    use = Gson().fromJson(message[0].toString(), listType)
                    runOnUiThread {
//                        chatAdapter.setList(use)
                        binding.recyclerViewMessages.scrollToPosition(chatAdapter.itemCount - 1)
                    }
                } else {
                    runOnUiThread {
                        val chatMessageItem =
                            gson.fromJson(message[0].toString(), ChatMessageItem::class.java)
//                        chatAdapter.setData(chatMessageItem)
                        binding.recyclerViewMessages.scrollToPosition(chatAdapter.itemCount - 1)
                    }
                }
            }
        }

        binding.send.setOnClickListener {
            val msg = JSONObject()
            msg.put("text", binding.textMessage.text.toString())
            msg.put("to", username)
            msg.put("from", sharedPreferences.getString("id", null))
            msg.put("socId", socketId)
            msg.put("too", to)
            val currentTime: String =
                SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
//            val message: Message =
//                Message(name!!, binding.textMessage.editText?.text.toString(), MyUtils.convertInto12(currentTime))
            val chatMessage = ChatMessageItem(binding.textMessage.text.toString())
//            chatAdapter.setData(chatMessage)
            mSocket.emit("chatMessage", msg)
            binding.recyclerViewMessages.scrollToPosition(chatAdapter.itemCount - 1)
            binding.textMessage.setText("")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("OnsTarty", "onStart: Executinggg")
        val chatDet = JSONObject()
        chatDet.put("from", sharedPreferences.getString("id", null))
        chatDet.put("to", to)
        mSocket.emit("navigate", chatDet)
    }
}