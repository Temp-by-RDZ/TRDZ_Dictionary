package com.trdz.dictionary.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.dictionary.base_utility.IN_BASIS
import com.trdz.dictionary.base_utility.IN_SERVER
import com.trdz.dictionary.base_utility.IN_STORAGE
import com.trdz.dictionary.model.DataLine
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.Repository
import com.trdz.dictionary.model.RequestResults
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WordsViewModel(
	private val repository: Repository,
	private val dataLive: SingleLiveData<StatusProcess>,
): ViewModel() {

	private var currentData: MutableList<DataWord> = listOf<DataWord>().toMutableList()

	fun getData(): LiveData<StatusProcess> = dataLive

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

	private val querySearch = MutableStateFlow("")

	init {
		CoroutineScope(Dispatchers.Main).launch {
			querySearch.debounce(750)
				.filter { query -> return@filter query.isNotEmpty() }
				.distinctUntilChanged()
				.collect { result -> startSearch(result) }
		}
	}

	override fun onCleared() {
		jobs?.cancel()
		super.onCleared()
	}

	fun setSearch(search: String) {
		querySearch.value = search
	}

	private fun startSearch(target: String) {
		Log.d("@@@", "Prs - Start loading")
		jobs?.cancel()
		with(dataLive) {
			postValue(StatusProcess.Loading)
			repository.setSource(IN_STORAGE)
			jobs = scope.launch {
				when (val response = repository.initWordList(target)) {
					is RequestResults.SuccessWords -> {
						Log.d("@@@", "Prs - Internal load complete")
						currentData = emptyData(repository.analyze(response.data.dataWord!!)).toMutableList()
						postValue(StatusProcess.Success(ModelResult(currentData)))
					}
					is RequestResults.Error -> {
						Log.w("@@@", "Prs - Failed internal load start external loading ${response.error}")
						startLoad(target)
					}
				}
			}
		}
	}

	private fun startLoad(target: String) {
		with(dataLive) {
			postValue(StatusProcess.Loading)
			repository.setSource(IN_SERVER)
			jobs = scope.launch {
				when (val response = repository.initWordList(target)) {
					is RequestResults.SuccessWords -> {
						Log.d("@@@", "Prs - External load complete")
						currentData = emptyData(repository.analyze(response.data.dataWord!!)).toMutableList()
						repository.update(currentData, target)
						postValue(StatusProcess.Success(ModelResult(currentData)))

					}
					is RequestResults.Error -> {
						Log.e("@@@", "Prs - Loading failed ${response.error}")
						postValue(StatusProcess.Error(-2, response.error))
					}
				}
			}
		}
	}

	private suspend fun emptyData(list: List<DataWord>): List<DataWord> {
		return if (list.isEmpty()) {
			repository.setSource(IN_BASIS)
			val response = repository.initWordList("") as RequestResults.SuccessWords
			response.data.dataWord!!
		}
		else list
	}

	fun getSaved() {
		val saved = ModelResult(currentData)
		if (saved.data.isEmpty()) dataLive.postValue(StatusProcess.ForceSet(repository.checkLast()))
		else dataLive.postValue(StatusProcess.Success(saved))
	}

	fun favAdd(data: DataWord) {
		jobs = scope.launch { repository.addFavorite(DataLine(data.id, data.name)) }
	}

	fun favRemove(data: DataWord) {
		jobs = scope.launch { repository.removeFavorite(DataLine(data.id, data.name)) }
	}

	fun visualChange(data: DataWord, position: Int) {
		val count = if (data.visual.state == 1) {
			changeStateAt(data, position, 0)
		}
		else {
			changeStateAt(data, position, 1)
		}
		dataLive.postValue(StatusProcess.Change(currentData, position + 1, count))
	}

	private fun changeStateAt(data: DataWord, position: Int, state: Int): Int {
		currentData[position].visual.state = state
		return setState(state * 2, data.visual.group + 1)
	}

	private fun setState(state: Int, group: Int): Int {
		var count = 0
		currentData.forEach { tek ->
			if (tek.visual.group == group) {
				tek.visual.state = state
				count++
			}
		}
		return count
	}

}