package com.idocnet.mvvmdemo.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idocnet.mvvmdemo.api.GithubService
import com.idocnet.mvvmdemo.api.response.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val githubService: GithubService): ViewModel() {
    private val compositeDisposable: CompositeDisposable? = CompositeDisposable()
    val repos: MutableLiveData<ArrayList<Repo>> = MutableLiveData()
    fun getRepos(key: String, page: Int){

        compositeDisposable?.add(githubService.searchRepos(key, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {response ->
                    repos.value = response.items
                },
                {error -> print("REPOERROR: ${error.message}")}
            ))
    }

    fun disposable() {
        compositeDisposable?.dispose()
    }
}