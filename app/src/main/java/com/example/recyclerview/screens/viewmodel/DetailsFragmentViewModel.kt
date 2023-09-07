package com.example.recyclerview.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.helpers.UserNotFoundException
import com.example.recyclerview.model.User
import com.example.recyclerview.model.UserController
import com.example.recyclerview.model.UserDetails

class DetailsFragmentViewModel(
    val controller: UserController
) : ViewModel() {
    private val _user = MutableLiveData<UserDetails>()
    val user : LiveData<UserDetails> = _user

    fun loadUser(userId: Int){
        if(user.value != null) return
        try {
           val userDetails = controller.getById(userId)
            _user.value = userDetails
        }
        catch (e : UserNotFoundException){
            //todo
        }

    }

    fun deleteUser(){
        val userDetails = user.value ?: return
        controller.deleteUser(userDetails.user)
    }
}