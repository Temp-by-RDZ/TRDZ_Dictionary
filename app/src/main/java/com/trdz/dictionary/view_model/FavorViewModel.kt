package com.trdz.dictionary.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.dictionary.model.data.DataLine
import com.trdz.dictionary.model.Repository
import kotlinx.coroutines.*

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
		startSearch()
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
				favoriteData = repository.initFavorList().toMutableList()
				postValue(favoriteData)
			}
		}
	}

	fun itemMove(fromPosition: Int, toPosition: Int): List<DataLine> {
		favoriteData.removeAt(fromPosition).apply {
			favoriteData.add(toPosition, this)
		}
		jobs = scope.launch { repository.update(favoriteData) }
		return favoriteData.toList()
	}

	fun itemRemove(data: DataLine, position: Int): List<DataLine> {
		favoriteData.removeAt(position)
		jobs = scope.launch { repository.removeFavorite(data) }
		return favoriteData.toList()
	}

}