package com.jorgesanaguaray.videogamesinformation.favorite.presentation

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.DialogDetailBinding
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentFavoriteBinding
import com.jorgesanaguaray.videogamesinformation.Constants.Companion.KEY_GAME_ID
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Jorge Sanaguaray
 */

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        favoriteViewModel = ViewModelProvider(this).get()
        favoriteAdapter = FavoriteAdapter(
            favoriteViewModel = favoriteViewModel,
            itemPosition = { favoriteAdapter.notifyItemChanged(it) },
            onFavoriteClick = { insertOrDeleteFavorite(it) },
            onButtonUrlClick = { goGamePage(it) },
            onCardGameShortClick = { goGameDetail(it) },
            onCardGameLongClick = { showDetailDialog(it) }
        )

    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteViewModel.favoriteState.collect {
                    setUpViews(it)
                }
            }
        }

        binding.mFloatingButton.setOnClickListener { showDeleteDialog() }

        binding.mSwipeRefresh.setOnRefreshListener {
            favoriteViewModel.getFavorites()
            binding.mSwipeRefresh.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews(favoriteState: FavoriteState) {

        favoriteAdapter.setGames(favoriteState.games)
        binding.mRecyclerView.adapter = favoriteAdapter

        if (favoriteState.isRecyclerView) binding.mRecyclerView.visibility = View.VISIBLE
        else binding.mRecyclerView.visibility = View.GONE

        if (favoriteState.isFloatingButton) binding.mFloatingButton.visibility = View.VISIBLE
        else binding.mFloatingButton.visibility = View.GONE

        if (favoriteState.isNoFavorites) binding.mNoFavorites.visibility = View.VISIBLE
        else binding.mNoFavorites.visibility = View.GONE

        if (favoriteState.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

    private fun insertOrDeleteFavorite(game: Game) {

        if (favoriteViewModel.isFavorite(game.id)) {
            favoriteViewModel.deleteFavoriteById(game.id)
            Snackbar.make(requireView(), R.string.game_removed_from_favorites, Snackbar.LENGTH_SHORT).show()
        } else {
            favoriteViewModel.insertFavorite(game)
            Snackbar.make(requireView(), R.string.game_saved_in_favorites, Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun showDetailDialog(game: Game) {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val binding: DialogDetailBinding = DialogDetailBinding.inflate(layoutInflater)

        binding.apply {
            mTitle.text = game.title
            mDescription.text = game.short_description
            mGenre.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genre) + "</b>" + " " + game.genre, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mPlatform.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.platform) + "</b>" + " " + game.platform, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mPublisher.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.publisher) + "</b>" + " " + game.publisher, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mDeveloper.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.developer) + "</b>" + " " + game.developer, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mReleaseDate.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.release_date) + "</b>" + " " + game.release_date, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mImage.load(game.thumbnail) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
                crossfade(400)
                transformations(RoundedCornersTransformation(10f))
            }
            mClose.setOnClickListener { bottomSheetDialog.dismiss() }
            mButtonUrl.setOnClickListener { goGamePage(game.game_url) }
        }

        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()

    }

    private fun showDeleteDialog() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setIcon(R.drawable.ic_delete)
        builder.setTitle(R.string.delete_all_favorite_games)
        builder.setMessage(R.string.are_you_sure_you_want_to_delete_all_your_favorite_games)
        builder.setPositiveButton(R.string.yes) { _: DialogInterface, _: Int -> favoriteViewModel.deleteFavorites() }
        builder.setNegativeButton(R.string.no) { _, _ ->}
        builder.setCancelable(false)
        builder.create().show()

    }

    private fun goGameDetail(id: Int) {
        val bundle = bundleOf(KEY_GAME_ID to id)
        findNavController().navigate(R.id.action_nav_favorite_to_nav_detail, bundle)
    }

    private fun goGamePage(gameUrl: String) {
        val uri = Uri.parse(gameUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}