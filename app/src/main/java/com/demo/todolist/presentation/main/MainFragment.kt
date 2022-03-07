package com.demo.todolist.presentation.main

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.todolist.R
import com.demo.todolist.data.model.Task
import com.demo.todolist.databinding.FragmentMainBinding
import com.demo.todolist.presentation.base.BaseFragment
import com.demo.todolist.presentation.utils.SortedState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment(), TasksAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var _binding: FragmentMainBinding
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        initView()
        setHasOptionsMenu(true)
        return _binding.root
    }

    private fun initView() {
        tasksAdapter = TasksAdapter(this@MainFragment)
        _binding.rvTasks.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        mainViewModel.tasks.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                tasksAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.taskEvent.collect { taskEvent ->
                when (taskEvent) {
                    is TaskEvent.ShowUndoMessage -> showSnackBar(taskEvent.task)
                }
            }
        }
    }


    private fun initListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mainViewModel.setUpSearchValue(newText)
                return false
            }
        })
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = tasksAdapter.currentList[viewHolder.adapterPosition]
                mainViewModel.onTaskSwiped(task)
            }

        }).attachToRecyclerView(_binding.rvTasks)

        _binding.fabAddTask.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.navigateToCreateTaskFragment())
        }
    }

    private fun showSnackBar(task: Task) {
        Snackbar.make(requireView(), "Task Deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                mainViewModel.onUndoClicked(task)
            }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = mainViewModel.getSearchValue()
        if (pendingQuery?.isNotEmpty() == true) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        initListener()

        setupDataInMenu(menu)


    }

    private fun setupDataInMenu(menu: Menu) {
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_hide_completed_tasks).isChecked =
                mainViewModel.preferenceFlow.first().hideCompleted
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_name -> {

                mainViewModel.setUpSortedOrder(SortedState.BY_NAME)
                true
            }
            R.id.action_sort_by_date_created -> {
                mainViewModel.setUpSortedOrder(SortedState.BY_DATE)
                true
            }
            R.id.action_hide_completed_tasks -> {
                item.isChecked = !item.isChecked
                mainViewModel.setUpHideCompleted(item.isChecked)
                true
            }
            R.id.action_delete_all_completed_tasks -> {
                // mainViewModel.onDeleteAllCompletedClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(task: Task) {
        mainViewModel.onTaskSelected(task)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        mainViewModel.onTaskedCheckedChanged(task, isChecked)
    }


}