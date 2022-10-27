package com.jorgesanaguaray.videogamesinformation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.SearchedGames
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchedGames: SearchedGames) : ViewModel() {

    private val _games = MutableLiveData<List<GameItem>>()
    val games: LiveData<List<GameItem>> get() = _games

    private val _searchViewVisibility = MutableLiveData<Boolean>()
    val searchViewVisibility: LiveData<Boolean> get() = _searchViewVisibility

    private val _recyclerViewVisibility = MutableLiveData<Boolean>()
    val recyclerViewVisibility: LiveData<Boolean> get() = _recyclerViewVisibility

    private val _textViewNoGamesVisibility = MutableLiveData<Boolean>()
    val textViewNoGamesVisibility: LiveData<Boolean> get() = _textViewNoGamesVisibility

    private val _textViewNoInternetVisibility = MutableLiveData<Boolean>()
    val textViewNoInternetVisibility: LiveData<Boolean> get() = _textViewNoInternetVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    fun getGameFromService(query: String) {

        showProgressBar()

        viewModelScope.launch {

            try {

                val games = searchedGames(query)

                if (games.isNotEmpty()) {
                    _games.value = games
                    showSearchViewAndRecyclerView()
                } else { // No games were found with the search term.
                    showTextViewNoGamesAndSearchView()
                }

            } catch (e: Exception) { // No internet connection.
                showTextViewNoInternet()
            }

        }

    }

    private fun showSearchViewAndRecyclerView() {

        _searchViewVisibility.value = true
        _recyclerViewVisibility.value = true
        _textViewNoGamesVisibility.value = false
        _textViewNoInternetVisibility.value = false
        _progressBarVisibility.value = false

    }

    private fun showTextViewNoGamesAndSearchView() {

        _searchViewVisibility.value = true
        _recyclerViewVisibility.value = false
        _textViewNoGamesVisibility.value = true
        _textViewNoInternetVisibility.value = false
        _progressBarVisibility.value = false

    }

    private fun showTextViewNoInternet() {

        _searchViewVisibility.value = false
        _recyclerViewVisibility.value = false
        _textViewNoGamesVisibility.value = false
        _textViewNoInternetVisibility.value = true
        _progressBarVisibility.value = false

    }

    private fun showProgressBar() {

        _searchViewVisibility.value = false
        _recyclerViewVisibility.value = false
        _textViewNoGamesVisibility.value = false
        _textViewNoInternetVisibility.value = false
        _progressBarVisibility.value = true

    }

}