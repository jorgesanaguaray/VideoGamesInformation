package com.jorgesanaguaray.videogamesinformation.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.snackbar.Snackbar
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentDetailBinding
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.items.SpecificGameItem
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.KEY_GAME_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var screenshotAdapter: ScreenshotAdapter
    private var gameId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        detailViewModel = ViewModelProvider(this).get()
        screenshotAdapter = ScreenshotAdapter()
        gameId = arguments?.getInt(KEY_GAME_ID)!!

    }

    override fun onResume() {
        super.onResume()

        detailViewModel.game.observe(this) {

            (requireActivity() as AppCompatActivity).supportActionBar?.title = it.title

            screenshotAdapter.setScreenshots(it.screenshots)

            binding.apply {

                mImage.load(it.thumbnail) {
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                    crossfade(true)
                    crossfade(400)
                    transformations(RoundedCornersTransformation(5f))
                }
                mButtonGoToTheGamePage.setOnClickListener { _ ->
                    val uri = Uri.parse(it.game_url)
                    val intent2 = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent2)
                }
                mButtonFavorite.setOnClickListener { _ -> insertOrDeleteFavorite(it) }
                mDescription.text = it.description
                mStatus.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.status) + "</b>" + " " + it.status, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mGenre.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genre) + "</b>" + " " + it.genre, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mPlatform.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.platform) + "</b>" + " " + it.platform, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mPublisher.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.publisher) + "</b>" + " " + it.publisher, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mDeveloper.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.developer) + "</b>" + " " + it.developer, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mReleaseDate.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.release_date) + "</b>" + " " + it.release_date, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mOs.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.os) + "</b>" + " " + it.minimum_system_requirements?.os, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mProcessor.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.processor) + "</b>" + " " + it.minimum_system_requirements?.processor, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mMemory.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.memory) + "</b>" + " " + it.minimum_system_requirements?.memory, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mGraphics.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.graphics) + "</b>" + " " + it.minimum_system_requirements?.graphics, HtmlCompat.FROM_HTML_MODE_LEGACY)
                mStorage.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.storage) + "</b>" + " " + it.minimum_system_requirements?.storage, HtmlCompat.FROM_HTML_MODE_LEGACY)
                if (it.screenshots.isEmpty()) mScreenshots.text = resources.getString(R.string.no_screenshots)
                mRecyclerView.adapter = screenshotAdapter

            }

        }

        detailViewModel.error.observe(this) {
            binding.mTextError.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.error) + "</b>" + " " + it + "." + "<br><br>" + "<b>" + resources.getString(
                R.string.possible_solution) + "</b>" + " " + resources.getString(R.string.check_your_internet_connection), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        detailViewModel.nestedScrollVisibility.observe(this) {
            binding.mNestedScroll.visibility = if (it) View.VISIBLE else View.GONE
        }

        detailViewModel.cardErrorVisibility.observe(this) {
            binding.mCardError.visibility = if (it) View.VISIBLE else View.GONE
        }

        detailViewModel.progressBarVisibility.observe(this) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.mSwipeRefresh.setOnRefreshListener {
            detailViewModel.getGameById(gameId)
            binding.mSwipeRefresh.isRefreshing = false
        }

        detailViewModel.getGameById(gameId)

        setStateOfButtonFavorite()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertOrDeleteFavorite(game: SpecificGameItem) {

        if (isFavorite()) {

            detailViewModel.deleteFavoriteById(gameId)
            binding.mButtonFavorite.text = resources.getString(R.string.save_to_favorites)
            Snackbar.make(requireView(), R.string.game_removed_from_favorites, Snackbar.LENGTH_LONG).show()

        } else {

            val gameItem = GameItem(id = game.id, title = game.title, thumbnail = game.thumbnail, short_description = game.short_description, game_url = game.game_url)
            detailViewModel.insertFavorite(gameItem)
            binding.mButtonFavorite.text = resources.getString(R.string.remove_from_favorites)
            Snackbar.make(requireView(), R.string.game_saved_in_favorites, Snackbar.LENGTH_LONG).show()

        }

    }

    private fun setStateOfButtonFavorite() {

        if (isFavorite()) binding.mButtonFavorite.text = resources.getString(R.string.remove_from_favorites)
        else binding.mButtonFavorite.text = resources.getString(R.string.save_to_favorites)

    }

    private fun isFavorite(): Boolean {
        return detailViewModel.isFavorite(gameId)
    }

}