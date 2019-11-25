package com.idocnet.mvvmdemo.api

import com.idocnet.mvvmdemo.api.response.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String, @Query("page") page: Int): Observable<SearchResponse>
}