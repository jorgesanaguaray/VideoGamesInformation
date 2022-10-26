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
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jorge Sanaguaray
 */

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var games: MutableList<GameItem>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel = ViewModelProvider(this).get()
        searchAdapter = SearchAdapter()
        games = ArrayList()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.games.observe(viewLifecycleOwner) {

            searchAdapter.setGames(it.shuffled())
            binding.mRecyclerView.adapter = searchAdapter
            searchAdapter.setOnButtonClick(object : SearchAdapter.OnButtonClick {
                override fun onClick(gameUrl: String) {
                    val uri = Uri.parse(gameUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            })

        }

        searchViewModel.message.observe(viewLifecycleOwner) {
            binding.mTextViewMessage.text = it
        }

        searchViewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            binding.mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.textViewVisibility.observe(viewLifecycleOwner) {
            binding.mTextViewMessage.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.recyclerViewVisibility.observe(viewLifecycleOwner) {
            binding.mRecyclerView.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.searchViewVisibility.observe(viewLifecycleOwner) {
            binding.mSearchView.visibility = if (it) View.VISIBLE else View.GONE
        }

        searchViewModel.getGameFromService()
        setOnSearchViewClick()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnSearchViewClick() {

        searchViewModel.games.observe(viewLifecycleOwner) {

            binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {

                    games.clear()

                    if (query != null) {

                        for (game in it) {
                            if (game.title.lowercase().contains(query.lowercase())) games.add(game)
                        }

                        searchAdapter.setGames(games.shuffled())

                    }

                    return true

                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    games.clear()

                    if (newText != null) {

                        for (game in it) {
                            if (game.title.lowercase().contains(newText.lowercase())) games.add(game)
                        }

                        searchAdapter.setGames(games.shuffled())

                    }

                    return true

                }

            })

        }

    }

}