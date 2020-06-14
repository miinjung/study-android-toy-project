package miinjung.study.android_toy_architecture.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import miinjung.study.android_toy_architecture.R
import miinjung.study.android_toy_architecture.data.ApiProvider
import miinjung.study.android_toy_architecture.data.model.RepoItem
import miinjung.study.android_toy_architecture.data.model.RepoUser
import miinjung.study.android_toy_architecture.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
    private lateinit var name : String
    private lateinit var login : String
    private val api by lazy { ApiProvider.api }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initialTimber()
        getKey()
    }

    private fun getKey(){
        val intent = intent

        login = checkNotNull(intent.getStringExtra(MainActivity.KEY_LOGIN))
        name = checkNotNull(intent.getStringExtra(MainActivity.KEY_NAME))

        loadRepoItem()
        loadRepoUser()
    }

    @SuppressLint("SetTextI18n")
    private fun setRepoItem(repoItem : RepoItem){
        hideProgress()
        tv_activity_detail_stars.text = getString(R.string.star_emoticon) + repoItem.stargazersCount + getString(R.string.stars)
        tv_activity_detail_discription.text = repoItem.description
        tv_activity_detali_full_name.text = repoItem.fullName
        Glide.with(this).load(repoItem.owner.avatarUrl).into(iv_activity_detail_user_image)
        if(repoItem.language != null)
            tv_activity_detail_language.text = repoItem.language
    }

    @SuppressLint("SetTextI18n")
    private fun setRepoUser(repoOwner: RepoUser){
        tv_activity_detail_follower.text = getString(R.string.follower) + repoOwner.followers
        tv_activity_detail_following.text = getString(R.string.following) + repoOwner.following
    }

    private fun loadRepoItem(){
        showProgress()
        api.getItemRepo(login,name).enqueue(object : Callback<RepoItem>{
            override fun onResponse(call: Call<RepoItem>, response: Response<RepoItem>) {
                if(response.isSuccessful){
                    response.body().let{
                        setRepoItem(checkNotNull(it))
                    }
                }else{
                    Toast.makeText(this@DetailActivity,response.message(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RepoItem>, t: Throwable) {
                Toast.makeText(this@DetailActivity,t.message,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadRepoUser(){
        api.getUserRepo(login).enqueue(object : Callback<RepoUser>{
            override fun onResponse(call: Call<RepoUser>, response: Response<RepoUser>) {
                if(response.isSuccessful){
                    response.body().let{
                        setRepoUser(checkNotNull(it))
                    }
                }else{
                    Toast.makeText(this@DetailActivity,response.message(),Toast.LENGTH_SHORT)
                }
            }

            override fun onFailure(call: Call<RepoUser>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initialTimber(){
        Timber.plant(Timber.DebugTree())
    }

    private fun showProgress() {
        pb_activity_detail_loading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pb_activity_detail_loading.visibility = View.INVISIBLE
    }

}
