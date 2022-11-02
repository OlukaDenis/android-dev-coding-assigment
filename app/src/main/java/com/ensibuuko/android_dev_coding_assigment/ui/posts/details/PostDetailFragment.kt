package com.ensibuuko.android_dev_coding_assigment.ui.posts.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.model.CommentEntity
import com.domain.model.PostEntity
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentPostDetailBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import com.ensibuuko.android_dev_coding_assigment.ui.home.HomeFragmentDirections
import com.ensibuuko.android_dev_coding_assigment.ui.posts.details.adapters.CommentListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PostDetailFragment :
    BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::inflate) {

    private val viewModel: PostDetailViewModel by viewModels()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleToolbar()
        populatePost()
        observeComments()

        args.selectedPost.let {
            viewModel.fetchRemoteComments(it.id)
            viewModel.getLocalComments(it.id)
        }

        with(binding) {
            etCommentBody.doOnTextChanged { text, _, _, _ ->
                flSend.isVisible = text.toString().length >= 2
            }

            flSend.setOnClickListener { validateComment() }
        }
    }


    private fun handleToolbar() {
        with(binding.layoutToolbar) {
            val user = args.selectedPost.user
            user?.let {
                mtvTitle.text = "${it.name}'s post"
            }

            btnBack.isVisible = true
            btnBack.setOnClickListener { navigateUp() }
        }
    }

    private fun validateComment() {
        with(binding) {
            when (viewModel.commentType) {
                PostDetailViewModel.CommentType.COMMENT -> {
                    viewModel.saveComment(etCommentBody.text.toString(), args.selectedPost)
                    etCommentBody.setText("")
                }

                PostDetailViewModel.CommentType.EDIT -> {
                    viewModel.selectedComment?.let {
                        it.body = etCommentBody.text.toString()
                        viewModel.updateComment(it)
                        etCommentBody.setText("")
                    }
                }
            }
        }
    }

    private fun observeComments() {
        viewModel.commentListState.observe(viewLifecycleOwner) { list ->
            list?.let {
                populateComments(it)
            }
        }
    }

    private fun populateComments(list: List<CommentEntity>) {
        with(binding) {
            val listAdapter = CommentListAdapter(
                viewModel.getUser,
                object : CommentListAdapter.CommentClickListener {
                    override fun onItemClicked(entity: CommentEntity) {

                    }

                    override fun onMenuClicked(view: View, entity: CommentEntity) {
                        showPostOptions(view, entity)
                    }
                })

            rvComments.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            listAdapter.submitList(list)
        }
    }

    private fun populatePost() {
        val entity = args.selectedPost
        with(binding) {
            entity.user?.let {
                mtvUsername.text = "@${it.username}"
                mtvAuthor.text = it.name

                civPostProfile.setOnClickListener { v ->
                    navigate(PostDetailFragmentDirections.actionPostDetailFragmentToProfileFragment(it))
                }
            }

            mtvBody.text = entity.body
            mtvPostTitle.text = entity.title
        }
    }

    private fun showPostOptions(view: View, entity: CommentEntity) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.post_menu, popupMenu.menu)

        val menu = popupMenu.menu
        menu.removeItem(R.id.action_view_post)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_view_post -> {}

                R.id.action_edit_post -> {
                    viewModel.commentType = PostDetailViewModel.CommentType.EDIT
                    viewModel.selectedComment = entity
                    binding.etCommentBody.setText(entity.body)
                }

                R.id.action_delete_post -> {
                    viewModel.deleteComment(entity)
                }

                else -> {}
            }
            true
        }
        popupMenu.show()
    }

}