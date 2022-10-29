package com.ensibuuko.android_dev_coding_assigment.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.domain.model.PostEntity
import com.ensibuuko.android_dev_coding_assigment.databinding.LayoutPostItemBinding
import timber.log.Timber

class PostListAdapter(
    private val clickListener: PostClickListener
) : ListAdapter<PostEntity, PostListAdapter.PostViewHolder>(PostDiffUtil) {

    object PostDiffUtil : DiffUtil.ItemCallback<PostEntity>() {
        override fun areItemsTheSame(
            oldItem: PostEntity,
            newItem: PostEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PostEntity,
            newItem: PostEntity
        ): Boolean = oldItem == newItem
    }

    inner class PostViewHolder(
        private val binding: LayoutPostItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bindEntity(entity: PostEntity) {
            with(binding) {

                entity.user?.let {
                    mtvUsername.text = "@${it.username}"
                    mtvAuthor.text = it.name
                }

                mtvBody.text = entity.body
                mtvTitle.text = entity.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val entity = getItem(position)
        holder.bindEntity(entity)
    }

    interface PostClickListener {
        fun onItemClicked(entity: PostEntity)
    }
}