package ir.es.mohammad.netflix.adapter

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.es.mohammad.netflix.R
import ir.es.mohammad.netflix.databinding.MovieBinding
import ir.es.mohammad.netflix.model.Movie

class MovieRecyclerAdapter(private var movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieRecyclerAdapter.CustomViewHolder>() {

    var setEachItem: CustomViewHolder.(movie: Movie) -> Unit = { movie ->
        viewHolderBinding.textMovieName.text = movie.title
        viewHolderBinding.btnMovieAction.visibility = View.GONE
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewHolderBinding: MovieBinding = DataBindingUtil.bind(itemView)!!

        fun setMovie(movie: Movie){
            viewHolderBinding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setMovie(movies[position])
        holder.setEachItem(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun remove(actualPosition: Int){
        movies.removeAt(actualPosition)
        notifyItemRemoved(actualPosition);
    }

    fun setData(movies: ArrayList<Movie>){
        this.movies = movies
        notifyDataSetChanged()
    }
}