package com.hocheol.todo.domain.todo

import com.hocheol.todo.data.entity.ToDoEntity
import com.hocheol.todo.data.repository.ToDoRepository
import com.hocheol.todo.domain.UseCase

internal class InsertToDoListUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke(toDoList: List<ToDoEntity>) {
        return toDoRepository.insertToDoList(toDoList)
    }

}