package com.sunbeam.password.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sunbeam.password.R
import com.sunbeam.password.entity.Account
import com.sunbeam.password.utility.CryptoUtils
import com.sunbeam.password.utility.KeystoreUtils

class PasswordAdapter(private var accountList:List<Account>, private var context: Context)
    : RecyclerView.Adapter<PasswordAdapter.AccountViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_pass,null)
        return AccountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        var account = accountList[position]
        holder.accType.text = account.acc_type

        holder.cardView.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(context)
            val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_detail_dialogue,null)
            bottomSheetDialog.setContentView(bottomSheetView)

            val acc_type = bottomSheetView.findViewById<TextView>(R.id.account_type)
            val email = bottomSheetView.findViewById<TextView>(R.id.email)
            val pass = bottomSheetView.findViewById<TextView>(R.id.password)
            val imgView = bottomSheetView.findViewById<ImageView>(R.id.password_visibility_toggle)

            acc_type.text = account.acc_type
            email.text = account.acc

            var n = 0
            val key = KeystoreUtils.getKey()
            var decryptedPass = ""
            if(key != null){
                decryptedPass = CryptoUtils.decrypt(account.pass,key)
            }
            imgView.setOnClickListener {
                if(n == 0) {

                    pass.text = decryptedPass
                    n++
//                    Log.e("Pass","pass if " + n)
                } else if(n == 1){
                    pass.text = "********"
                    n++
//                    Log.e("Pass","pass else " + n)
                }
                n %= 2
            }

            bottomSheetDialog.show()
        }
    }

    inner class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val accType : TextView
        val pass : TextView
        val cardView : CardView
        init {
            accType = itemView.findViewById(R.id.acc_type)
            pass = itemView.findViewById(R.id.pass)
            cardView = itemView.findViewById(R.id.cardview)
        }
    }
}