package com.example.recyclerview.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.R
import com.example.recyclerview.UserOperation
import com.example.recyclerview.UsersAdapter
import com.example.recyclerview.helpers.viewBinding
import com.example.recyclerview.databinding.FragmentListBinding
import com.example.recyclerview.helpers.factory
import com.example.recyclerview.model.User
import com.example.recyclerview.model.UsersChangeListener
import com.example.recyclerview.navigator
import com.example.recyclerview.screens.viewmodel.ListFragmentViewModel

class ListFragment : Fragment(R.layout.fragment_list) {
    private val binding by viewBinding<FragmentListBinding>()
    private lateinit var adapter: UsersAdapter
    private val viewModel: ListFragmentViewModel by viewModels{ factory() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UsersAdapter(object : UserOperation {
            override fun onDeleteUser(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onMoveUser(user: User, moveTo: Int) {
                viewModel.moveUser(user, moveTo)
            }

            override fun onDetailsUser(user: User) {
                navigator().selectUser(user.id)
            }


        })
        viewModel.users.observe(viewLifecycleOwner){
            adapter.users = it
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }
}