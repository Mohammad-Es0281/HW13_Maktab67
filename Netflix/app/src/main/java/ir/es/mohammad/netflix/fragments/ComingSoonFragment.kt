package ir.es.mohammad.netflix.fragments

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import ir.es.mohammad.netflix.R
import ir.es.mohammad.netflix.adapter.MovieRecyclerAdapter
import ir.es.mohammad.netflix.databinding.FragmentComingSoonBinding
import ir.es.mohammad.netflix.model.Movie
import ir.es.mohammad.netflix.viewmodel.ComingSoonViewModel
import ir.es.mohammad.netflix.viewmodel.UserViewModel

class ComingSoonFragment : Fragment(R.layout.fragment_coming_soon) {

    private lateinit var binding: FragmentComingSoonBinding
    private val comingSoonViewModel: ComingSoonViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val mAdapter by lazy { MovieRecyclerAdapter(ArrayList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentComingSoonBinding.bind(view)
        setRecyclerView()
        comingSoonViewModel.comingSoonMovies.observe(this){ mAdapter.setData(ArrayList(it)) }
    }

    private fun setRecyclerView(){
        binding.recyclerViewComingSoonMovies.layoutManager = LinearLayoutManager(requireContext())
        mAdapter.setEachItem = { movie ->
            setMovie(movie)
            viewHolderBinding.btnMovieAction.setMovieActionButton(movie)
        }
        binding.recyclerViewComingSoonMovies.adapter = mAdapter
    }

    private fun ImageButton.setMovieActionButton(movie: Movie) {
        setImageResource(R.drawable.ic_baseline_share_24)
        setOnClickListener {
            if (userViewModel.registered.value!!)
                context.startActivity(createShareText(movie.title))
            else
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

    private fun createShareText(text: String): Intent {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(EXTRA_TEXT, text)
        }
        return intent
    }
}