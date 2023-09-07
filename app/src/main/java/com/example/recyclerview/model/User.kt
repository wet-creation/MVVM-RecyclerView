package com.example.recyclerview.model

data class User(val id: Int, val name: String, val job:String, val image:String)
data class UserDetails(val user: User, val details:String)