package com.trdz.dictionary.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.dictionary.base_utility.IN_STORAGE
import com.trdz.dictionary.model.DataLine
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.Repository
import com.trdz.dictionary.model.RequestResults
import kotlinx.coroutines.*
import java.util.ArrayList

class FavorViewModel(
	private val repository: Repository,
	private val dataLive: SingleLiveData<List<DataLine>>,
): ViewModel() {

	private var favoriteData: MutableList<DataLine> = listOf<DataLine>().toMutableList()
	fun getData(): LiveData<List<DataLine>> = dataLive

	private val scope = CoroutineScope(
		Dispatchers.IO
				+ SupervisorJob()
				+ CoroutineExceptionHandler { _, throwable ->
			handleError(throwable)
		})

	private fun handleError(throwable: Throwable) {
		Log.w("@@@", "Prs - Coroutine dead $throwable")
	}

	private var jobs: Job? = null

	init {
		val list = mutableListOf<DataLine>()
		for (i in 0..99) {
			list.add(DataLine(i, "Заметка ${i + 1}"))
		}
		favoriteData = list
		dataLive.postValue(favoriteData)
	}

	override fun onCleared() {
		jobs?.cancel()
		super.onCleared()
	}

	private fun startSearch() {
		Log.d("@@@", "Prs - Start loading")
		jobs?.cancel()
		with(dataLive) {
			jobs = scope.launch {
				val response = repository.initFavorList()
				postValue(response)
			}
		}
	}

	fun itemMove(fromPosition: Int, toPosition: Int): List<DataLine> {
		favoriteData.removeAt(fromPosition).apply {
			favoriteData.add(toPosition, this)
		}
		return favoriteData.toList()
	}

	fun itemRemove(position: Int): List<DataLine> {
		favoriteData.removeAt(position)
		return favoriteData.toList()
	}

}