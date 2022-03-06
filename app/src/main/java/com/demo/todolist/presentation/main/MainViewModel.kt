package com.demo.todolist.presentation.main


import androidx.lifecycle.*
import com.demo.todolist.data.model.Task
import com.demo.todolist.domain.useCase.GetAllTasks
import com.demo.todolist.presentation.utils.SortedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetAllTasks
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortedOrder = MutableStateFlow(SortedState.BY_DATE)
    val hideCompleted = MutableStateFlow(false)




    private val taskFlow = combine(
        searchQuery,
        sortedOrder,
        hideCompleted
    ) { query, sortedValue, hideValue ->
        Triple(
            query,
            sortedValue,
            hideValue
        ) // wrapped all three value in single object because single value only return
    }.flatMapLatest {
        useCase.run(it.first,it.second, it.third)
    }

    val tasks = taskFlow.asLiveData()


}