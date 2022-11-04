package com.jorgesanaguaray.videogamesinformation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.data.local.entities.FavoriteGameEntity
import com.jorgesanaguaray.videogamesinformation.domain.*
import com.jorgesanaguaray.videogamesinformation.domain.item.SpecificGameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class DetailViewModel @Inject constructor(

    private val getGameByIdFromServiceUseCase: GetGameByIdFromServiceUseCase,
    private val insertFavoriteGameUseCase: InsertFavoriteGameUseCase,
    private val deleteFavoriteGameByIdUseCase: DeleteFavoriteGameByIdUseCase,
    private val getFavoriteGameByIdUseCase: GetFavoriteGameByIdUseCase

    ) : ViewModel() {

    private val _game = MutableLiveData<SpecificGameItem>()
    val game: LiveData<SpecificGameItem> get() = _game

    private val _nestedScrollViewVisibility = MutableLiveData<Boolean>()
    val nestedScrollViewVisibility: LiveData<Boolean> get() = _nestedScrollViewVisibility

    private val _textViewNoInternetVisibility = MutableLiveData<Boolean>()
    val textViewNoInternetVisibility: LiveData<Boolean> get() = _textViewNoInternetVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    fun getGameByIdFromService(id: Int) {

        showProgressBar()

        viewModelScope.launch {

            try {

                val game = getGameByIdFromServiceUseCase(id)
                _game.value = game
                showNestedScrollView()

            } catch (e: Exception) { // No internet connection.

                showTextViewNoInternet()

            }

        }

    }

    fun insertFavoriteGame(favorite: FavoriteGameEntity) {

        viewModelScope.launch {
            insertFavoriteGameUseCase(favorite)
        }

    }

    fun deleteFavoriteGameById(id: Int) {

        viewModelScope.launch {
            deleteFavoriteGameByIdUseCase(id)
        }

    }

    fun isFavoriteGame(id: Int): Boolean {

        var favoriteGameEntity: FavoriteGameEntity?

        runBlocking {
            favoriteGameEntity = getFavoriteGameByIdUseCase(id)
        }

        if (favoriteGameEntity == null) return false

        return true

    }

    private fun showNestedScrollView() {

        _nestedScrollViewVisibility.value = true
        _textViewNoInternetVisibility.value = false
        _progressBarVisibility.value = false

    }

    private fun showTextViewNoInternet() {

        _nestedScrollViewVisibility.value = false
        _textViewNoInternetVisibility.value = true
        _progressBarVisibility.value = false

    }

    private fun showProgressBar() {

        _nestedScrollViewVisibility.value = false
        _textViewNoInternetVisibility.value = false
        _progressBarVisibility.value = true

    }

}