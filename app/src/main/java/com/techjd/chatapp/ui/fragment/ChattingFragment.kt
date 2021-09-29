package com.techjd.chatapp.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techjd.chatapp.ChatApplication
import com.techjd.chatapp.R
import com.techjd.chatapp.adapters.ChatAdapter
import com.techjd.chatapp.api.ChatMessageItem
import com.techjd.chatapp.databinding.ActivityChatBinding
import com.techjd.chatapp.databinding.FragmentChatsBinding
import com.techjd.chatapp.databinding.FragmentChattingBinding
import com.techjd.chatapp.models.Messages.*
import com.techjd.chatapp.ui.ChatViewModel
import com.techjd.chatapp.ui.MainActivity
import io.socket.client.Socket
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class ChattingFragment : Fragment() {
    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!
    private lateinit var mSocket: Socket
    private lateinit var socketId: String
    private lateinit var username: String
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var to: String
    private lateinit var mainActivity: MainActivity
    private lateinit var chatViewModel: ChatViewModel
    val args: ChattingFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        mSocket = mainActivity.mSocket

        socketId = args.socketId
        username = args.username
        to = args.to
        name = args.name
        email = args.email
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChattingBinding.inflate(inflater, container, false)
        val view = binding.root
        sharedPreferences = requireContext().getSharedPreferences("NAME", Context.MODE_PRIVATE)
        val chatDet = JSONObject()
        chatDet.put("from", sharedPreferences.getString("id", null))
        chatDet.put("to", to)
        mSocket.emit("navigate", chatDet)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()
        mainActivity.hideBottom()
        chatAdapter = ChatAdapter(
            sharedPreferences.getString("id", null)
        )
        binding.tvProfileName.text = args.name
        binding.tvStaus.text = "Online"
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
//        val intent = requireActivity().intent

//        socketId = intent.getStringExtra("socket").toString()
//        username = intent.getStringExtra("name").toString()
//        to = intent.getStringExtra("to").toString()
//        Log.d("TO ID", "To Id is $to")

//        chatViewModel = mainActivity.chatViewModel
//        val app: ChatApplication = application as ChatApplication
//        mSocket = app.socket!!
//
//        mSocket.connect()

        println(mSocket.id())

        layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        binding.recyclerViewMessages.layoutManager = layoutManager
        binding.recyclerViewMessages.adapter = chatAdapter

        val gson = Gson()

        var use: ArrayList<MessagesItem>
        val listType = object : TypeToken<List<MessagesItem>>() {}.type
        mSocket.on("message") { message ->
            activity?.runOnUiThread {
                if (message[0]::class.java.simpleName == "JSONArray") {
                    use = Gson().fromJson(message[0].toString(), listType)
                    Log.d("MESSAGES LIST", "$use")
                    activity?.runOnUiThread {
                        chatAdapter.setList(use)
                        binding.recyclerViewMessages.scrollToPosition(chatAdapter.itemCount - 1)
                    }
                } else {
                    activity?.runOnUiThread {
                        val chatMessageItem = gson.fromJson(message[0].toString(), MessagesItem::class.java)
                        chatAdapter.setData(chatMessageItem)
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
            val chatMessage = MessagesItem(
                body = binding.textMessage.text.toString(),
                from = sharedPreferences.getString("id", null)!!,
                to = username,
                fromObj = listOf<FromObj>(
                    FromObj(
                        _id = sharedPreferences.getString("id", null)!!,
                        email = "abc@gmail.com",
                        name = sharedPreferences.getString("name", null)!!
                    )
                ),
                toObj = listOf<ToObj>(
                    ToObj(
                        _id = username,
                        email = email,
                        name = name
                    )
                )
//                from = From(
//                    _id = sharedPreferences.getString("id", null)!!,
//                    email = "jaydeepparmar723@gmail.com",
//                    name = sharedPreferences.getString("name", null)!!
//                ),

//                to = To(
//                    _id = username,
//                    email = email,
//                    name = name
//                )
            )
//            val chatMessage = ChatMessageItem(binding.textMessage.text.toString())
            chatAdapter.setData(chatMessage)
            mSocket.emit("chatMessage", msg)
            binding.recyclerViewMessages.scrollToPosition(chatAdapter.itemCount - 1)
            binding.textMessage.setText("")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}