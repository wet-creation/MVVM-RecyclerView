package com.example.recyclerview

import android.app.Application
import com.example.recyclerview.model.UserController

class App : Application() {
    val userController = UserController()
}