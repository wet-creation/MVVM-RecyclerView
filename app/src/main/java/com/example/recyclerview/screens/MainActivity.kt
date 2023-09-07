package com.example.recyclerview.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.FragmentNavigation
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.model.User

class MainActivity : AppCompatActivity(), FragmentNavigation {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,ListFragment())
                .commit()

        }

    }
    override fun goBack() {
       onBackPressed()
    }

    override fun selectUser(userId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,DetailsFragment.newInstance(userId))
            .addToBackStack(null)
            .commit()
    }

    override fun showToast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}