package com.demo.todolist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.demo.todolist.data.app.ApplicationScope
import com.demo.todolist.data.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun getDao(): TaskDao

    // callBack not instantiation before provider provide the instance of db
    class CallBack @Inject constructor(
        private val database: Provider<DataBase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        // call the first time open db only
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //db operations
            val dao = database.get().getDao()

            applicationScope.launch {
                dao.insert(Task("Going to Gym", important = true))
                dao.insert(Task("Reading Ds& Algo book", important = true))
                dao.insert(Task("Take Medicine"))
                dao.insert(Task("Meet My Friends"))
                dao.insert(Task("Buy Clothes"))

            }

        }
    }
}