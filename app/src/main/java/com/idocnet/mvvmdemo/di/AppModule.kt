package com.idocnet.mvvmdemo.di

import android.app.Application
import androidx.room.Room
import com.idocnet.mvvmdemo.api.GithubService
import com.idocnet.mvvmdemo.app.MyApplication
import com.idocnet.mvvmdemo.db.RepoDao
import com.idocnet.mvvmdemo.db.UsersDatabase
import com.idocnet.mvvmdemo.util.LiveDataCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule(private val app: MyApplication) {
    @Singleton
    @Provides
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(): UsersDatabase {
        return Room
            .databaseBuilder(app, UsersDatabase::class.java, "order.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: UsersDatabase): RepoDao {
        return db.repoDao()
    }
}