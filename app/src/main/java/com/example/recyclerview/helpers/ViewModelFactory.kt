package com.example.recyclerview.helpers

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerview.App
import com.example.recyclerview.screens.viewmodel.DetailsFragmentViewModel
import com.example.recyclerview.screens.viewmodel.ListFragmentViewModel

class ViewModelFactory(
    private val app: App
) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            ListFragmentViewModel::class.java -> {
                ListFragmentViewModel(app.userController)
            }
            DetailsFragmentViewModel::class.java -> {
                DetailsFragmentViewModel(app.userController)
            }
            else -> throw java.lang.IllegalArgumentException("Unknown viewModel")
        }
        return viewModel as T
    }
}
fun Fragment.factory() : ViewModelFactory {
    return  ViewModelFactory(requireContext().applicationContext as App)
}