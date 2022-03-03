package com.demo.todolist.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat


@Entity(tableName = "table_task")
@Parcelize
data class Task(
    val name: String,
    val important: Boolean = false, val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    val cratedAtString: String
        get() = DateFormat.getDateTimeInstance().format(createdAt)
}