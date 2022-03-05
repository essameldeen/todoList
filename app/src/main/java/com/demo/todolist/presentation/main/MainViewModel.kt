package com.demo.todolist.presentation.main


import androidx.lifecycle.*
import com.demo.todolist.data.model.Task
import com.demo.todolist.domain.useCase.GetAllTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetAllTasks
) : ViewModel() {

    val tasks = MutableStateFlow<List<Task>>(listOf())

    init {
        getAllTasks()
    }

    private fun getAllTasks() {
        useCase.run().onEach {
            tasks.value = it
        }.launchIn(viewModelScope)
    }


}