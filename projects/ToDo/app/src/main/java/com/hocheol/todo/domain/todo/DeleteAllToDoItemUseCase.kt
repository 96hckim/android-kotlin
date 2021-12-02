package com.hocheol.todo.domain.todo

import com.hocheol.todo.data.repository.ToDoRepository
import com.hocheol.todo.domain.UseCase

internal class DeleteAllToDoItemUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke() {
        return toDoRepository.deleteAll()
    }

}