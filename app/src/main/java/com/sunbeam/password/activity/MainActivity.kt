package com.sunbeam.password.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sunbeam.password.R
import com.sunbeam.password.adapter.PasswordAdapter
import com.sunbeam.password.entity.Account

class MainActivity : AppCompatActivity() {

//    private val accountList = listOf("Google", "Linkedin", "Twitter", "Facebook", "Instagram")

    private val accountList = ArrayList<Account>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerview.layoutManager = GridLayoutManager(this,1)
        recyclerview.adapter = PasswordAdapter(accountList,this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { addBottomSheet() }
    }

    fun addBottomSheet(){

    }
}