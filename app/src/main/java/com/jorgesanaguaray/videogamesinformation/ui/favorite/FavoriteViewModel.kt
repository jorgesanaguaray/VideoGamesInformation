package com.jorgesanaguaray.videogamesinformation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.GetAllFavorites
import com.jorgesanaguaray.videogamesinformation.domain.item.FavoritesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val getAllFavoritesUseCase: GetAllFavorites) : ViewModel() {

    private val _games = MutableLiveData<List<FavoritesItem>>()
    val games: LiveData<List<FavoritesItem>> get() = _games

    private val _recyclerViewVisibility = MutableLiveData<Boolean>()
    val recyclerViewVisibility: LiveData<Boolean> get() = _recyclerViewVisibility

    private val _textViewNoFavoritesVisibility = MutableLiveData<Boolean>()
    val textViewNoFavoritesVisibility: LiveData<Boolean> get() = _textViewNoFavoritesVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    init {
        getAllFavorites()
    }

    fun getAllFavorites() {

        showProgressBar()

        viewModelScope.launch {

            val games = getAllFavoritesUseCase()

            if (games.isNotEmpty()) {

                _games.value = games
                showRecyclerView()

            } else {

                showTextViewNoFavorites()

            }

        }

    }

    private fun showRecyclerView() {

        _recyclerViewVisibility.value = true
        _textViewNoFavoritesVisibility.value = false
        _progressBarVisibility.value = false

    }

    private fun showTextViewNoFavorites() {

        _recyclerViewVisibility.value = false
        _textViewNoFavoritesVisibility.value = true
        _progressBarVisibility.value = false

    }

    private fun showProgressBar() {

        _recyclerViewVisibility.value = false
        _textViewNoFavoritesVisibility.value = false
        _progressBarVisibility.value = true

    }

}