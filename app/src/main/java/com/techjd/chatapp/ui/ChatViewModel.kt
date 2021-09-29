package com.techjd.chatapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.chatapp.models.Users
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.json.JSONObject


class ChatViewModel(private val socketInstance: Socket) : ViewModel() {
    private val socketId = MutableLiveData<String>()
    val socketIdLD: LiveData<String> = socketId
    private lateinit var userObj: JSONObject
    private val users = MutableLiveData<ArrayList<Users>>()
    val usersLD: LiveData<ArrayList<Users>> = users
    val socket: Socket = socketInstance
    fun getSocketId(id: String?) = viewModelScope.launch {
        socketInstance.on(Socket.EVENT_CONNECT) {
            socketId.postValue(socketInstance.id())
            userObj = JSONObject()
            userObj.put("id", id)
            userObj.put("socketId", socketInstance.id())
            socketInstance.emit("userJoined", userObj)
        }
    }

    override fun onCleared() {
        super.onCleared()
//        if (socketInstance == null ){
//            Log.d("SOCKET NULL", "Whats the error")
//        } else {
//            Log.d("SOCKET ID FROM INST", socketInstance.id())
//        }
        Log.d("ID INSIDE ON CLEAR", socketIdLD.value!!)
        socketInstance.on(Socket.EVENT_DISCONNECT) {
            Log.d("SOCKET ID FROM INST", socketInstance.id())

        }
        val socId = JSONObject()
        socId.put("socketId", socketIdLD.value!!)
        socketInstance.emit("userLeft", socId)
//        Log.d("SOCKETID", "onDestroy: ${mSocket.id()}")
//        socketInstance.close()
        socketInstance.disconnect()
        socketInstance.off()
        socketInstance.close()
    }


    fun getUsers() = viewModelScope.launch {

    }


}