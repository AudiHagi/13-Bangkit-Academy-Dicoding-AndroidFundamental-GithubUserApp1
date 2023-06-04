package com.submission.dicoding.githubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.dicoding.githubuser.GSONFile.GithubUser
import com.submission.dicoding.githubuser.databinding.ItemUserBinding
import java.util.*

class UserAdapter(private val listUser: ArrayList<GithubUser>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class UserViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(user: GithubUser) {
            binding.tvUsername.text = user.login
            binding.tvId.text = user.type.lowercase(Locale.getDefault())
            Glide.with(itemView)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.ivProfile)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(
                listUser[holder.adapterPosition]
            )
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}
