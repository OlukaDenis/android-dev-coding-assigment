package com.ensibuuko.android_dev_coding_assigment.ui.posts.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentPostDetailBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::inflate) {

    private val viewModel: PostDetailViewModel by viewModels()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleToolbar()
        populatePost()
    }

    private fun populatePost() {
        val entity = args.selectedPost
        with(binding) {
            entity.user?.let {
                mtvUsername.text = "@${it.username}"
                mtvAuthor.text = it.name
            }

            mtvBody.text = entity.body
            mtvPostTitle.text = entity.title

        }
    }

    private fun handleToolbar() {
        with(binding.layoutToolbar) {

            Timber.d("USer: ${args.selectedPost.user}")
            val user = args.selectedPost.user
            user?.let {
                mtvTitle.text = "${it.name}'s post"
            }

            btnBack.isVisible = true
            btnBack.setOnClickListener { navigateUp() }
        }
    }


}