package com.demo.todolist.data.app

import android.content.Context
import androidx.room.Room
import com.demo.todolist.data.db.TaskDao
import com.demo.todolist.data.Repo.TasksRepoImpl
import com.demo.todolist.data.db.TaskDatabase
import com.demo.todolist.domain.Repo.TasksRepo
import com.demo.todolist.domain.useCase.GetAllTasks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "tasks.db"
        ).build()

    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


    @Singleton
    @Provides
    fun provideRunRepo(
        dao: TaskDao,
    ): TasksRepo {
        return TasksRepoImpl(
            dao = dao
        )
    }

    @Singleton
    @Provides
    fun provideGetAllUseCase(repo:TasksRepo)= GetAllTasks(repo)
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope