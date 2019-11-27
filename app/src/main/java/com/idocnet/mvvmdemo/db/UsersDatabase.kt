package com.idocnet.mvvmdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.idocnet.mvvmdemo.api.response.Repo

@Database(entities = [User::class, Repo::class], version = 4, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao

    companion object {

        @Volatile private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, UsersDatabase::class.java, "order.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as UsersDatabase
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java, "Sample.db")
                .build()
    }
}