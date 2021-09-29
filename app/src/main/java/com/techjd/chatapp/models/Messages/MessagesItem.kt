package com.techjd.chatapp.models.Messages

data class MessagesItem(
    val body: String,
    val from: String,
    val fromObj: List<FromObj>,
    val to: String,
    val toObj: List<ToObj>
)