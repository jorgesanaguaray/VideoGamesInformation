package com.jorgesanaguaray.videogamesinformation.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.items.SpecificGame
import com.jorgesanaguaray.videogamesinformation.usecases.DeleteFavoriteByIdUseCase
import com.jorgesanaguaray.videogamesinformation.usecases.InsertFavoriteUseCase
import com.jorgesanaguaray.videogamesinformation.usecases.IsFavoriteUseCase
import com.jorgesanaguaray.videogamesinformation.detail.domain.DetailRepository
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
class DetailViewModel @Inject constructor(

    private val detailRepository: DetailRepository,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteByIdUseCase: DeleteFavoriteByIdUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase

) : ViewModel() {

    private val _game = MutableLiveData<SpecificGame>()
    val game: LiveData<SpecificGame> get() = _game

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    fun getGameById(id: Int) {

        viewModelScope.launch {

            _detailState.update {
                it.copy(isContent = false, isError = false, isLoading = true)
            }

            detailRepository.getGameById(id).onSuccess { game ->

                _game.value = game

                _detailState.update {
                    it.copy(isContent = true, isError = false, isLoading = false)
                }

            }.onFailure { error ->

                _detailState.update {
                    it.copy(error = error.toString(), isContent = false, isError = true, isLoading = false)
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