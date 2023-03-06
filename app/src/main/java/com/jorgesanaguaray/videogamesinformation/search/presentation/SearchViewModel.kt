package com.jorgesanaguaray.videogamesinformation.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.search.domain.SearchRepository
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
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    init {
        getGames()
    }

    fun getGames() {

        viewModelScope.launch {

            _searchState.update {
                it.copy(isSearchView = false, isRecyclerView = false, isError = false, isLoading = true)
            }

            searchRepository.getGames().onSuccess { games ->

                _searchState.update {
                    it.copy(games = games, isSearchView = true, isRecyclerView = true, isError = false, isLoading = false)
                }

            }.onFailure { error ->

                _searchState.update {
                    it.copy(error = error.toString(), isSearchView = false, isRecyclerView = false, isError = true, isLoading = false)
                }

            }

        }

    }

    fun getSearchedGames(query: String) {

        viewModelScope.launch {

            searchRepository.getSearchedGames(query).onSuccess { games ->

                _searchState.update {
                    it.copy(games = games, isSearchView = true, isRecyclerView = true, isError = false, isLoading = false)
                }

            }.onFailure {}

        }

    }

    fun insertFavorite(game: Game) {

        viewModelScope.launch {
            searchRepository.insertFavorite(game)
        }

    }

    fun deleteFavoriteById(id: Int) {

        viewModelScope.launch {
            searchRepository.deleteFavoriteById(id)
        }

    }

    fun isFavorite(id: Int): Boolean {

        var result = false

        viewModelScope.launch {
            result = searchRepository.isFavorite(id)
        }

        return result

    }

}