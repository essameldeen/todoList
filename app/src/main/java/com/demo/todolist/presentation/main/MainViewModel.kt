package com.demo.todolist.presentation.main


import androidx.lifecycle.*
import com.demo.todolist.data.db.PreferenceManager
import com.demo.todolist.data.model.Task
import com.demo.todolist.domain.useCase.GetAllTasks
import com.demo.todolist.presentation.utils.SortedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetAllTasks,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

     val preferenceFlow = preferenceManager.preferenceFlow

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

    private val taskFlow = combine(
        searchQuery,
        preferenceFlow
    ) { query, preferenceFlow ->
        Pair(
            query,
            preferenceFlow
        ) // wrapped all three value in single object because single value only return
    }.flatMapLatest {
        useCase.run(it.first, it.second.sortedState, it.second.hideCompleted)
    }

    val tasks = taskFlow.asLiveData()


}