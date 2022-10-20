package com.jorgesanaguaray.videogamesinformation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.GetRandomGameFromApiInteractor
import com.jorgesanaguaray.videogamesinformation.domain.GetRandomGameFromDatabaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val getRandomGameFromApiInteractor: GetRandomGameFromApiInteractor,
    private val getRandomGameFromDatabaseInteractor: GetRandomGameFromDatabaseInteractor

    ) : ViewModel() {

    private val _game = MutableLiveData<GameItem>()
    val game: LiveData<GameItem> get() = _game

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    private val _textViewVisibility = MutableLiveData<Boolean>()
    val textViewVisibility: LiveData<Boolean> get() = _textViewVisibility

    private val _scrollViewVisibility = MutableLiveData<Boolean>()
    val scrollViewVisibility: LiveData<Boolean> get() = _scrollViewVisibility

    fun getRandomGameFromApi() {

        showProgressBar()

        viewModelScope.launch {

            try {

                val game = getRandomGameFromApiInteractor()
                _game.value = game
                showScrollView()

            } catch (e: Exception) { // No internet connection.

                getRandomGameFromDatabase()

            }

        }

    }

    private fun getRandomGameFromDatabase() {

        viewModelScope.launch {

            try {

                val game = getRandomGameFromDatabaseInteractor()
                _game.value = game
                showScrollView()

            } catch (e: Exception) { // The database returns an empty list.

                _message.value = e.message
                showTextView()

            }

        }

    }

    private fun showProgressBar() {

        _progressBarVisibility.value = true
        _textViewVisibility.value = false
        _scrollViewVisibility.value = false

    }

    private fun showTextView() {

        _textViewVisibility.value = true
        _progressBarVisibility.value = false
        _scrollViewVisibility.value = false

    }

    private fun showScrollView() {

        _scrollViewVisibility.value = true
        _progressBarVisibility.value = false
        _textViewVisibility.value = false

    }

}