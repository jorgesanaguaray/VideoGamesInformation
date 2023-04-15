package com.jorgesanaguaray.videogamesinformation.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.category.domain.CategoryRepository
import com.jorgesanaguaray.videogamesinformation.core.domain.CoreRepository
import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game
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
class CategoryViewModel @Inject constructor(

    private val categoryRepository: CategoryRepository,
    private val coreRepository: CoreRepository

) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()

    init {
        getGamesByCategory("2d")
    }

    fun getGamesByCategory(category: String) {

        viewModelScope.launch {

            _categoryState.update {
                it.copy(isTextInputLayout = false, isRecyclerView = false, isError = false, isLoading = true)
            }

            categoryRepository.getGamesByCategory(category).onSuccess { games ->

                _categoryState.update {
                    it.copy(games = games, isTextInputLayout = true, isRecyclerView = true, isError = false, isLoading = false)
                }

            }.onFailure { error ->

                _categoryState.update {
                    it.copy(error = error.toString(), isTextInputLayout = false, isRecyclerView = false, isError = true, isLoading = false)
                }

            }

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