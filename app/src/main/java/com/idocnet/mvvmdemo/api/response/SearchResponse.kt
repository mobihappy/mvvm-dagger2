package com.idocnet.mvvmdemo.api.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class SearchResponse (
    val total_count: Long,
    val incomplete_results: Boolean,
    val items: List<Repo>
)

@Entity(tableName = "repos")
class Repo(
    @PrimaryKey
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("owner")
    @field:Embedded(prefix = "owner_")
    val owner: Owner,
    @field:SerializedName("stargazers_count")
    val stars: Int
)

class Owner(
    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("url")
    val url: String?
)