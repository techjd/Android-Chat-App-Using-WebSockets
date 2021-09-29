package com.techjd.chatapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techjd.chatapp.api.ChatMessageItem
import com.techjd.chatapp.databinding.ItemChatBinding
import com.techjd.chatapp.databinding.ItemTextOtherUserBinding
import com.techjd.chatapp.databinding.ItemTextUserBinding
import com.techjd.chatapp.models.Messages.MessagesItem
import kotlin.math.log


class ChatAdapter(val id: String?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var messageList: ArrayList<MessagesItem> = arrayListOf<MessagesItem>()

    inner class UserViewHolder(val binding: ItemTextUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val text = messageList[position]
            binding.tvMessage.text = text.body
        }
    }

    inner class OtherViewHolder(val binding: ItemTextOtherUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val text = messageList[position]
            binding.tvMessage.text = text.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return UserViewHolder(
                ItemTextUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return OtherViewHolder(
                ItemTextOtherUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.d("FROM OBJ ID", "${messageList[position].fromObj[0]._id} and $id")
        if (messageList[position].from == id) {
            (holder as UserViewHolder).bind(position)
        } else {
            (holder as OtherViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (messageList[position].from == id) {
            return 0
        } else {
            return 1
        }
    }

    fun setList(messageList: ArrayList<MessagesItem>) {
        Log.d("DATA BEFORE", "setData: $messageList ")

        this.messageList = messageList
        Log.d("DATA AFTER", "setData: $messageList ")

        notifyDataSetChanged()
    }

    fun setData(msg: MessagesItem) {
//        messageList.add
        Log.d("DATA BEFORE", "setData: $messageList ")
        messageList.add(msg)
        Log.d("DATA AFTER", "setData: $messageList ")
//        notifyItemInserted(messageList.size - 1)
//        notifyDataSetChanged()
    }
}
