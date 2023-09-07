package com.example.recyclerview

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ItemUserBinding
import com.example.recyclerview.model.User

class UsersAdapter(
    private val userOperation: UserOperation
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>(), OnClickListener {
    var users: List<User> = emptyList()
        set(param) {
            field = param
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        binding.btnMoreOption.setOnClickListener(this)
        binding.root.setOnClickListener(this)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            tvName.text = user.name
            tvJob.text = user.job
            holder.itemView.tag = user
            btnMoreOption.tag = user
            if (user.image.isNotBlank()) {
                Glide.with(imgProfile.context).load(user.image)
                    .circleCrop()
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(imgProfile)
            } else {
                Glide.with(imgProfile.context).clear(imgProfile)
                imgProfile.setImageResource(R.drawable.ic_profile)
            }

        }
    }


    class UserViewHolder(
        var binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnMoreOption -> {
                showPopUp(v)
            }
            R.id.clItem -> userOperation.onDetailsUser(v.tag as User)
        }
    }

    private fun showPopUp(v: View) {
        val popUpMenu = PopupMenu(v.context, v)
        val user = v.tag as User
        val position = users.indexOfFirst { user.id == it.id }
        popUpMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, "Move up").apply {
            isEnabled = position > 0
        }
        popUpMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")
        popUpMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, "Move down").apply {
            isEnabled = position < users.size - 1
        }

        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    userOperation.onMoveUser(user, -1)
                }
                ID_REMOVE -> {
                    userOperation.onDeleteUser(user)
                }
                ID_MOVE_DOWN -> {
                    userOperation.onMoveUser(user, 1)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popUpMenu.show()
    }

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 3
        private const val ID_REMOVE = 2
    }
}
