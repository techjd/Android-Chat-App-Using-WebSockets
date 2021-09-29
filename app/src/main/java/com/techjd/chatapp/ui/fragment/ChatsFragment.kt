package com.techjd.chatapp.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techjd.chatapp.adapters.UsersAdapter
import com.techjd.chatapp.databinding.FragmentChatsBinding
import com.techjd.chatapp.models.Users
import com.techjd.chatapp.ui.ChatViewModel
import com.techjd.chatapp.ui.MainActivity
import io.socket.client.Socket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var socketId: String
    private lateinit var userObj: JSONObject
    private lateinit var gson: Gson
    private lateinit var adapter: UsersAdapter
    private lateinit var mSocket: Socket
    private lateinit var token: String
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.d("CALLING AGAIN", "onCreate: Calling Again and Again ")


//        val app = requireActivity().application as ChatApplication
//        mSocket = app.socket!!
//        mSocket.connect()


//        userObj = JSONObject()
//
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d("CALLING AGAIN", "onViewCreated: Calling Again and Again ")
        sharedPreferences = requireContext().getSharedPreferences("NAME", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", null)!!
        mainActivity = activity as MainActivity
        mainActivity.showBottom()
        chatViewModel = mainActivity.chatViewModel
//        Log.d("On View Created", "onViewCreated: This is calld")
        chatViewModel.socketIdLD.observe(viewLifecycleOwner, { id ->
            binding.userName.text = sharedPreferences.getString("name", null)!!
            binding.socketId.text = id

            Log.d("SOCKET FROM CHAT FRAG", id)
        })
        gson = Gson()



        GlobalScope.launch {
            delay(500)
            val cA = JSONObject()
//            binding.socketId.text = sharedPreferences.getString("socketId", null)

            cA.put("id", chatViewModel.socketIdLD.value)
            mainActivity.mSocket.emit("createdAgain", cA)
        }

        GlobalScope.launch {
            delay(200)
            activity?.runOnUiThread {
                adapter = UsersAdapter(requireContext(), sharedPreferences.getString("id", null)!!)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            }
        }


        var use: ArrayList<Users>
        var finalLisOfUsers = arrayListOf<Users>()
        val listType = object : TypeToken<List<Users>>() {}.type
        mainActivity.mSocket.on("users") { user ->
            use = Gson().fromJson(user[0].toString(), listType)

            activity?.runOnUiThread {

                if (binding.socketId.text == "") {
                    binding.socketId.text = chatViewModel.socketIdLD.value
//                    binding.socketId.text = sharedPreferences.getString("socketId", null)!!
                }
//                for (i in 0..(use.size-1)) {
//                    if (use[i].socketId == chatViewModel.socketIdLD.value) {
//
//                    } else {
//                        finalLisOfUsers.add(use[i])
//                    }
//                }
//                finalLisOfUsers = use
                adapter.setData(use)
            }
            println(use.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}