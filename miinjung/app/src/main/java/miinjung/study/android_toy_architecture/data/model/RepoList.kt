package miinjung.study.android_toy_architecture.data.model

import com.google.gson.annotations.SerializedName

data class RepoList (
    @SerializedName("total_count")
    val totalCount: Int,
    val items : ArrayList<RepoItem>
)