package com.jorgesanaguaray.videogamesinformation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.GamesFromService2
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gamesFromService2: GamesFromService2
    ) : ViewModel() {

    private val _games = MutableLiveData<List<GameItem>>()
    val games: LiveData<List<GameItem>> get() = _games

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    private val _textViewVisibility = MutableLiveData<Boolean>()
    val textViewVisibility: LiveData<Boolean> get() = _textViewVisibility

    private val _recyclerViewVisibility = MutableLiveData<Boolean>()
    val recyclerViewVisibility: LiveData<Boolean> get() = _recyclerViewVisibility

    private val _searchViewVisibility = MutableLiveData<Boolean>()
    val searchViewVisibility: LiveData<Boolean> get() = _searchViewVisibility


    fun getGameFromService() {

        showProgressBar()

        viewModelScope.launch {

            try {

                val games = gamesFromService2()
                _games.value = games
                showRecyclerViewAndSearchView()

            } catch (e: Exception) { // No internet connection.

                _message.value = "Check your internet connection"
                showTextView()

            }

        }

    }


    private fun showProgressBar() {

        _progressBarVisibility.value = true
        _textViewVisibility.value = false
        _recyclerViewVisibility.value = false
        _searchViewVisibility.value = false

    }

    private fun showTextView() {

        _textViewVisibility.value = true
        _progressBarVisibility.value = false
        _recyclerViewVisibility.value = false
        _searchViewVisibility.value = false

    }

    private fun showRecyclerViewAndSearchView() {

        _recyclerViewVisibility.value = true
        _searchViewVisibility.value = true
        _progressBarVisibility.value = false
        _textViewVisibility.value = false

    }


}