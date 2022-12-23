package com.jorgesanaguaray.videogamesinformation.ui.favorite

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.material.snackbar.Snackbar
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentFavoriteBinding
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
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
        favoriteAdapter = FavoriteAdapter(
            favoriteViewModel = favoriteViewModel,
            itemPosition = { favoriteAdapter.notifyItemChanged(it) },
            onFavoriteClick = { insertOrDeleteFavorite(it) },
            onButtonUrlClick = { goToTheGamePage(it) },
            onCardGameClick = { goToTheGameDetails(it) }
        )

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
        }

        favoriteViewModel.recyclerViewVisibility.observe(viewLifecycleOwner) {
            binding.mRecyclerView.visibility = if (it) View.VISIBLE else View.GONE
        }

        favoriteViewModel.floatingActionButtonVisibility.observe(viewLifecycleOwner) {
            binding.mFloatingActionButton.visibility = if (it) View.VISIBLE else View.GONE
        }

        favoriteViewModel.textViewNoFavoritesVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewNoFavorites.visibility = if (it) View.VISIBLE else View.GONE
        }

        favoriteViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.mFloatingActionButton.setOnClickListener {
            showDeleteDialog()
        }

        binding.mSwipeRefreshLayout.setOnRefreshListener {
            favoriteViewModel.getFavoriteGames()
            binding.mSwipeRefreshLayout.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDeleteDialog() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setIcon(R.drawable.ic_delete)
        builder.setTitle(R.string.delete_all_favorite_games)
        builder.setMessage(R.string.are_you_sure_you_want_to_delete_all_your_favorite_games)
        builder.setPositiveButton(R.string.yes) { _: DialogInterface, _: Int -> favoriteViewModel.deleteFavoriteGames() }
        builder.setNegativeButton(R.string.no) { _, _ ->}
        builder.setCancelable(false)
        builder.create().show()

    }

    private fun insertOrDeleteFavorite(gameItem: GameItem) {

        if (favoriteViewModel.isFavorite(gameItem.id)) {
            favoriteViewModel.deleteFavoriteById(gameItem.id)
            Snackbar.make(requireView(), R.string.game_removed_from_favorites, Snackbar.LENGTH_SHORT).show()
        } else {
            favoriteViewModel.insertFavorite(gameItem)
            Snackbar.make(requireView(), R.string.game_saved_in_favorites, Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun goToTheGamePage(gameUrl: String) {
        val uri = Uri.parse(gameUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goToTheGameDetails(id: Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(KEY_GAME_ID, id)
        startActivity(intent)
    }

}