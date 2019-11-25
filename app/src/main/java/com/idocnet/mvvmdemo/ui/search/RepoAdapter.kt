package com.idocnet.mvvmdemo.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.idocnet.mvvmdemo.R
import com.idocnet.mvvmdemo.api.response.Repo
import kotlinx.android.synthetic.main.item_user.view.*



class RepoAdapter(private val clickListener: (Repo) -> Unit): ListAdapter<Repo, RepoAdapter.ViewHolder>(repoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(inflate.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, clickListener)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repo: Repo, clickListener: (Repo) -> Unit) {
            itemView.tvName.text = repo.description
            itemView.setOnClickListener { clickListener(repo) }
        }
    }

    companion object {
        val repoDiffCallback = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.description == newItem.description
                        && oldItem.stars == newItem.stars
            }

        }
    }

}
