package com.jorgesanaguaray.videogamesinformation.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.category.domain.CategoryRepository
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.usecases.DeleteFavoriteByIdUseCase
import com.jorgesanaguaray.videogamesinformation.usecases.InsertFavoriteUseCase
import com.jorgesanaguaray.videogamesinformation.usecases.IsFavoriteUseCase
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
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteByIdUseCase: DeleteFavoriteByIdUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase

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

    fun insertFavorite(gameItem: Game) {

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

}