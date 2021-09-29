package com.techjd.chatapp.models.NewMessage

data class NewMessageItem(
    val __v: Int,
    val _id: String,
    val body: String,
    val createdAt: String,
    val from: From,
    val to: To,
    val updatedAt: String
)