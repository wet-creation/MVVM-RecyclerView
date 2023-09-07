package com.example.recyclerview

import com.example.recyclerview.model.User

interface UserOperation {
    fun onDeleteUser(user: User)
    fun onMoveUser(user: User, moveTo:Int)
    fun onDetailsUser(user: User)
}