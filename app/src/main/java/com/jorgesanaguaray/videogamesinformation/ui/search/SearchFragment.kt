package com.jorgesanaguaray.videogamesinformation.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.jorgesanaguaray.videogamesinformation.databinding.FragmentSearchBinding
import com.jorgesanaguaray.videogamesinformation.ui.detail.DetailActivity
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.KEY_GAME_ID
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jorge Sanaguaray
 */

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel = ViewModelProvider(this).get()
        searchAdapter = SearchAdapter()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.games.observe(viewLifecycleOwner) {

            searchAdapter.setGames(it)
            binding.mRecyclerView.adapter = searchAdapter
            searchAdapter.setOnButtonClick(object : SearchAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    val uri = Uri.parse(gameUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            })
            searchAdapter.setOnCardViewClick(object : SearchAdapter.OnCardViewClick {
                override fun onClick(id: Int) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(KEY_GAME_ID, id)
                    startActivity(intent)
                }
            })

        }

        searchViewModel.searchViewVisibility.observe(viewLifecycleOwner) {
            binding.mSearchView.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.recyclerViewVisibility.observe(viewLifecycleOwner) {
            binding.mRecyclerView.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.textViewNoGamesVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewNoGames.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.textViewNoInternetVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewNoInternet.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.getSearchedGames(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        binding.mSwipeRefreshLayout.setOnRefreshListener {
            searchViewModel.getSearchedGames("")
            binding.mSearchView.setQuery("", false)
            binding.mSwipeRefreshLayout.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}