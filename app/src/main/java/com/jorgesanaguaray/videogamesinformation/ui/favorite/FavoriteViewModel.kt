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

    private val _floatingButtonVisibility = MutableLiveData<Boolean>()
    val floatingButtonVisibility: LiveData<Boolean> get() = _floatingButtonVisibility

    private val _noFavoritesVisibility = MutableLiveData<Boolean>()
    val noFavoritesVisibility: LiveData<Boolean> get() = _noFavoritesVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    init {
        getFavorites()
    }

    fun getFavorites() {

        viewModelScope.launch {

            showProgressBar()

            val games = favoriteRepository.getFavorites()

            if (games.isNotEmpty()) {

                _games.value = games
                showRecyclerViewAndFloatingButton()

            } else {
                showNoFavorites()
            }

        }

    }

    fun deleteFavorites() {

        viewModelScope.launch {
            favoriteRepository.deleteFavorites()
            showNoFavorites()
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

    private fun showRecyclerViewAndFloatingButton() {
        _recyclerViewVisibility.value = true
        _floatingButtonVisibility.value = true
        _noFavoritesVisibility.value = false
        _progressBarVisibility.value = false
    }

    private fun showNoFavorites() {
        _recyclerViewVisibility.value = false
        _floatingButtonVisibility.value = false
        _noFavoritesVisibility.value = true
        _progressBarVisibility.value = false
    }

    private fun showProgressBar() {
        _recyclerViewVisibility.value = false
        _floatingButtonVisibility.value = false
        _noFavoritesVisibility.value = false
        _progressBarVisibility.value = true
    }

}