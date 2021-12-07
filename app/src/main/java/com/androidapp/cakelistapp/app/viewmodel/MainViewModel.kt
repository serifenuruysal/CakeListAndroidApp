package com.androidapp.cakelistapp.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.cakelistapp.data.model.CakeModel
import com.androidapp.cakelistapp.data.repository.ApiResponse
import com.androidapp.cakelistapp.data.repository.CakeListRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nur Uysal on 06/12/2021.
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: CakeListRepositoryImpl) :
    ViewModel() {

    private val _data = MutableLiveData<MainViewState<MutableList<CakeModel>>>()

    val data: LiveData<MainViewState<MutableList<CakeModel>>>
        get() = _data

    init {
        getCakeList()
    }

    // suspend and resume make this network request main-safe
    // so our ViewModel doesn't need to worry about threading
    fun getCakeList() = viewModelScope.launch {
        _data.postValue(MainViewState.loading(null))
        when (val response = mainRepository.getCakeList()) {
            is ApiResponse.Error ->
                _data.postValue(MainViewState.error(response.error, null))
            is ApiResponse.Success ->
                _data.postValue(MainViewState.success(getFilteredCakeList(response.data.toMutableList())))
        }
    }

    //Remove duplicate entries
    //Order entries by name
    fun getFilteredCakeList(inputList: MutableList<CakeModel>?): MutableList<CakeModel> {
        return inputList?.distinctBy { it.title }?.sortedBy { it.title }?.toMutableList()
            ?: mutableListOf()
    }

}
