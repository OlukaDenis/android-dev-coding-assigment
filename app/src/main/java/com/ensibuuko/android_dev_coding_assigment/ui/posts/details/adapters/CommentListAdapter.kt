package com.ensibuuko.android_dev_coding_assigment.ui.posts.details.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.domain.model.CommentEntity
import com.domain.model.UserEntity
import com.ensibuuko.android_dev_coding_assigment.databinding.LayoutLeftCommentItemBinding
import com.ensibuuko.android_dev_coding_assigment.databinding.LayoutRightCommentItemBinding

class CommentListAdapter(
    private val currentUser: UserEntity,
    private val clickListener: CommentClickListener
) : ListAdapter<CommentEntity, RecyclerView.ViewHolder>(CommentDiffUtil) {

    companion object {

        private const val LEFT_COMMENT = 1
        private const val RIGHT_COMMENT = 2

        object CommentDiffUtil : DiffUtil.ItemCallback<CommentEntity>() {
            override fun areItemsTheSame(
                oldItem: CommentEntity,
                newItem: CommentEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CommentEntity,
                newItem: CommentEntity
            ): Boolean = oldItem == newItem
        }

    }
    inner class RightCommentViewHolder(
        private val binding: LayoutRightCommentItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindEntity(entity: CommentEntity) {
            with(binding) {

                mtvBody.text = entity.body
                mtvEmail.text = entity.email

                civMenu.setOnClickListener { v -> clickListener.onMenuClicked(v, entity) }
            }
        }
    }

    inner class LeftCommentViewHolder(
        private val binding: LayoutLeftCommentItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindEntity(entity: CommentEntity) {
            with(binding) {
                mtvBody.text = entity.body
                mtvEmail.text = entity.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RIGHT_COMMENT -> RightCommentViewHolder(
                LayoutRightCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> LeftCommentViewHolder(
                LayoutLeftCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val entity = getItem(position)
        return if (entity.email == currentUser.email) {
            RIGHT_COMMENT
        } else {
            LEFT_COMMENT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = getItem(position)

        when (holder) {
            is LeftCommentViewHolder -> {
                holder.bindEntity(entity)
            }

            is RightCommentViewHolder -> {
                holder.bindEntity(entity)
            }
        }
    }

    interface CommentClickListener {
        fun onItemClicked(entity: CommentEntity)
        fun onMenuClicked(view: View, entity: CommentEntity)
    }
}