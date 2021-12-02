package com.hocheol.todo.data.local.db.dao

import androidx.room.*
import com.hocheol.todo.data.entity.ToDoEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDoEntity")
    fun getAll(): List<ToDoEntity>

    @Query("SELECT * FROM ToDoEntity WHERE id=:id")
    fun getById(id: Long): ToDoEntity?

    @Insert
    fun insert(toDoEntity: ToDoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoEntityList: List<ToDoEntity>)

    @Query("DELETE FROM ToDoEntity WHERE id=:id")
    fun delete(id: Long)

    @Query("DELETE FROM ToDoEntity")
    fun deleteAll()

    @Update
    fun update(toDoEntity: ToDoEntity)

}
