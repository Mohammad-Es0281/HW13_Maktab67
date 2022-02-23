package ir.es.mohammad.netflix.network

import ir.es.mohammad.netflix.model.Photos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface FlickrService {
    @GET("services/rest/")
    fun getPhotos(@QueryMap queryParams: HashMap<String, String>): Call<Photos>
}