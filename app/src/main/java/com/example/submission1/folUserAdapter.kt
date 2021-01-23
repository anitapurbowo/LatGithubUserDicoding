package com.example.submission1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.databinding.ItemlistBinding

class folUserAdapter (private val listUser:ArrayList<GitHubUser>) : RecyclerView.Adapter<folUserAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data : GitHubUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder (private val binding: ItemlistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gitUser : GitHubUser) {
            with(binding) {
                tvNama.text = gitUser.nama
                tvCompany.text = gitUser.company
                tvRepos.text = gitUser.repository
                Glide.with(binding.root).load(gitUser.image).into(imAvatar)
                //imAvatar.setImageResource(gitUser.image)
                cardAll.setOnClickListener {
                    //Toast.makeText(itemView.context, "Favorite ${gitUser.nama}", Toast.LENGTH_SHORT).show()
                    onItemClickCallback?.onItemClicked(gitUser)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}