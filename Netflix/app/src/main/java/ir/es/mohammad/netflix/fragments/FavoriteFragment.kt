package ir.es.mohammad.netflix.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import ir.es.mohammad.netflix.adapter.MovieRecyclerAdapter
import ir.es.mohammad.netflix.R
import ir.es.mohammad.netflix.viewmodel.UserViewModel
import ir.es.mohammad.netflix.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        binding.recyclerViewFavoriteMovies.layoutManager = GridLayoutManager(requireContext(), 3)

        val movieAdapter = MovieRecyclerAdapter(ArrayList(userViewModel.favoriteMovies))
        movieAdapter.setEachItem = { movie ->
            viewHolderBinding.btnMovieAction.setImageResource(R.drawable.ic_baseline_remove_24)
            viewHolderBinding.btnMovieAction.setOnClickListener {
                movieAdapter.remove(adapterPosition)
                userViewModel.removeMovieFromFavorite(movie)
            }
        }
        binding.recyclerViewFavoriteMovies.adapter = movieAdapter
    }
}