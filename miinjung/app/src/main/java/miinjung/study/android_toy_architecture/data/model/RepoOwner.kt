package miinjung.study.android_toy_architecture.data.model

import com.google.gson.annotations.SerializedName

data class RepoOwner(
    @SerializedName("avatar_url")
    val avatarUrl:String,
    val login : String
)