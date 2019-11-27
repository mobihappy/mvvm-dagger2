package com.idocnet.mvvmdemo.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import com.idocnet.mvvmdemo.AppExecutors
import com.idocnet.mvvmdemo.api.ApiResponse
import com.idocnet.mvvmdemo.api.GithubService
import com.idocnet.mvvmdemo.api.Resource
import com.idocnet.mvvmdemo.api.response.Repo
import com.idocnet.mvvmdemo.api.response.SearchResponse
import com.idocnet.mvvmdemo.db.RepoDao
import com.idocnet.mvvmdemo.db.UsersDatabase
import com.idocnet.mvvmdemo.repository.NetworkBoundResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: UsersDatabase,
    private val githubService: GithubService,
    private val repoDao: RepoDao) {
//    private val compositeDisposable: CompositeDisposable? = CompositeDisposable()
//
//    fun getRepos(key: String, page: Int): LiveData<List<Repo>> {
//        refreshRepo(key, page)
//
//        return repoDao.getRepos()
//    }
//
//    private fun refreshRepo(key: String, page: Int){
//        compositeDisposable?.add(githubService.searchRepos(key, page)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {response ->
//                    repoDao.insertRepo(response.items)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                        { Log.d("REPODAOERROR: " ,"Insert Sucess")},
//                        {error ->
//                            val str = error.message
//                            Log.d("REPODAOERROR:" , str)
//                        }
//                    )
//                },
//                {error -> print("REPOERROR: ${error.message}")}
//            )
//        )
//    }
//
//    fun disposable() {
//        compositeDisposable?.dispose()
//    }

    fun getRepos(key: String, page: Int) : LiveData<Resource<List<Repo>>>  {
        return object: NetworkBoundResource<List<Repo>, SearchResponse>(appExecutors) {
            override fun saveCallResult(item: SearchResponse) {
                db.runInTransaction {
                    repoDao.insertRepo(item.items)
                }
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Repo>> {
                return repoDao.getRepos()
            }

            override fun createCall(): LiveData<ApiResponse<SearchResponse>> {
                return githubService.searchRepos(key, page)
            }

        }.asLiveData()
    }
}