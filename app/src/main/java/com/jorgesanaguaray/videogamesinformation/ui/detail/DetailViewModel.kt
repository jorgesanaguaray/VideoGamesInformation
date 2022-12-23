package com.jorgesanaguaray.videogamesinformation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.items.SpecificGameItem
import com.jorgesanaguaray.videogamesinformation.domain.usecases.DeleteFavoriteByIdUseCase
import com.jorgesanaguaray.videogamesinformation.domain.usecases.InsertFavoriteUseCase
import com.jorgesanaguaray.videogamesinformation.domain.usecases.IsFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class DetailViewModel @Inject constructor(

    private val detailRepository: DetailRepository,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteByIdUseCase: DeleteFavoriteByIdUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase

) : ViewModel() {

    private val _game = MutableLiveData<SpecificGameItem>()
    val game: LiveData<SpecificGameItem> get() = _game

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _nestedScrollVisibility = MutableLiveData<Boolean>()
    val nestedScrollVisibility: LiveData<Boolean> get() = _nestedScrollVisibility

    private val _cardErrorVisibility = MutableLiveData<Boolean>()
    val cardErrorVisibility: LiveData<Boolean> get() = _cardErrorVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    fun getGameById(id: Int) {

        viewModelScope.launch {

            showProgressBar()

            try {

                _game.value = detailRepository.getGameById(id)
                showNestedScrollView()

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

    private fun showNestedScrollView() {
        _nestedScrollVisibility.value = true
        _cardErrorVisibility.value = false
        _progressBarVisibility.value = false
    }

    private fun showCardError() {
        _nestedScrollVisibility.value = false
        _cardErrorVisibility.value = true
        _progressBarVisibility.value = false
    }

    private fun showProgressBar() {
        _nestedScrollVisibility.value = false
        _cardErrorVisibility.value = false
        _progressBarVisibility.value = true
    }

}