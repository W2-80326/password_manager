package com.sunbeam.password.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.sunbeam.password.R
import com.sunbeam.password.adapter.PasswordAdapter
import com.sunbeam.password.dbHelper.AccountDbHelper
import com.sunbeam.password.entity.Account
import com.sunbeam.password.utility.CryptoUtils
import com.sunbeam.password.utility.KeystoreUtils
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {

//    private val accountList = listOf("Google", "Linkedin", "Twitter", "Facebook", "Instagram")

    private val accountList = ArrayList<Account>()
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter : PasswordAdapter
    private lateinit var key : SecretKey

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerView)
        recyclerview.layoutManager = GridLayoutManager(this,1)
        adapter = PasswordAdapter(accountList,this)
        recyclerview.adapter = adapter

        KeystoreUtils.generateKey()
        key = KeystoreUtils.getKey()!!

        getAllData()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { addBottomSheet() }
    }

    fun addBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_add_dialogue,null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val accounts = bottomSheetView.findViewById<Spinner>(R.id.accounts)
        val email = bottomSheetView.findViewById<TextInputLayout>(R.id.email)
        val pass = bottomSheetView.findViewById<TextInputLayout>(R.id.password)
        val add_btn = bottomSheetView.findViewById<Button>(R.id.add_button)
        var accountType = ""
        val adapter = ArrayAdapter.createFromResource(this,R.array.account_type,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accounts.adapter = adapter
        val defaultValue = "Select"
        val spinnerPosition = adapter.getPosition(defaultValue)
        accounts.setSelection(spinnerPosition)

        accounts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()

                accountType = item

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        add_btn.setOnClickListener {
            val emailValue = email.editText?.text.toString()
            val passValue = pass.editText?.text.toString()

            if (accountType == defaultValue) {
                Toast.makeText(this, "Please select a valid account type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val account = Account()
            account.acc_type = accountType
            account.acc = emailValue
            if(key != null){
                val encryptedPass = CryptoUtils.encrypt(passValue,key)
                account.pass = encryptedPass
            }
            addAccount(account)

            // Add the new account to the list and notify the adapter
            accountList.add(account)
            recyclerview.adapter?.notifyItemInserted(accountList.size - 1)

            bottomSheetDialog.dismiss() // Dismiss the dialog after adding the account
        }

        bottomSheetDialog.show()
    }

    fun addAccount(account: Account){
        val dbHelper = AccountDbHelper(this)
        val num = dbHelper.insertAcc(account)
        Log.e("Main","account added : "+num)
    }

    fun getAllData(){
        val dbHelper = AccountDbHelper(this)
        val accounts = dbHelper.getDatas()
        accountList.clear()
        accountList.addAll(accounts)
        adapter.notifyDataSetChanged()
    }
}