package com.idocnet.mvvmdemo.di

import com.idocnet.mvvmdemo.GithubActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injection(activity: GithubActivity)
}