package com.jorgesanaguaray.videogamesinformation.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.videogamesinformation.domain.CategoriesGames
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoriesGames: CategoriesGames) : ViewModel() {

    private val _games = MutableLiveData<List<GameItem>>()
    val games: LiveData<List<GameItem>> get() = _games

    private val _textInputLayoutVisibility = MutableLiveData<Boolean>()
    val textInputLayoutVisibility: LiveData<Boolean> get() = _textInputLayoutVisibility

    private val _recyclerViewVisibility = MutableLiveData<Boolean>()
    val recyclerViewVisibility: LiveData<Boolean> get() = _recyclerViewVisibility

    private val _textViewNoInternetVisibility = MutableLiveData<Boolean>()
    val textViewNoInternetVisibility: LiveData<Boolean> get() = _textViewNoInternetVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    fun getCategoriesFromService(category: String) {

        showProgressBar()

        viewModelScope.launch {

            try {

                val games = categoriesGames(category)
                _games.value = games
                showTextInputLayoutAndRecyclerView()

            } catch (e: Exception) { // No internet connection.
                showTextViewNoInternet()
            }

        }

    }

    private fun showTextInputLayoutAndRecyclerView() {

        _textInputLayoutVisibility.value = true
        _recyclerViewVisibility.value = true
        _textViewNoInternetVisibility.value = false
        _progressBarVisibility.value = false

    }

    private fun showTextViewNoInternet() {

        _textInputLayoutVisibility.value = false
        _recyclerViewVisibility.value = false
        _textViewNoInternetVisibility.value = true
        _progressBarVisibility.value = false

    }

    private fun showProgressBar() {

        _textInputLayoutVisibility.value = false
        _recyclerViewVisibility.value = false
        _textViewNoInternetVisibility.value = false
        _progressBarVisibility.value = true

    }

}