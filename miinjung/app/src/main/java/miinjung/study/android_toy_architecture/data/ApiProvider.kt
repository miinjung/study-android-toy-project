package miinjung.study.android_toy_architecture.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private const val baseURL = "https://api.github.com"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val api : ApiServiceImpl = retrofit.create(ApiServiceImpl::class.java)
}