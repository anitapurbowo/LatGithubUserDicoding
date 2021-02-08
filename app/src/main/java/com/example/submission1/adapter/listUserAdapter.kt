package com.example.submission1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.dataclass.GitHubUser
import com.example.submission1.R
import com.example.submission1.databinding.ItemlistBinding

class listUserAdapter (private val listUser:ArrayList<GitHubUser>) : RecyclerView.Adapter<listUserAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data : GitHubUser)
        fun onFavUserClicked(data : GitHubUser, position : Int)
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
                Glide
                    .with(binding.root)
                    .load(gitUser.image)
                    .placeholder(R.drawable.openimage)
                    .error(R.drawable.ic_baseline_block_24)
                    .into(imAvatar)

                cardAll.setOnClickListener {
                    onItemClickCallback?.onItemClicked(gitUser)
                }

                if (gitUser.fav==1)
                    ivFavUser.setImageResource(R.drawable.ic_baseline_star_24)
                else
                    ivFavUser.setImageResource(R.drawable.stargrey)

                ivFavUser.setOnClickListener {
                    onItemClickCallback?.onFavUserClicked(gitUser,adapterPosition)
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