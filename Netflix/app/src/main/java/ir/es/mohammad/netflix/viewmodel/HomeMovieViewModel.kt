package ir.es.mohammad.netflix.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.es.mohammad.netflix.model.ComingSoon
import ir.es.mohammad.netflix.model.Movie
import ir.es.mohammad.netflix.model.Photos
import ir.es.mohammad.netflix.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMovieViewModel: ViewModel() {
     val movies: MutableLiveData<MutableSet<Movie>> by lazy { MutableLiveData<MutableSet<Movie>>() }

     init { fillComingSoonMovie() }

     private fun fillComingSoonMovie() {
          val queryParams = hashMapOf(
               "api_key" to "0f8c1d6193e2794fcad7977669bb009f",
               "method" to "flickr.photos.getRecent",
               "extras" to "url_s",
               "format" to "json",
               "nojsoncallback" to "1",
               "per_page" to "100",
               "page" to "1"
          )
          NetworkManager.flickrService.getPhotos(queryParams).enqueue(object : Callback<Photos> {
               override fun onResponse(call: Call<Photos>, response: Response<Photos>) {
                    val set = mutableSetOf<Movie>()
                    response.body()?.photos?.photo?.forEach {
                         set.add(Movie(it.title, it.url_s))
                    }
                    movies.postValue(set)
               }

               override fun onFailure(call: Call<Photos>, t: Throwable) {
                    Log.d("TAGG", t.message.toString())
               }
          })
     }
}