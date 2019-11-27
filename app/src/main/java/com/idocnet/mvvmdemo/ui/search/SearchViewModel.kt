package com.idocnet.mvvmdemo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idocnet.mvvmdemo.api.GithubService
import com.idocnet.mvvmdemo.api.Resource
import com.idocnet.mvvmdemo.api.response.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel() {
    lateinit var repos: LiveData<Resource<List<Repo>>>

    fun getRepos(key: String, page: Int){
        repos = repository.getRepos(key, page)
    }

}