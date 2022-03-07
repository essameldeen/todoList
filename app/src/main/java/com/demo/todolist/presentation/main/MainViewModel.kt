package com.demo.todolist.presentation.main


import androidx.lifecycle.*
import com.demo.todolist.data.db.PreferenceManager
import com.demo.todolist.data.model.Task
import com.demo.todolist.domain.useCase.AddTask
import com.demo.todolist.domain.useCase.DeleteTask
import com.demo.todolist.domain.useCase.GetAllTasks
import com.demo.todolist.domain.useCase.UpdateTask
import com.demo.todolist.presentation.utils.SortedState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCaseGetAllTasks: GetAllTasks,
    private val useCaseUpdateTask: UpdateTask,
    private var useCaseDeleteTask: DeleteTask,
    private val useCaseAddTask: AddTask,
    private val preferenceManager: PreferenceManager,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    //private val searchQuery = state.getLiveData("searchQuery", "")


    val preferenceFlow = preferenceManager.preferenceFlow

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

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

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        useCaseDeleteTask.run(task)
        taskEventChannel.send(TaskEvent.ShowUndoMessage(task))
    }

    fun onUndoClicked(task: Task) = viewModelScope.launch {
        useCaseAddTask.run(task)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {

    }


    fun onTaskedCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        useCaseUpdateTask.run(task.copy(isCompleted = isChecked))
    }


}