package com.idocnet.mvvmdemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.idocnet.mvvmdemo.api.response.Repo
import io.reactivex.Flowable

@Dao
interface RepoDao {
    @Query("SELECT * FROM repos")
    fun getRepos(): LiveData<List<Repo>>

    @Query("SELECT * FROM repos WHERE id = :id")
    fun getRepoById(id: String): Flowable<Repo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepo(repos: List<Repo>)
}