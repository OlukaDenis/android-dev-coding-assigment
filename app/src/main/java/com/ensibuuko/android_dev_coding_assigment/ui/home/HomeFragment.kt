package com.ensibuuko.android_dev_coding_assigment.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.model.PostEntity
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentHomeBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import com.ensibuuko.android_dev_coding_assigment.ui.home.adapters.PostListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchRemotePosts()
        viewModel.getLocalPosts()

        observeLocalPosts()
        handleToolbar()

        binding.mtvWhatOnMind.setOnClickListener {
            navigate(
                HomeFragmentDirections.actionHomeFragmentToAddNewPostFragment()
            )
        }
    }

    private fun handleToolbar() {
        with(binding.layoutToolbar) {
            mtvTitle.text = getString(R.string.home)
        }
    }

    private fun observeLocalPosts() {
        viewModel.postListState.observe(viewLifecycleOwner) { list ->
            list?.let {
                populateUI(it)
            }
        }
    }

    private fun populateUI(posts: List<PostEntity>) {
        with(binding) {
            val listAdapter = PostListAdapter(object : PostListAdapter.PostClickListener {
                override fun onItemClicked(entity: PostEntity) {

                }

                override fun onMenuClicked(view: View, entity: PostEntity) {
                    showPostOptions(view, entity)
                }
            })

            rvPosts.apply {
                this.adapter = listAdapter
                this.layoutManager = LinearLayoutManager(requireContext())
            }

            listAdapter.submitList(posts)
        }
    }

    private fun showPostOptions(view: View, entity: PostEntity) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.post_menu, popupMenu.menu)

        val menu = popupMenu.menu

        val currentUser = viewModel.getUser
        if (entity.userId != currentUser.id) {
            menu.removeItem(R.id.action_delete_post)
        }

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_view_post -> {
                    Timber.d("View post")
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