package com.example.graduationapp.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.graduationapp.remote.retro.DefaultRepo

object ServiceLocator {
    /*var tasksRepository:DefaultRepo?=null
@VisibleForTesting set

    fun provideTasksRepository(context: Context):DefaultRepo{
        return tasksRepository?:createRepository(context)
    }

    private fun createRepository(context: Context): DefaultRepo {
        val dao = createDatabase(context)
       var repository = DefaultTasksRepository(TasksRemoteDataSource,TasksLocalDataSource(dao))
        return repository
    }

    private fun createDatabase(context: Context):TasksDao{
        val database = Room.databaseBuilder(context.applicationContext,
                ToDoDatabase::class.java, "Tasks.db")
                .build()
        return database.taskDao()

    }*/
}