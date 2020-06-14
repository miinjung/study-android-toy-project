package miinjung.study.android_toy_architecture.data.model

import com.google.gson.annotations.SerializedName

data class RepoItem (
    @SerializedName("full_name")
    val fullName:String,
    val description : String,
    val language : String?, // 데이터 값 내부에 null 있음 처리 필요
    @SerializedName("stargazers_count")
    val stargazersCount : Int,
    val name : String,
    val owner : RepoOwner
)