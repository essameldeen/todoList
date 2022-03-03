package com.demo.todolist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat


@Entity(tableName = "task_table")
data class Task(
    val name: String,
    val important: Boolean = false, val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)  {
    val cratedAtString: String
        get() = DateFormat.getDateTimeInstance().format(createdAt)
}