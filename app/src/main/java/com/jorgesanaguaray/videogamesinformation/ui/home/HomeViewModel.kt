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

    private val recommendedGameFromService: RecommendedGameFromService,
    private val recommendedGameFromDao: RecommendedGameFromDao,
    private val recommendedCategoriesFromService: RecommendedCategoriesFromService,
    private val recommendedCategoriesFromDao: RecommendedCategoriesFromDao,
    private val recommendedPlatformsFromService: RecommendedPlatformsFromService,
    private val recommendedPlatformsFromDao: RecommendedPlatformsFromDao,
    private val recommendedGamesFromService: RecommendedGamesFromService,
    private val recommendedGamesFromDao: RecommendedGamesFromDao

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
        getRecommendedGameFromService()
        getRecommendedCategoriesFromService()
        getRecommendedPlatformsFromService()
        getRecommendedGamesFromService()
    }

    fun getRecommendedGameFromService() {

        showProgressBar()

        viewModelScope.launch {

            try {

                val game = recommendedGameFromService()
                _game.value = game
                showNestedScrollView()

            } catch (e: Exception) { // No internet connection.

                getRecommendedGameFromDao()

            }

        }

    }

    private fun getRecommendedGameFromDao() {

        viewModelScope.launch {

            try {

                val game = recommendedGameFromDao()
                _game.value = game
                showNestedScrollView()

            } catch (e: Exception) { // The database is empty

                showTextViewNoInternet()

            }

        }

    }

    fun getRecommendedCategoriesFromService() {

        viewModelScope.launch {

            try {

                val categories = recommendedCategoriesFromService()
                _categories.value = categories

            } catch (e: Exception) { // No internet connection.

                getRecommendedCategoriesFromDao()

            }

        }

    }

    private fun getRecommendedCategoriesFromDao() {

        viewModelScope.launch {

            try {

                val categories = recommendedCategoriesFromDao()
                _categories.value = categories

            } catch (_: Exception) {} // The database is empty.

        }

    }

    fun getRecommendedPlatformsFromService() {

        viewModelScope.launch {

            try {

                val platforms = recommendedPlatformsFromService()
                _platforms.value = platforms

            } catch (e: Exception) { // No internet connection.

                getRecommendedPlatformsFromDao()

            }

        }

    }

    private fun getRecommendedPlatformsFromDao() {

        viewModelScope.launch {

            try {

                val platforms = recommendedPlatformsFromDao()
                _platforms.value = platforms

            } catch (_: Exception) {} // The database is empty.

        }

    }

    fun getRecommendedGamesFromService() {

        viewModelScope.launch {

            try {

                val games = recommendedGamesFromService()
                _games.value = games

            } catch (e: Exception) { // No internet connection.

                getRecommendedGamesFromDao()

            }

        }

    }

    private fun getRecommendedGamesFromDao() {

        viewModelScope.launch {

            try {

                val games = recommendedGamesFromDao()
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