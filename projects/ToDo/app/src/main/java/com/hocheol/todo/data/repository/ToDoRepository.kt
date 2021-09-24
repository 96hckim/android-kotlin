package com.hocheol.todo.data.repository

import com.hocheol.todo.data.entity.ToDoEntity

/**
 * 1. InsertToDoList
 * 2. getToDoList
 */
interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

}