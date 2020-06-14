package miinjung.study.android_toy_architecture.data

import miinjung.study.android_toy_architecture.data.model.RepoItem
import miinjung.study.android_toy_architecture.data.model.RepoList
import miinjung.study.android_toy_architecture.data.model.RepoOwner
import miinjung.study.android_toy_architecture.data.model.RepoUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceImpl {
    @GET("/search/repositories")
    fun getSearchRepo(
        @Query("q") query: String): Call<RepoList>

    @GET("/repos/{login}/{name}")
    fun getItemRepo(
        @Path("login") ownerLogin: String,
        @Path("name") repoName: String): Call<RepoItem>

    @GET("/users/{login}")
    fun getUserRepo(
        @Path("login") ownerLogin: String): Call<RepoUser>
}