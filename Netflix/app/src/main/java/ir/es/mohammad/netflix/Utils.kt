package ir.es.mohammad.netflix

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import ir.es.mohammad.netflix.model.Movie
import ir.es.mohammad.netflix.model.Photos
import ir.es.mohammad.netflix.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory

import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import ir.es.mohammad.netflix.model.ComingSoon
import okhttp3.ResponseBody


@BindingAdapter("movieImage")
fun setMovieImage(imageView: ImageView, movieImageURL: String?) {
    if (movieImageURL == null)
        imageView.setImageResource(R.drawable.ic_baseline_movie_24)
    else
        setImageWithGlide(imageView, movieImageURL)
}

@BindingAdapter("profileImage")
fun setProfileImage(imageView: ImageView, nullObj: Any?){
        NetworkManager.afsharService.downloadProfileImage("mes_profile.jpeg").enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    val bmp = BitmapFactory.decodeStream(response.body()!!.byteStream())
                    imageView.setImageBitmap(bmp)
                }
                else
                    imageView.setImageResource(R.drawable.profile_pic)
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("TAGG", t.message.toString())
            }
        })
}

private fun setImageWithGlide(imageView: ImageView, movieImageURL: String?){
    Glide
        .with(imageView.context)
        .load(movieImageURL)
        .placeholder(R.drawable.loading_animation)
        .into(imageView)
}