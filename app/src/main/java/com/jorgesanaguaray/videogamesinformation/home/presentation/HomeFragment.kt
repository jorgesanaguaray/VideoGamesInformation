package com.jorgesanaguaray.videogamesinformation.home.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.DialogDetailBinding
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentHomeBinding
import com.jorgesanaguaray.videogamesinformation.Constants.Companion.KEY_GAME_ID
import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Jorge Sanaguaray
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get()
        homeAdapter = HomeAdapter(
            homeViewModel = homeViewModel,
            itemPosition = { homeAdapter.notifyItemChanged(it) },
            onFavoriteClick = { insertOrDeleteFavorite(it) },
            onButtonUrlClick = { goGameWebsite(it) },
            onCardGameShortClick = { goGameDetail(it) },
            onCardGameLongClick = { showDetailDialog(it) }
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeState.collect {
                    setUpViews(it)
                }
            }
        }

        binding.mSwipeRefresh.setOnRefreshListener {
            homeViewModel.getGames()
            binding.mSwipeRefresh.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews(homeState: HomeState) {

        val images = ArrayList<SlideModel>()
        for (game in homeState.gamesA) {
            images.add(SlideModel(game.thumbnail))
        }
        binding.apply {
            mImageSlider.setImageList(images)
            mImageSlider.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    goGameDetail(homeState.gamesA[position].id)
                }
            })
        }

        homeAdapter.setGames(homeState.gamesB)
        binding.mRecyclerView.adapter = homeAdapter

        binding.mError.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.error) + "</b>" + " " + homeState.error + "." + "<br><br>" + "<b>" + resources.getString(R.string.possible_solution) + "</b>" + " " + resources.getString(R.string.check_your_internet_connection), HtmlCompat.FROM_HTML_MODE_LEGACY)

        if (homeState.isContent) binding.mNestedScroll.visibility = View.VISIBLE
        else binding.mNestedScroll.visibility = View.GONE

        if (homeState.isError) binding.mCardError.visibility = View.VISIBLE
        else binding.mCardError.visibility = View.GONE

        if (homeState.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

    private fun insertOrDeleteFavorite(game: Game) {

        if (homeViewModel.isFavorite(game.id)) {
            homeViewModel.deleteFavoriteById(game.id)
            Snackbar.make(requireView(), R.string.game_removed_from_favorites, Snackbar.LENGTH_SHORT).show()
        } else {
            homeViewModel.insertFavorite(game)
            Snackbar.make(requireView(), R.string.game_added_in_favorites, Snackbar.LENGTH_SHORT).show()
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
            mButtonUrl.setOnClickListener { goGameWebsite(game.game_url) }
        }

        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()

    }

    private fun goGameDetail(id: Int) {
        val bundle = bundleOf(KEY_GAME_ID to id)
        findNavController().navigate(R.id.action_nav_home_to_nav_detail, bundle)
    }

    private fun goGameWebsite(gameUrl: String) {
        val uri = Uri.parse(gameUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}