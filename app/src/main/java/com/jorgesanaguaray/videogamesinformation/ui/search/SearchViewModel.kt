package com.jorgesanaguaray.videogamesinformation.ui.search

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
class SearchViewModel @Inject constructor(

    private val searchRepository: SearchRepository,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteByIdUseCase: DeleteFavoriteByIdUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase

) : ViewModel() {

    private val _games = MutableLiveData<List<GameItem>>()
    val games: LiveData<List<GameItem>> get() = _games

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _searchViewVisibility = MutableLiveData<Boolean>()
    val searchViewVisibility: LiveData<Boolean> get() = _searchViewVisibility

    private val _recyclerViewVisibility = MutableLiveData<Boolean>()
    val recyclerViewVisibility: LiveData<Boolean> get() = _recyclerViewVisibility

    private val _noGamesVisibility = MutableLiveData<Boolean>()
    val noGamesVisibility: LiveData<Boolean> get() = _noGamesVisibility

    private val _cardErrorVisibility = MutableLiveData<Boolean>()
    val cardErrorVisibility: LiveData<Boolean> get() = _cardErrorVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    init {
        getSearchedGames("")
    }

    fun getSearchedGames(query: String) {

        viewModelScope.launch {

            showProgressBar()

            try {

                val games = searchRepository.getSearchedGames(query)

                if (games.isNotEmpty()) {

                    _games.value = games
                    showSearchViewAndRecyclerView()

                } else { // No games were found with the search term.

                    showNoGamesAndSearchView()

                }

            } catch (e: Exception) {

                _error.value = e.toString()
                showCardError()

            }

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

    private fun showSearchViewAndRecyclerView() {
        _searchViewVisibility.value = true
        _recyclerViewVisibility.value = true
        _noGamesVisibility.value = false
        _cardErrorVisibility.value = false
        _progressBarVisibility.value = false
    }

    private fun showNoGamesAndSearchView() {
        _searchViewVisibility.value = true
        _recyclerViewVisibility.value = false
        _noGamesVisibility.value = true
        _cardErrorVisibility.value = false
        _progressBarVisibility.value = false
    }

    private fun showCardError() {
        _searchViewVisibility.value = false
        _recyclerViewVisibility.value = false
        _noGamesVisibility.value = false
        _cardErrorVisibility.value = true
        _progressBarVisibility.value = false
    }

    private fun showProgressBar() {
        _searchViewVisibility.value = false
        _recyclerViewVisibility.value = false
        _noGamesVisibility.value = false
        _cardErrorVisibility.value = false
        _progressBarVisibility.value = true
    }

}