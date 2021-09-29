package com.techjd.chatapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techjd.chatapp.Inital
import com.techjd.chatapp.R
import com.techjd.chatapp.ui.ChatViewModel
import com.techjd.chatapp.ui.MainActivity


class ProfileFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var mainActivity: MainActivity
    private lateinit var ini: Inital
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity
        chatViewModel = mainActivity.chatViewModel
        chatViewModel.socketIdLD.observe(this, { id ->
            Log.d("SOCKET ID FROM PROF", id)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ini = view.findViewById(R.id.initialValue)
////        ini.text = "JD"
//        ini.display("J")
//        ini.setCoordinate(100F)
//        ini.setRadius(60F)
    }

}