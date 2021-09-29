package com.techjd.chatapp.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techjd.chatapp.ui.ChatViewModel
import io.socket.client.Socket

class ChatViewModelProviderFactory(var socketInstance: Socket) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(socketInstance) as T
    }
}