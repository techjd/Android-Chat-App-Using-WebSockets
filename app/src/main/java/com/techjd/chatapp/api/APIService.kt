package com.techjd.chatapp.api


import com.techjd.chatapp.models.ReturnedRes.UserRes
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @FormUrlEncoded
    @POST("api/v1/user/registerUser")
    fun registerUser(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ): Call<UserRes>

    @FormUrlEncoded
    @POST("api/v1/user/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserRes>

    @GET("api/v1/user/me")
    fun getLoggedInUserInfo(
        @Header("token") token: String
    ): Call<User>

    @GET("getChatUsers")
    fun getChatUsers(
        @Header("token") token: String
    ): Call<com.techjd.chatapp.models.UsersList.User>
}