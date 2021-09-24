package com.hocheol.todo.data.repository

import com.hocheol.todo.data.entity.ToDoEntity

class DefaultToDoRepository : ToDoRepository {

    override suspend fun getToDoList(): List<ToDoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        TODO("Not yet implemented")
    }

}