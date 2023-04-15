package com.jorgesanaguaray.videogamesinformation.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.core.domain.CoreRepository
import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game
import com.jorgesanaguaray.videogamesinformation.favorite.domain.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class FavoriteViewModel @Inject constructor(

    private val favoriteRepository: FavoriteRepository,
    private val coreRepository: CoreRepository

) : ViewModel() {

    private val _favoriteState = MutableStateFlow(FavoriteState())
    val favoriteState: StateFlow<FavoriteState> = _favoriteState.asStateFlow()

    init {
        getFavorites()
    }

    fun getFavorites() {

        viewModelScope.launch {

            _favoriteState.update {
                it.copy(isRecyclerView = false, isFloatingButton = false, isNoFavorites = false, isLoading = true)
            }

            favoriteRepository.getFavorites().onSuccess { games ->

                if (games.isNotEmpty()) {

                    _favoriteState.update {
                        it.copy(games = games, isRecyclerView = true, isFloatingButton = true, isNoFavorites = false, isLoading = false)
                    }

                } else {

                    _favoriteState.update {
                        it.copy(isRecyclerView = false, isFloatingButton = false, isNoFavorites = true, isLoading = false)
                    }

                }

            }.onFailure {}

        }

    }

    fun deleteFavorites() {

        viewModelScope.launch {

            favoriteRepository.deleteFavorites()
            getFavorites()

        }

    }

    fun insertFavorite(game: Game) {

        viewModelScope.launch {
            coreRepository.insertFavorite(game)
        }

    }

    fun deleteFavoriteById(id: Int) {

        viewModelScope.launch {
            coreRepository.deleteFavoriteById(id)
        }

    }

    fun isFavorite(id: Int): Boolean {

        var result = false

        viewModelScope.launch {
            result = coreRepository.isFavorite(id)
        }

        return result

    }

}