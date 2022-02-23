package ir.es.mohammad.netflix.network

import ir.es.mohammad.netflix.model.ComingSoon
import ir.es.mohammad.netflix.model.Photos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbService {
    @GET("/{lang}/API/ComingSoon/k_d6pbw34n")
    fun comingSoonMovies(@Path("lang") language: String = "en"): Call<ComingSoon>
}