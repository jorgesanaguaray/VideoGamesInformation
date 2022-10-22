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

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this).get()
        categoryAdapter = CategoryAdapter()

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

            goToTheGamePage(it.game_url)

        }

        homeViewModel.categories.observe(viewLifecycleOwner) {

            categoryAdapter.setGames(it)
            binding.mRecyclerView.adapter = categoryAdapter
            categoryAdapter.setOnButtonClick(object : CategoryAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    val uri = Uri.parse(gameUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            })

        }

        homeViewModel.message.observe(viewLifecycleOwner) {
            binding.mTextViewMessage.text = it
        }

        homeViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.textViewVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewMessage.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.nestedScrollViewVisibility.observe(viewLifecycleOwner) {
            binding.mNestedScrollView.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.getGameFromService()
        homeViewModel.getCategoriesFromService()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToTheGamePage(gameUrl: String) {

        binding.mButtonGoToTheGamePage.setOnClickListener {

            val uri = Uri.parse(gameUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }

    }

}