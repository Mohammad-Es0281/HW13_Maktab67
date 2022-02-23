package ir.es.mohammad.netflix.viewmodel

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.es.mohammad.netflix.model.ComingSoon
import ir.es.mohammad.netflix.model.Movie
import ir.es.mohammad.netflix.network.NetworkManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    val registered: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val profileImgDrawable: MutableLiveData<Drawable> by lazy { MutableLiveData<Drawable>() }
    val fullName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val email: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val username: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val phoneNumber: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val favoriteMovies = HashSet<Movie>()

    fun addMovieToFavorite(movie: Movie){
        movie.isFavorite = true
        favoriteMovies.add(movie)
    }

    fun removeMovieFromFavorite(movie: Movie){
        movie.isFavorite = false
        favoriteMovies.remove(movie)
    }

    private val profilePicName = "mes_profile.jpeg"
    fun uploadProfileImage(bytes: ByteArray) {
        val reqBody = MultipartBody.create(MediaType.parse("image/*"), bytes)
        val img = MultipartBody.Part.createFormData("image", profilePicName, reqBody)
        NetworkManager.afsharService.uploadProfileImage(profilePicName, img).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.d("TAGG", "OK")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("TAGG", t.message!!)
            }
        })
    }
}