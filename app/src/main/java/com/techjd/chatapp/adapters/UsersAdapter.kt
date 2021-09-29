package com.techjd.chatapp.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.techjd.chatapp.ChatApplication
import com.techjd.chatapp.databinding.ItemChatListBinding
import com.techjd.chatapp.databinding.ItemUsersBinding
import com.techjd.chatapp.models.Users
import com.techjd.chatapp.ui.fragment.ChatsFragmentDirections
import io.socket.client.Socket

class UsersAdapter(private val context: Context, private val id: String?) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var userList = emptyList<Users>()
    private lateinit var mSocket: Socket
    private lateinit var sharedPreferences: SharedPreferences


    val app: ChatApplication = context.applicationContext as ChatApplication

    inner class UsersViewHolder(val binding: ItemChatListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemChatListBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        val user = userList[position]
//        Log.d("IN BIND", "ID IS ${id} and userSocket is ${user.socketId} ")
//        if (id == user.socketId) {
//            holder.binding.usersCardView.visibility = View.GONE
//        } else {
        if (id == user.userId._id) {
            holder.binding.tvProfileName.text = "${user.userId.name} (YOU)"
            holder.binding.tvEmail.text = user.userId.email
            holder.binding.layoutChatList.setOnClickListener {
                Toast.makeText(context, "Can't Chat With Yourselft", Toast.LENGTH_SHORT).show()

//                it.findNavController().navigate(
//                    ChatsFragmentDirections.actionChatsFragmentToChattingFragment(
//                        user.socketId,
//                        user._id,
//                        user.userId._id
////                    user.userId
//                    )
//                )
            }

//            Toast.makeText(context, user.socketId, Toast.LENGTH_LONG).show()
//            val intent = Intent(context, ChatActivity::class.java)
//            intent.putExtra("socket", user.socketId)
//            intent.putExtra("name", user._id)
//            intent.putExtra("to", user.userId)
//            context.startActivity(intent)
        } else {
            holder.binding.tvProfileName.text = user.userId.name
            holder.binding.tvEmail.text = user.userId.email
            holder.binding.layoutChatList.setOnClickListener {

                it.findNavController().navigate(
                    ChatsFragmentDirections.actionChatsFragmentToChattingFragment(
                        user.socketId,
                        user._id,
                        user.userId._id,
                        user.userId.name,
                        user.userId.email
//                    user.userId
                    )
                )
            }
        }
//        }

//        with(holder) {
//            with(userList[position])
//            {
//                if (id == this.socketId) {
//                    binding.usersCardView.visibility = View.GONE
//                } else {
//                    binding.userName.text = this._id
//                    binding.socketId.text = this.socketId
//                    binding.usersCardView.setOnClickListener {
//                        Toast.makeText(context, this.socketId, Toast.LENGTH_LONG).show()
//                        val intent = Intent(context, ChatActivity::class.java)
//                        intent.putExtra("socket", this.socketId)
//                        intent.putExtra("name", this._id)
//                        intent.putExtra("to", this.userId)
//                        context.startActivity(intent)
//                    }
//                }
//
//            }
//        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(userList: ArrayList<Users>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}