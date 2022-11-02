package com.jorgesanaguaray.videogamesinformation.ui.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentFavoriteBinding
import com.jorgesanaguaray.videogamesinformation.ui.detail.DetailActivity
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.KEY_GAME_ID
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jorge Sanaguaray
 */

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteViewModel = ViewModelProvider(this).get()
        favoriteAdapter = FavoriteAdapter()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.games.observe(viewLifecycleOwner) {

            favoriteAdapter.setGames(it)
            binding.mRecyclerView.adapter = favoriteAdapter
            favoriteAdapter.setOnButtonClick(object : FavoriteAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    val uri = Uri.parse(gameUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            })
            favoriteAdapter.setOnCardViewClick(object : FavoriteAdapter.OnCardViewClick {
                override fun onClick(id: Int) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(KEY_GAME_ID, id)
                    startActivity(intent)
                }
            })

        }

        favoriteViewModel.recyclerViewVisibility.observe(viewLifecycleOwner) {
            binding.mRecyclerView.visibility = if (it) View.VISIBLE else View.GONE
        }

        favoriteViewModel.textViewNoFavoritesVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewNoFavorites.visibility = if (it) View.VISIBLE else View.GONE
        }

        favoriteViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.mSwipeRefreshLayout.setOnRefreshListener {
            favoriteViewModel.getAllFavorites()
            binding.mSwipeRefreshLayout.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}