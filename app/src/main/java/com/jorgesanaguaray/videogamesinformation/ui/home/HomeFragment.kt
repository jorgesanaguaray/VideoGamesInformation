package com.jorgesanaguaray.videogamesinformation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentHomeBinding
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.ui.detail.DetailActivity
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
            onButtonUrlClick = { goToTheGamePage(it) },
            onCardGameClick = { goToTheGameDetails(it) }
        )

    }

    override fun onResume() {
        super.onResume()

        homeViewModel.images.observe(viewLifecycleOwner) {

            val images = ArrayList<SlideModel>()

            for (game in it) {
                images.add(SlideModel(game.thumbnail))
            }

            binding.apply {
                mImageSlider.setImageList(images)
                mImageSlider.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                        goToTheGameDetails(it[position].id)
                    }
                })
            }

        }

        homeViewModel.games.observe(viewLifecycleOwner) {
            homeAdapter.setGames(it)
            binding.mRecyclerView.adapter = homeAdapter
        }

        homeViewModel.error.observe(viewLifecycleOwner) {
            binding.mTextError.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.error) + "</b>" + " " + it + "." + "<br><br>" + "<b>" + resources.getString(R.string.possible_solution) + "</b>" + " " + resources.getString(R.string.check_your_internet_connection), HtmlCompat.FROM_HTML_MODE_LEGACY)
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