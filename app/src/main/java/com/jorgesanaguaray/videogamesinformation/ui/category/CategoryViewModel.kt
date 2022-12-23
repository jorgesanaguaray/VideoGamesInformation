package com.jorgesanaguaray.videogamesinformation.ui.category

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
class CategoryViewModel @Inject constructor(

    private val categoryRepository: CategoryRepository,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteByIdUseCase: DeleteFavoriteByIdUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase

) : ViewModel() {

    private val _games = MutableLiveData<List<GameItem>>()
    val games: LiveData<List<GameItem>> get() = _games

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _textInputLayoutVisibility = MutableLiveData<Boolean>()
    val textInputLayoutVisibility: LiveData<Boolean> get() = _textInputLayoutVisibility

    private val _recyclerViewVisibility = MutableLiveData<Boolean>()
    val recyclerViewVisibility: LiveData<Boolean> get() = _recyclerViewVisibility

    private val _cardErrorVisibility = MutableLiveData<Boolean>()
    val cardErrorVisibility: LiveData<Boolean> get() = _cardErrorVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    init {
        getGamesByCategory("2d")
    }

    fun getGamesByCategory(category: String) {

        viewModelScope.launch {

            showProgressBar()

            try {

                _games.value = categoryRepository.getGamesByCategory(category)
                showTextInputLayoutAndRecyclerView()

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

    private fun showTextInputLayoutAndRecyclerView() {
        _textInputLayoutVisibility.value = true
        _recyclerViewVisibility.value = true
        _cardErrorVisibility.value = false
        _progressBarVisibility.value = false
    }

    private fun showCardError() {
        _textInputLayoutVisibility.value = false
        _recyclerViewVisibility.value = false
        _cardErrorVisibility.value = true
        _progressBarVisibility.value = false
    }

    private fun showProgressBar() {
        _textInputLayoutVisibility.value = false
        _recyclerViewVisibility.value = false
        _cardErrorVisibility.value = false
        _progressBarVisibility.value = true
    }

}