package com.demo.todolist.presentation.main

import androidx.fragment.app.viewModels
import com.demo.todolist.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by viewModels()

}