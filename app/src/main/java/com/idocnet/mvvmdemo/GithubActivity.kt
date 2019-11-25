package com.idocnet.mvvmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.idocnet.mvvmdemo.api.response.Repo
import com.idocnet.mvvmdemo.app.MyApplication
import com.idocnet.mvvmdemo.databinding.ActivityGithubBinding
import com.idocnet.mvvmdemo.db.User
import com.idocnet.mvvmdemo.ui.search.RepoAdapter
import com.idocnet.mvvmdemo.ui.search.SearchViewModel
import com.idocnet.mvvmdemo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_github.*
import javax.inject.Inject

class GithubActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var binding: ActivityGithubBinding

    private lateinit var adapter: RepoAdapter
    private val repos: ArrayList<Repo> = ArrayList()

    private var totalItemCount: Int = 0
    private val threshold = 3
    private var lastvisibleItemPosition: Int = 0
    private var isLoading: Boolean = false

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github)
        (application as MyApplication).getComponent().injection(this)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        adapter = RepoAdapter { repo ->
            Toast.makeText(this, repo.description, Toast.LENGTH_LONG).show()
        }
        rvRepo.layoutManager = LinearLayoutManager(this)
        rvRepo.adapter = adapter

        rvRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                totalItemCount = linearLayoutManager.itemCount
                lastvisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastvisibleItemPosition + threshold >= totalItemCount) {
                    loadRepos(++page)
                    isLoading = true
                }
            }
        })

        searchViewModel.repos.observe(this, Observer<ArrayList<Repo>> { repos ->
            this.repos.addAll(repos)
            val r: ArrayList<Repo> = ArrayList()
            r.addAll(this.repos)
            Log.d("SEARCHMODELLOG", "${this.repos.size}")
            adapter.submitList(r)
            val json = Gson().toJson(repos)
            println(json)
            isLoading = false
        })

        loadRepos(page)

        val user = User("1", "Hoang Tung")
        binding.user = user

        btnRemove.setOnClickListener {
//            this.repos.removeAt(3)
//            val r: ArrayList<Repo> = ArrayList()
//            r.addAll(this.repos)
//            adapter.submitList(r)
            user.name = "Vuong Quang"
        }

        edName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.apply {
                    user.name = edName.text.toString()
                    invalidateAll()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    override fun onDestroy() {
        searchViewModel.disposable()
        super.onDestroy()
    }

    private fun loadRepos(page: Int) {
        searchViewModel.getRepos("Rxjava", page)
    }
}
