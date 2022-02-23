package ir.es.mohammad.netflix.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import ir.es.mohammad.netflix.*
import ir.es.mohammad.netflix.adapter.MovieRecyclerAdapter
import ir.es.mohammad.netflix.databinding.FragmentHomeBinding
import ir.es.mohammad.netflix.model.Movie
import ir.es.mohammad.netflix.viewmodel.HomeMovieViewModel
import ir.es.mohammad.netflix.viewmodel.UserViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val homeMovieViewModel: HomeMovieViewModel by viewModels()
    private val mAdapter by lazy { MovieRecyclerAdapter(ArrayList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setRecyclerView()
        homeMovieViewModel.movies.observe(this){ mAdapter.setData(ArrayList(it)) }
    }

    private fun setRecyclerView(){
        binding.recyclerViewHomeMovies.layoutManager = GridLayoutManager(requireContext(), 3)
        mAdapter.setEachItem = { movie -> viewHolderBinding.btnMovieAction.setMovieActionButton(movie) }
        binding.recyclerViewHomeMovies.adapter = mAdapter
    }

    private fun ImageButton.setMovieActionButton(movie: Movie) {
        if (movie.isFavorite)
            setImageResource(R.drawable.ic_baseline_favorite_24)
        else
            setImageResource(R.drawable.ic_outline_favorite)

        setOnClickListener {
            if (userViewModel.registered.value!!) {
                if (movie.isFavorite) {
                    userViewModel.removeMovieFromFavorite(movie)
                    setImageResource(R.drawable.ic_outline_favorite)
                } else {
                    userViewModel.addMovieToFavorite(movie)
                    setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            } else
                showRegisterSnackbar()
        }
    }

    private fun showRegisterSnackbar() {
        Snackbar.make(binding.root, "First you must register", Snackbar.LENGTH_LONG).apply {
            setAction("Register") {
                val bottomNavigationView =
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNavigationView.selectedItemId = R.id.showInfoFragment
            }
        }.show()
    }
}