package com.trdz.dictionary.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trdz.dictionary.model.DataLine
import com.trdz.dictionary.model.Repository

class ViewModelFactories(
	private val repository: Repository,
	private val dataLive: SingleLiveData<StatusProcess>,
	private val dataLiveList: SingleLiveData<List<DataLine>>,
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
		WordsViewModel::class.java -> WordsViewModel(repository, dataLive)
		FavorViewModel::class.java -> FavorViewModel(repository, dataLiveList)
		else -> throw IllegalArgumentException("Unknown ViewModel class")
	} as T

}