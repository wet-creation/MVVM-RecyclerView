package com.example.recyclerview

import androidx.fragment.app.Fragment
import com.example.recyclerview.model.User


fun  Fragment.navigator(): FragmentNavigation {
    return requireActivity() as FragmentNavigation
}

interface FragmentNavigation {
    fun goBack()
    fun selectUser(userId: Int)
    fun showToast(messageRes: Int)
}
