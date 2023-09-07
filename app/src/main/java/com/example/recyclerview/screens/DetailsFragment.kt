package com.example.recyclerview.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.recyclerview.R
import com.example.recyclerview.helpers.viewBinding
import com.example.recyclerview.databinding.FragmentDetailsBinding
import com.example.recyclerview.helpers.factory
import com.example.recyclerview.navigator
import com.example.recyclerview.screens.viewmodel.DetailsFragmentViewModel
import org.apache.commons.lang3.StringUtils.isNotBlank

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding<FragmentDetailsBinding>()
    private val viewModel: DetailsFragmentViewModel by viewModels { factory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser(requireArguments().getInt(ARGS_ID_USER))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deleteButton.setOnClickListener {
            viewModel.deleteUser()
            navigator().showToast(R.string.user_deleting)
            navigator().goBack()
        }
        viewModel.user.observe(viewLifecycleOwner) {user->
            binding.userNameTextView.text = user.user.name
            binding.userDetailsTextView.text = user.details
            if (user.user.image.isNotBlank()) {
                Glide.with(this).load(user.user.image)
                    .circleCrop()
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(binding.photoImageView)
            } else {
                Glide.with(this).clear(binding.photoImageView)
                binding.photoImageView.setImageResource(R.drawable.ic_profile)
            }
        }
    }


    companion object {
        private const val ARGS_ID_USER = "idUser"
        fun newInstance(userId: Int) :DetailsFragment{
            val fragment = DetailsFragment()
            fragment.arguments = bundleOf(ARGS_ID_USER to userId)
            return fragment
        }

    }
}