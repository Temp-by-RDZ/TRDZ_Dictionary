package com.trdz.dictionary.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.dictionary.model.data.DataLine
import com.trdz.dictionary.model.Repository
import kotlinx.coroutines.*

class HistoryViewModel(
	private val repository: Repository,
	private val dataLive: SingleLiveData<List<DataLine>>,
): ViewModel() {

	private var historyData: MutableList<DataLine> = listOf<DataLine>().toMutableList()
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
				historyData = repository.initSearchList().toMutableList()
				postValue(historyData)
			}
		}
	}

}