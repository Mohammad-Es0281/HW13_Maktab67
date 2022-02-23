package ir.es.mohammad.netflix.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.es.mohammad.netflix.model.ComingSoon
import ir.es.mohammad.netflix.model.Movie
import ir.es.mohammad.netflix.network.NetworkManager
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComingSoonViewModel : ViewModel() {
    val comingSoonMovies: MutableLiveData<HashSet<Movie>> by lazy { MutableLiveData<HashSet<Movie>>() }

    init {
        fillComingSoonMovie()
    }

    private fun fillComingSoonMovie() {
        NetworkManager.imdbService.comingSoonMovies().enqueue(object : Callback<ComingSoon> {
            override fun onResponse(call: Call<ComingSoon>, response: Response<ComingSoon>) {
                val hashSet = HashSet<Movie>()
                response.body()?.items?.forEach { hashSet.add(Movie(it.title, it.image)) }
                comingSoonMovies.postValue(hashSet)
            }

            override fun onFailure(call: Call<ComingSoon>, t: Throwable) {
                Log.d("TAGG", t.message.toString())
            }
        })
    }
}