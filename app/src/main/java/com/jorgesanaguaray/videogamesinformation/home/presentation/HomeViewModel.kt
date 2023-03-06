package com.jorgesanaguaray.videogamesinformation.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.home.domain.HomeRepository
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
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    init {
        getGames()
    }

    fun getGames() {

        viewModelScope.launch {

            _homeState.update {
                it.copy(isContent = false, isError = false, isLoading = true)
            }

            homeRepository.getGamesA().onSuccess { games ->

                _homeState.update {
                    it.copy(gamesA = games, isContent = true, isError = false, isLoading = false)
                }

            }.onFailure { error ->

                _homeState.update {
                    it.copy(error = error.toString(), isContent = false, isError = true, isLoading = false)
                }

            }

            homeRepository.getGamesB().onSuccess { games ->

                _homeState.update {
                    it.copy(gamesB = games, isContent = true, isError = false, isLoading = false)
                }

            }.onFailure {}

        }

    }

    fun insertFavorite(game: Game) {

        viewModelScope.launch {
            homeRepository.insertFavorite(game)
        }

    }

    fun deleteFavoriteById(id: Int) {

        viewModelScope.launch {
            homeRepository.deleteFavoriteById(id)
        }

    }

    fun isFavorite(id: Int): Boolean {

        var result = false

        viewModelScope.launch {
            result = homeRepository.isFavorite(id)
        }

        return result

    }

}