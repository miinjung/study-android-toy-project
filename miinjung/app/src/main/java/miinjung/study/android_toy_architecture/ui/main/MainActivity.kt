package miinjung.study.android_toy_architecture.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import miinjung.study.android_toy_architecture.R
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import miinjung.study.android_toy_architecture.data.ApiProvider
import miinjung.study.android_toy_architecture.data.model.RepoList
import miinjung.study.android_toy_architecture.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val api by lazy { ApiProvider.api }
    private val dataAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialList()
        initialTimber()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val search : SearchView = menu.findItem(R.id.search).actionView as SearchView
        val nullMessage = Toast.makeText(this@MainActivity,R.string.no_query,Toast.LENGTH_SHORT)

        search.queryHint = "Search..."
        search.maxWidth = Integer.MAX_VALUE
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.isNullOrEmpty()){
                    nullMessage.show()
                }else{
                    checkInternet(query)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    nullMessage.show()
                }
                return false
            }
        })
        return true
    }

    private fun loadData(query:String){
        showProgress()

        api.getSearchRepo(query).enqueue(object : retrofit2.Callback<RepoList>{
            override fun onResponse(call: Call<RepoList>, response: Response<RepoList>) {
                if(response.isSuccessful){
                    response.body().let{
                        if(it != null){
                            if(it.totalCount == 0) {
                                Toast.makeText(this@MainActivity,"data없음",Toast.LENGTH_SHORT).show()
                                noData()
                            }
                            else{
                                haveData()
                                dataAdapter.getData(it.items)
                            }
                        }else {
                            noData()
                            Toast.makeText(this@MainActivity,"data null",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this@MainActivity,response.message(),Toast.LENGTH_SHORT).show()
                    noData()
                }
                hideProgress()
            }

            override fun onFailure(call: Call<RepoList>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
                hideProgress()
            }
        })
    }

    @Suppress("DEPRECATION")
    private fun checkInternet(query:String){
        val cm: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(cm.activeNetworkInfo == null){
            loadData(query)
        }else{
            Toast.makeText(this@MainActivity,R.string.disconnect_internet,Toast.LENGTH_SHORT).show()
        }
    }
    private fun showProgress() {
        pb_activity_main_loading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pb_activity_main_loading.visibility = View.INVISIBLE
    }


    private fun initialList(){
        rv_activity_main_repo.let{
            it.adapter = dataAdapter
        }
        clickItem()

    }

    private fun initialTimber(){
        Timber.plant(Timber.DebugTree())
    }

    private fun clickItem(){
        dataAdapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(login: String, name: String) {
                val intent = Intent(this@MainActivity,DetailActivity::class.java)
                intent.putExtra(KEY_LOGIN,login)
                intent.putExtra(KEY_NAME,name)

                startActivity(intent)
            }
        })
    }

    private fun noData(){
        rv_activity_main_repo.visibility = View.INVISIBLE
        tv_activity_main_no_data.visibility = View.VISIBLE
    }

    private fun haveData(){
        rv_activity_main_repo.visibility = View.VISIBLE
        tv_activity_main_no_data.visibility = View.INVISIBLE
    }

    companion object Key{
        const val KEY_LOGIN = "login"
        const val KEY_NAME = "name"
    }
}