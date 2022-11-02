package com.ensibuuko.android_dev_coding_assigment.ui.posts.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.domain.model.PostEntity
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentAddNewPostBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseUiSate
import com.ensibuuko.android_dev_coding_assigment.utils.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewPostFragment :
    BaseFragment<FragmentAddNewPostBinding>(FragmentAddNewPostBinding::inflate) {

    private val viewModel: AddPostViewModel by viewModels()
    private val args: AddNewPostFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleToolbar()

        with(binding) {
            btnAddPost.setOnClickListener {
                validateFields()
            }
        }

        if (args.isUpdate) {
            populatePost()
        }

        observeAddPost()
    }

    private fun populatePost() {
        with(binding) {
            args.selectedPost?.let {
                etBody.setText(it.body)
                etTitle.setText(it.title)
            }
        }
    }

    private fun handleToolbar() {
        with(binding.layoutToolbar) {
            mtvTitle.text = if (args.isUpdate) getString(R.string.update_post) else getString(R.string.new_post)
            btnBack.isVisible = true
            civBack.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_close
                )
            )
            btnBack.setOnClickListener { navigateUp() }

            binding.btnAddPost.text = if (args.isUpdate) getString(R.string.update) else getString(R.string.add_post)
        }
    }

    private fun observeAddPost() {
        viewModel.addPostState.observe(viewLifecycleOwner) {
            when(it) {
                is BaseUiSate.Loading -> {
                    showProgressDialog()
                }
                is BaseUiSate.Success<*> -> {
                    hideProgressDialog()
                    showSuccessAlert(if (args.isUpdate) "Post updated successfully" else "Post added successfully")
                    navigateUp()
                }
                is BaseUiSate.Error -> {
                    showErrorAlert(it.message)
                    hideProgressDialog()
                }
            }
        }
    }

    private fun validateFields() {
        with(binding) {
            when {
                !Validator.isRequired(etTitle.text.toString()) || !Validator.isRequired(etTitle.text.toString()) -> {
                    showInfoAlert("All fields required")
                }

                else -> {
                    if (args.isUpdate) {
                       args.selectedPost?.let {
                           it.title = etTitle.text.toString().trim()
                           it.body = etBody.text.toString().trim()

                           viewModel.updatePost(it)
                       }
                    } else {
                        viewModel.addPost(etTitle.text.toString().trim(), etBody.text.toString().trim())
                    }
                }
            }
        }
    }
}