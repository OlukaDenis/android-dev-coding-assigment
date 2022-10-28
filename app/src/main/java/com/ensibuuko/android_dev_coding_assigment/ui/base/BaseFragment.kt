package com.ensibuuko.android_dev_coding_assigment.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.utils.ProgressUtils
import com.ensibuuko.android_dev_coding_assigment.utils.ToastUtils
import com.ensibuuko.android_dev_coding_assigment.utils.ToastUtils.showAlert

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private lateinit var progressUtils: ProgressUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressUtils = ProgressUtils(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    protected fun showProgressDialog() {
        progressUtils.showDialog()
    }

    protected fun hideProgressDialog() {
        progressUtils.hideDialog()
    }

    fun showErrorAlert(message: String) {
        showAlert(getString(R.string.error), message, ToastUtils.TYPE.FAILURE)
    }

    fun showInfoAlert(message: String) {
        showAlert(getString(R.string.info), message, ToastUtils.TYPE.INFO)
    }

    fun showSuccessAlert(message: String) {
        showAlert(getString(R.string.success), message, ToastUtils.TYPE.SUCCESS)
    }

    protected fun navigate(direction: NavDirections) = findNavController().navigate(direction)

    protected fun navigateClearBackstack(destination: Int, bundle: Bundle?) =
        findNavController().navigate(
            destination,
            bundle,
            NavOptions.Builder().setPopUpTo(destination, true).build()
        )

    protected fun navigateUp() = findNavController().navigateUp()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}