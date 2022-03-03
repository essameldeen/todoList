package com.demo.todolist.data.app

import android.content.Context
import androidx.room.Room
import com.demo.todolist.data.db.DataBase
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
    fun provideDataBase(@ApplicationContext context: Context, callback: DataBase.CallBack) =
        Room.databaseBuilder(
            context,
            DataBase::class.java,
            "tasks.db"
        ).addCallback(callback).build()

    @Provides
    @Singleton
    fun provideTaskDao(db: DataBase) = db.getDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope