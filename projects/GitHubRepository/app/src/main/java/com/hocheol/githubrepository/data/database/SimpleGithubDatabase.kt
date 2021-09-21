package com.hocheol.githubrepository.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.githubrepository.data.dao.RepositoryDao
import com.hocheol.githubrepository.data.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class SimpleGithubDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao

}
