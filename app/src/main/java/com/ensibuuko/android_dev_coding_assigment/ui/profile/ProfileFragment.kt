package com.ensibuuko.android_dev_coding_assigment.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.model.PostEntity
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentProfileBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseUiSate
import com.ensibuuko.android_dev_coding_assigment.ui.home.HomeFragmentDirections
import com.ensibuuko.android_dev_coding_assigment.ui.home.adapters.PostListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val args: ProfileFragmentArgs by navArgs()
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLocalUserPosts(args.selectedUser.id)

        observeDeletePost()
        observeLocalPosts()

        handleToolbar()
    }

    private fun handleToolbar() {
        with(binding.layoutToolbar) {
            args.selectedUser.let {
                mtvTitle.text = if (it.id == viewModel.getUser.id) getString(R.string.my_profile) else "${it.name}'s Profile"
            }

            btnBack.isVisible = true
            btnBack.setOnClickListener { navigateUp() }
        }
    }

    private fun observeLocalPosts() {
        viewModel.postListState.observe(viewLifecycleOwner) { list ->
            list?.let {
                populateUI(it)
            }
        }
    }


    private fun observeDeletePost() {
        viewModel.deleteState.observe(viewLifecycleOwner) {
            when(it) {
                is BaseUiSate.Success<*> -> {
                    hideProgressDialog()
                    showSuccessAlert("Post deleted successfully")
                }

                is BaseUiSate.Error -> {
                    hideProgressDialog()
                    showErrorAlert(it.message)
                }

                is BaseUiSate.Loading -> {
                    showProgressDialog()
                }
            }
        }
    }

    private fun populateUI(posts: List<PostEntity>) {
        with(binding) {

            args.selectedUser.let {
                mtvAuthor.text = it.name
                mtvUsername.text = "@${it.username}"
                mtvEmail.text = it.email
                mtvPhone.text = it.phone
            }


            val listAdapter = PostListAdapter(object : PostListAdapter.PostClickListener {
                override fun onItemClicked(entity: PostEntity) {
                    goToPost(entity)
                }

                override fun onMenuClicked(view: View, entity: PostEntity) {
                    showPostOptions(view, entity)
                }

                override fun onProfileClicked(entity: PostEntity) {
                    entity.user?.let {
                        navigate(
                            ProfileFragmentDirections.actionProfileFragmentSelf(it)
                        )
                    }
                }
            })

            rvPosts.apply {
                this.adapter = listAdapter
                this.layoutManager = LinearLayoutManager(requireContext())
            }

            listAdapter.submitList(posts)
        }
    }

    private fun goToPost(entity: PostEntity) {
        navigate(
            ProfileFragmentDirections.actionProfileFragmentToPostDetailFragment(entity)
        )
    }

    private fun showPostOptions(view: View, entity: PostEntity) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.post_menu, popupMenu.menu)

        val menu = popupMenu.menu

        val currentUser = viewModel.getUser
        if (entity.userId != currentUser.id) {
            menu.removeItem(R.id.action_delete_post)
            menu.removeItem(R.id.action_edit_post)
        }

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_view_post -> {
                    goToPost(entity)
                }

                R.id.action_edit_post -> {
                    navigate(
                        ProfileFragmentDirections.actionProfileFragmentToAddNewPostFragment(
                            true,
                            entity
                        )
                    )
                }

                R.id.action_delete_post -> {
                    viewModel.deletePost(entity)
                }

                else -> {}
            }
            true
        }
        popupMenu.show()
    }
}