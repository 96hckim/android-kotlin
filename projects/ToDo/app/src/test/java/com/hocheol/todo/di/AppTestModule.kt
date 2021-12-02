package com.hocheol.todo.di

import com.hocheol.todo.data.repository.TestToDoRepository
import com.hocheol.todo.data.repository.ToDoRepository
import com.hocheol.todo.domain.todo.GetToDoListUseCase
import com.hocheol.todo.domain.todo.InsertToDoListUseCase
import com.hocheol.todo.presentation.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    // ViewModel
    viewModel { ListViewModel(get()) }

    // UseCase
    factory { GetToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }

    // Repository
    single<ToDoRepository> { TestToDoRepository() }

}