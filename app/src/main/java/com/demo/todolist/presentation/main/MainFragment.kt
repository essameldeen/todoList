package com.demo.todolist.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.todolist.data.model.Task
import com.demo.todolist.databinding.FragmentMainBinding
import com.demo.todolist.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(), TasksAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var _binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)

        initView()
        return _binding.root
    }

    private fun initView() {
        val tasksAdapter = TasksAdapter(this@MainFragment)
        _binding.rvTasks.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onItemClick(task: Task) {
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
    }

}