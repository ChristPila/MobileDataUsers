package com.odc.finalappodc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.odc.finalappodc.MainActivity
import com.odc.finalappodc.R
import com.odc.finalappodc.databinding.DispositionLigneBinding
import com.odc.finalappodc.model.User

class UserAdapter(val ctx: MainActivity, val userData: List<User>) :
        RecyclerView.Adapter<UserAdapter.MainViewHolder>() {

        inner class MainViewHolder(val binding: DispositionLigneBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bindItem(user: User) {
                // attacher le valeur aux vuex avec item

                binding?.prenom.setText("${user.first_name}")
                binding?.email.setText("${user.email}")


                binding.root.setOnClickListener {
                    //user for readen profile
                }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return MainViewHolder(DispositionLigneBinding.inflate(inflater, parent, false))
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val user = userData[position]
            holder.bindItem(user)
        }

        override fun getItemCount(): Int {
            return userData.size
        }
}