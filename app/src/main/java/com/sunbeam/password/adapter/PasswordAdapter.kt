package com.sunbeam.password.adapter

import android.content.Context
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
        holder.acc.text = account.acc
        holder.pass.text = account.pass

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
            pass.text = account.pass



        }
    }

    inner class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val acc : TextView
        val pass : TextView
        val cardView : CardView
        init {
            acc = itemView.findViewById(R.id.acc)
            pass = itemView.findViewById(R.id.pass)
            cardView = itemView.findViewById(R.id.cardview)
        }
    }
}