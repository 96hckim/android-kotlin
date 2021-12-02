package com.hocheol.todo.presentation.list

import com.hocheol.todo.data.entity.ToDoEntity

sealed class ToDoListState {

    object UnInitialized : ToDoListState()

    object Loading : ToDoListState()

    data class Success(
        val toDoList: List<ToDoEntity>
    ) : ToDoListState()

    object Error : ToDoListState()

}
