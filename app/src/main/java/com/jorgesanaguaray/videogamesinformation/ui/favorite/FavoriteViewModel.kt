package com.jorgesanaguaray.videogamesinformation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.usecases.DeleteFavoriteByIdUseCase
import com.jorgesanaguaray.videogamesinformation.domain.usecases.InsertFavoriteUseCase
import com.jorgesanaguaray.videogamesinformation.domain.usecases.IsFavoriteUseCase
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class FavoriteViewModel @Inject constructor(

    private val favoriteRepository: FavoriteRepository,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteByIdUseCase: DeleteFavoriteByIdUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase

    ) : ViewModel() {

    private val _games = MutableLiveData<List<GameItem>>()
    val games: LiveData<List<GameItem>> get() = _games

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

            val games = favoriteRepository.getFavorites()

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

            favoriteRepository.deleteFavorites()
            showTextViewNoFavorites()

        }

    }


    fun insertFavorite(gameItem: GameItem) {

        viewModelScope.launch {
            insertFavoriteUseCase(gameItem)
        }

    }

    fun deleteFavoriteById(id: Int) {

        viewModelScope.launch {
            deleteFavoriteByIdUseCase(id)
        }

    }

    fun isFavorite(id: Int): Boolean {

        var result = false

        viewModelScope.launch {
            result = isFavoriteUseCase(id)
        }

        return result

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