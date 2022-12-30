package com.jorgesanaguaray.videogamesinformation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.KEY_GAME_ID
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onStart() {
        super.onStart()

        homeViewModel = ViewModelProvider(this).get()
        homeAdapter = HomeAdapter(
            homeViewModel = homeViewModel,
            itemPosition = { homeAdapter.notifyItemChanged(it) },
            onFavoriteClick = { insertOrDeleteFavorite(it) },
            onButtonUrlClick = { goGamePage(it) },
            onCardGameShortClick = { goGameDetail(it) },
            onCardGameLongClick = { showDetailDialog(it) }
        )

    }

    override fun onResume() {
        super.onResume()

        homeViewModel.gamesA.observe(viewLifecycleOwner) {

            val images = ArrayList<SlideModel>()

            for (game in it) {
                images.add(SlideModel(game.thumbnail))
            }

            binding.apply {
                mImageSlider.setImageList(images)
                mImageSlider.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                        goGameDetail(it[position].id)
                    }
                })
            }

        }

        homeViewModel.gamesB.observe(viewLifecycleOwner) {
            homeAdapter.setGames(it)
            binding.mRecyclerView.adapter = homeAdapter
        }

        homeViewModel.error.observe(viewLifecycleOwner) {
            binding.mError.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.error) + "</b>" + " " + it + "." + "<br><br>" + "<b>" + resources.getString(R.string.possible_solution) + "</b>" + " " + resources.getString(R.string.check_your_internet_connection), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        homeViewModel.nestedScrollVisibility.observe(viewLifecycleOwner) {
            binding.mNestedScroll.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.cardErrorVisibility.observe(viewLifecycleOwner) {
            binding.mCardError.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
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

    private fun insertOrDeleteFavorite(gameItem: GameItem) {

        if (homeViewModel.isFavorite(gameItem.id)) {
            homeViewModel.deleteFavoriteById(gameItem.id)
            Snackbar.make(requireView(), R.string.game_removed_from_favorites, Snackbar.LENGTH_SHORT).show()
        } else {
            homeViewModel.insertFavorite(gameItem)
            Snackbar.make(requireView(), R.string.game_saved_in_favorites, Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun showDetailDialog(gameItem: GameItem) {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val binding: DialogDetailBinding = DialogDetailBinding.inflate(layoutInflater)

        binding.apply {
            mTitle.text = gameItem.title
            mDescription.text = gameItem.short_description
            mGenre.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genre) + "</b>" + " " + gameItem.genre, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mPlatform.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.platform) + "</b>" + " " + gameItem.platform, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mPublisher.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.publisher) + "</b>" + " " + gameItem.publisher, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mDeveloper.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.developer) + "</b>" + " " + gameItem.developer, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mReleaseDate.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.release_date) + "</b>" + " " + gameItem.release_date, HtmlCompat.FROM_HTML_MODE_LEGACY)
            mImage.load(gameItem.thumbnail) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
                crossfade(400)
                transformations(RoundedCornersTransformation(10f))
            }
            mClose.setOnClickListener { bottomSheetDialog.dismiss() }
            mButtonUrl.setOnClickListener { goGamePage(gameItem.game_url) }
        }

        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()

    }

    private fun goGameDetail(id: Int) {
        val bundle = bundleOf(KEY_GAME_ID to id)
        findNavController().navigate(R.id.action_nav_home_to_nav_detail, bundle)
    }

    private fun goGamePage(gameUrl: String) {
        val uri = Uri.parse(gameUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}