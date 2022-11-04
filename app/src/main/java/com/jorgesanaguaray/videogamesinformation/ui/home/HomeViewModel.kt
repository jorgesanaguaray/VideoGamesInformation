package com.jorgesanaguaray.videogamesinformation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.*
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val getGameFromServiceUseCase: GetGameFromServiceUseCase,
    private val getGameFromDaoUseCase: GetGameFromDaoUseCase,

    private val get5GamesByCategoryFromServiceUseCase: Get5GamesByCategoryFromServiceUseCase,
    private val getCategoryGamesFromDaoUseCase: GetCategoryGamesFromDaoUseCase,

    private val get5GamesByPlatformFromServiceUseCase: Get5GamesByPlatformFromServiceUseCase,
    private val getPlatformGamesFromDaoUseCase: GetPlatformGamesFromDaoUseCase,

    private val get5GamesFromServiceUseCase: Get5GamesFromServiceUseCase,
    private val getGamesFromDaoUseCase: GetGamesFromDaoUseCase

    ) : ViewModel() {

    private val _game = MutableLiveData<GameItem>()
    val game: LiveData<GameItem> get() = _game

    private val _categories = MutableLiveData<List<GameItem>>()
    val categories: LiveData<List<GameItem>> get() = _categories

    private val _platforms = MutableLiveData<List<GameItem>>()
    val platforms: LiveData<List<GameItem>> get() = _platforms

    private val _games = MutableLiveData<List<GameItem>>()
    val games: LiveData<List<GameItem>> get() = _games

    private val _nestedScrollViewVisibility = MutableLiveData<Boolean>()
    val nestedScrollViewVisibility: LiveData<Boolean> get() = _nestedScrollViewVisibility

    private val _textViewNoInternetVisibility = MutableLiveData<Boolean>()
    val textViewNoInternetVisibility: LiveData<Boolean> get() = _textViewNoInternetVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility


    init {
        getGameFromService()
        get5GamesByCategoryFromService()
        get5GamesByPlatformFromService()
        get5GamesFromService()
    }


    fun getGameFromService() {

        showProgressBar()

        viewModelScope.launch {

            try {

                val game = getGameFromServiceUseCase()
                _game.value = game
                showNestedScrollView()

            } catch (e: Exception) { // No internet connection.

                getGameFromDao()

            }

        }

    }

    private fun getGameFromDao() {

        viewModelScope.launch {

            try {

                val game = getGameFromDaoUseCase()
                _game.value = game
                showNestedScrollView()

            } catch (e: Exception) { // The database is empty

                showTextViewNoInternet()

            }

        }

    }


    fun get5GamesByCategoryFromService() {

        viewModelScope.launch {

            try {

                val categories = get5GamesByCategoryFromServiceUseCase()
                _categories.value = categories

            } catch (e: Exception) { // No internet connection.

                getCategoryGamesFromDao()

            }

        }

    }

    private fun getCategoryGamesFromDao() {

        viewModelScope.launch {

            try {

                val categories = getCategoryGamesFromDaoUseCase()
                _categories.value = categories

            } catch (_: Exception) {} // The database is empty.

        }

    }


    fun get5GamesByPlatformFromService() {

        viewModelScope.launch {

            try {

                val platforms = get5GamesByPlatformFromServiceUseCase()
                _platforms.value = platforms

            } catch (e: Exception) { // No internet connection.

                getPlatformGamesFromDao()

            }

        }

    }

    private fun getPlatformGamesFromDao() {

        viewModelScope.launch {

            try {

                val platforms = getPlatformGamesFromDaoUseCase()
                _platforms.value = platforms

            } catch (_: Exception) {} // The database is empty.

        }

    }


    fun get5GamesFromService() {

        viewModelScope.launch {

            try {

                val games = get5GamesFromServiceUseCase()
                _games.value = games

            } catch (e: Exception) { // No internet connection.

                getGamesFromDao()

            }

        }

    }

    private fun getGamesFromDao() {

        viewModelScope.launch {

            try {

                val games = getGamesFromDaoUseCase()
                _games.value = games

            } catch (_: Exception) {} // The database is empty.

        }

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