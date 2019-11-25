package com.idocnet.mvvmdemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    fun getUsers(): Observable<List<User>>

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUserById(id: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Completable

    @Query("DELETE FROM Users")
    fun deleteAllUsers()
}