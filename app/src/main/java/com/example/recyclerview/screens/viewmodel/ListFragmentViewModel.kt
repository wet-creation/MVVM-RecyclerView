package com.example.recyclerview.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.model.User
import com.example.recyclerview.model.UserController
import com.example.recyclerview.model.UsersChangeListener

class ListFragmentViewModel(
    val controller: UserController

) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users : LiveData<List<User>> = _users
    private val usersChangeListener: UsersChangeListener = {
        _users.value = it
    }
    init {
        loadUser()
    }

    override fun onCleared() {
        super.onCleared()
        controller.deleteListener(usersChangeListener)
    }
    fun loadUser(){
        controller.addListener(usersChangeListener)
    }

    fun deleteUser(user: User) {
        controller.deleteUser(user)
    }

    fun moveUser(user: User, moveTo: Int) {
        controller.moveUser(user,moveTo)
    }

}