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

    private val gameFromService: GameFromService,
    private val gameFromDao: GameFromDao,
    private val categoriesFromService: CategoriesFromService,
    private val categoriesFromDao: CategoriesFromDao,
    private val platformsFromService: PlatformsFromService,
    private val platformsFromDao: PlatformsFromDao

    ) : ViewModel() {

    private val _game = MutableLiveData<GameItem>()
    val game: LiveData<GameItem> get() = _game

    private val _categories = MutableLiveData<List<GameItem>>()
    val categories: LiveData<List<GameItem>> get() = _categories

    private val _platforms = MutableLiveData<List<GameItem>>()
    val platforms: LiveData<List<GameItem>> get() = _platforms

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    private val _textViewVisibility = MutableLiveData<Boolean>()
    val textViewVisibility: LiveData<Boolean> get() = _textViewVisibility

    private val _nestedScrollViewVisibility = MutableLiveData<Boolean>()
    val nestedScrollViewVisibility: LiveData<Boolean> get() = _nestedScrollViewVisibility


    fun getGameFromService() {

        showProgressBar()

        viewModelScope.launch {

            try {

                val game = gameFromService()
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

                val game = gameFromDao()
                _game.value = game
                showNestedScrollView()

            } catch (e: Exception) { // The database is empty

                _message.value = " Check your internet connection"
                showTextView()

            }

        }

    }


    fun getCategoriesFromService() {

        viewModelScope.launch {

            try {

                val categories = categoriesFromService()
                _categories.value = categories

            } catch (e: Exception) { // No internet connection.

                getCategoriesFromDao()

            }

        }

    }

    private fun getCategoriesFromDao() {

        viewModelScope.launch {

            try {

                val categories = categoriesFromDao()
                _categories.value = categories

            } catch (_: Exception) {} // The database is empty.

        }

    }


    fun getPlatformsFromService() {

        viewModelScope.launch {

            try {

                val platforms = platformsFromService()
                _platforms.value = platforms

            } catch (e: Exception) { // No internet connection.

                getPlatformsFromDao()

            }

        }

    }

    private fun getPlatformsFromDao() {

        viewModelScope.launch {

            try {

                val platforms = platformsFromDao()
                _platforms.value = platforms

            } catch (_: Exception) {} // The database is empty.

        }

    }


    private fun showProgressBar() {

        _progressBarVisibility.value = true
        _textViewVisibility.value = false
        _nestedScrollViewVisibility.value = false

    }

    private fun showTextView() {

        _textViewVisibility.value = true
        _progressBarVisibility.value = false
        _nestedScrollViewVisibility.value = false

    }

    private fun showNestedScrollView() {

        _nestedScrollViewVisibility.value = true
        _progressBarVisibility.value = false
        _textViewVisibility.value = false

    }


}