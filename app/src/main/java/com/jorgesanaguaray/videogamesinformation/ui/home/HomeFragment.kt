package com.jorgesanaguaray.videogamesinformation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import coil.load
import com.jorgesanaguaray.videogamesinformation.R
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jorge Sanaguaray
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter1: HomeAdapter
    private lateinit var homeAdapter2: HomeAdapter
    private lateinit var gameAdapter: GameAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this).get()
        homeAdapter1 = HomeAdapter()
        homeAdapter2 = HomeAdapter()
        gameAdapter = GameAdapter()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.game.observe(viewLifecycleOwner) {

            binding.apply {

                mTitle.text = it.title
                mImage.load(it.thumbnail) {
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                    crossfade(true)
                    crossfade(400)
                }
                mShortDescription.text = it.short_description

            }

            buttonGoToTheGamePage(it.game_url)

        }

        homeViewModel.categories.observe(viewLifecycleOwner) {

            homeAdapter1.setGames(it)
            binding.mRecyclerViewCategory.adapter = homeAdapter1
            homeAdapter1.setOnButtonClick(object : HomeAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    adapterGoToTheGamePage(gameUrl)
                }
            })

        }

        homeViewModel.platforms.observe(viewLifecycleOwner) {

            homeAdapter2.setGames(it)
            binding.mRecyclerViewPlatform.adapter = homeAdapter2
            homeAdapter2.setOnButtonClick(object : HomeAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    adapterGoToTheGamePage(gameUrl)
                }
            })

        }

        homeViewModel.games.observe(viewLifecycleOwner) {

            gameAdapter.setGames(it)
            binding.mRecyclerViewGames.adapter = gameAdapter
            gameAdapter.setOnButtonClick(object : GameAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    adapterGoToTheGamePage(gameUrl)
                }
            })

        }

        homeViewModel.nestedScrollViewVisibility.observe(viewLifecycleOwner) {
            binding.mNestedScrollView.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.textViewNoInternetVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewNoInternet.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.mSwipeRefreshLayout.setOnRefreshListener {

            homeViewModel.getRecommendedGameFromService()
            homeViewModel.getRecommendedCategoriesFromService()
            homeViewModel.getRecommendedPlatformsFromService()
            homeViewModel.getRecommendedGamesFromService()
            binding.mSwipeRefreshLayout.isRefreshing = false

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buttonGoToTheGamePage(gameUrl: String) {

        binding.mButtonGoToTheGamePage.setOnClickListener {

            val uri = Uri.parse(gameUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }

    }

    private fun adapterGoToTheGamePage(gameUrl: String) {

        val uri = Uri.parse(gameUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

    }

}