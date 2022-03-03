package com.demo.todolist.presentation.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.demo.todolist.domain.useCase.GetAllTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetAllTasks
) : ViewModel() {

    val tasks = useCase.run().asLiveData()

}