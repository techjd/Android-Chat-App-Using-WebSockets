package com.techjd.chatapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techjd.chatapp.R
import com.techjd.chatapp.ui.ChatViewModel
import com.techjd.chatapp.ui.MainActivity

class OnlineUsersFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity
        chatViewModel = mainActivity.chatViewModel
        chatViewModel.socketIdLD.observe(this, { id ->
            Log.d("SOCKET FROM ON FRAG", id)
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_users, container, false)
    }
}