package com.demo.todolist.data.db

import androidx.room.*
import com.demo.todolist.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM TABLE_TASK")
    fun getAllTasks(): Flow<List<Task>>

}
