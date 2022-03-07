package com.demo.todolist.presentation.main


import androidx.lifecycle.*
import com.demo.todolist.data.db.PreferenceManager
import com.demo.todolist.data.model.Task
import com.demo.todolist.domain.useCase.GetAllTasks
import com.demo.todolist.domain.useCase.UpdateTask
import com.demo.todolist.presentation.utils.SortedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCaseGetAllTasks: GetAllTasks,
    private val useCaseUpdateTask: UpdateTask,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val preferenceFlow = preferenceManager.preferenceFlow

    private val taskFlow = combine(
        searchQuery,
        preferenceFlow
    ) { query, preferenceFlow ->
        Pair(
            query,
            preferenceFlow
        ) // wrapped all three value in single object because single value only return
    }.flatMapLatest {
        useCaseGetAllTasks.run(it.first, it.second.sortedState, it.second.hideCompleted)
    }

    val tasks = taskFlow.asLiveData()


    fun setUpSearchValue(searchValue: String) {
        searchQuery.value = searchValue
    }

    fun getSearchValue() = searchQuery.value

    fun setUpSortedOrder(sortedState: SortedState) = viewModelScope.launch {
        preferenceManager.updateSortState(sortedState)
    }

    fun setUpHideCompleted(hideValue: Boolean) = viewModelScope.launch {
        preferenceManager.updateHideCompleted(hideValue)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {

    }


    fun onTaskedCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        useCaseUpdateTask.run(task.copy(isCompleted = isChecked))
    }


}