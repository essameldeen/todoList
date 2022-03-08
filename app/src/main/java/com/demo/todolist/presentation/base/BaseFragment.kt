package com.demo.todolist.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.demo.todolist.presentation.loading.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment()
{
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        loading = LoadingDialog(requireActivity())
    }

    fun showLoading()
    {
        if (!loading.isShowing) loading.show()
    }

    fun dismissLoading()
    {
        if (loading.isShowing) loading.dismiss()
    }

    fun showError(error: String)
    {
        dismissLoading()
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }
    fun showMessage(message: String)
    {
        dismissLoading()
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}