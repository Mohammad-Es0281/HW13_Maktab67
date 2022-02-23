package ir.es.mohammad.netflix.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AfsharService {
    @GET("/uploads/{photo_name}")
    fun downloadProfileImage(@Path("photo_name") user: String): Call<ResponseBody>

    @Multipart
    @POST("users/{image_name}")
    fun uploadProfileImage(
        @Path("image_name") imageName: String,
        @Part image: MultipartBody.Part): Call<Any>
}