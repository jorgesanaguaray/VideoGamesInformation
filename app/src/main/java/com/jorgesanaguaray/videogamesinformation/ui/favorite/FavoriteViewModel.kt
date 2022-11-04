package com.jorgesanaguaray.videogamesinformation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.DeleteFavoriteGamesUseCase
import com.jorgesanaguaray.videogamesinformation.domain.GetFavoriteGamesUseCase
import com.jorgesanaguaray.videogamesinformation.domain.item.FavoriteGameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class FavoriteViewModel @Inject constructor(

    private val getFavoriteGamesUseCase: GetFavoriteGamesUseCase,
    private val deleteFavoriteGamesUseCase: DeleteFavoriteGamesUseCase

    ) : ViewModel() {

    private val _games = MutableLiveData<List<FavoriteGameItem>>()
    val games: LiveData<List<FavoriteGameItem>> get() = _games

    private val _recyclerViewVisibility = MutableLiveData<Boolean>()
    val recyclerViewVisibility: LiveData<Boolean> get() = _recyclerViewVisibility

    private val _floatingActionButtonVisibility = MutableLiveData<Boolean>()
    val floatingActionButtonVisibility: LiveData<Boolean> get() = _floatingActionButtonVisibility

    private val _textViewNoFavoritesVisibility = MutableLiveData<Boolean>()
    val textViewNoFavoritesVisibility: LiveData<Boolean> get() = _textViewNoFavoritesVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    init {
        getFavoriteGames()
    }

    fun getFavoriteGames() {

        showProgressBar()

        viewModelScope.launch {

            val games = getFavoriteGamesUseCase()

            if (games.isNotEmpty()) {

                _games.value = games
                showRecyclerViewAndFloatingActionButton()

            } else {

                showTextViewNoFavorites()

            }

        }

    }

    fun deleteFavoriteGames() {

        viewModelScope.launch {

            deleteFavoriteGamesUseCase()
            showTextViewNoFavorites()

        }

    }

    private fun showRecyclerViewAndFloatingActionButton() {

        _recyclerViewVisibility.value = true
        _floatingActionButtonVisibility.value = true
        _textViewNoFavoritesVisibility.value = false
        _progressBarVisibility.value = false

    }

    private fun showTextViewNoFavorites() {

        _recyclerViewVisibility.value = false
        _floatingActionButtonVisibility.value = false
        _textViewNoFavoritesVisibility.value = true
        _progressBarVisibility.value = false

    }

    private fun showProgressBar() {

        _recyclerViewVisibility.value = false
        _floatingActionButtonVisibility.value = false
        _textViewNoFavoritesVisibility.value = false
        _progressBarVisibility.value = true

    }

}