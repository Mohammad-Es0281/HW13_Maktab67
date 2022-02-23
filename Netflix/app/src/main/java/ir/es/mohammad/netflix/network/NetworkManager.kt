package ir.es.mohammad.netflix.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val interceptor = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder().addHeader("Authorization", "k_d6pbw34n").build())
    }

    private val imdbClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    val imdbService: ImdbService =
        buildRetrofit("https://imdb-api.com/", imdbClient).create(ImdbService::class.java)

    val afsharService: AfsharService =
        buildRetrofit("http://51.195.19.222/").create(AfsharService::class.java)

    val flickrService: FlickrService =
        buildRetrofit("https://www.flickr.com/").create(FlickrService::class.java)

    private fun buildRetrofit(baseUrl: String, client: OkHttpClient? = null): Retrofit {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl)
        if (client != null) retrofitBuilder.client(client)
        return retrofitBuilder.build()
    }
}