package com.trdz.dictionary.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trdz.dictionary.base_utility.IN_SERVER
import com.trdz.dictionary.base_utility.IN_STORAGE
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.Repository
import com.trdz.dictionary.model.RequestResults
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel(
	private val repository: Repository,
	private val dataLive: SingleLiveData<StatusProcess>,
): ViewModel() {

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
			querySearch.debounce(500)
				.filter { query -> return@filter query.isNotEmpty() }
				.distinctUntilChanged()
				.collect { result -> startSearch(result) }
		}
	}
	private fun dataFromNetwork(query: String): Flow<String> {
		return flow {
			emit(query)
		}
	}
	fun setSearch(search: String) {
		querySearch.value = search
	}

	fun getPodData(): LiveData<StatusProcess> = dataLive

	private fun startSearch(target: String) {
		Log.d("@@@", "Prs - Start loading")
		jobs?.cancel()
		with(dataLive) {
			postValue(StatusProcess.Loading)
			repository.setSource(IN_STORAGE)
			jobs = scope.launch {
				when (val response = repository.getInitList(target)) {
					is RequestResults.Success -> {
						Log.d("@@@", "Prs - Internal load complete")
						val result = response.data.dataWord!!
						repository.dataUpdate(result.toMutableList())
						postValue(StatusProcess.Success(ModelResult(repository.getList())))

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
		jobs?.cancel()
		with(dataLive) {
			postValue(StatusProcess.Loading)
			repository.setSource(IN_SERVER)
			jobs = scope.launch {
				when (val response = repository.getInitList(target)) {
					is RequestResults.Success -> {
						Log.d("@@@", "Prs - External load complete")
						val result = response.data.dataWord!!
						repository.dataUpdate(result.toMutableList())
						repository.update(target)
						postValue(StatusProcess.Success(ModelResult(repository.getList())))

					}
					is RequestResults.Error -> {
						Log.e("@@@", "Prs - Loading failed ${response.error}")
						postValue(StatusProcess.Error(-2, response.error))
					}
				}
			}
		}
	}

	fun getSaved() {
		dataLive.postValue(StatusProcess.Success(ModelResult(repository.getList())))
	}

	fun visualChange(data: DataWord, position: Int) {
		val count = if (data.state == 1) {
			repository.changeStateAt(data, position, 0)
		}
		else {
			repository.changeStateAt(data, position, 1)
		}
		dataLive.postValue(StatusProcess.Change(repository.getList(), position + 1, count))
	}

	override fun onCleared() {
		super.onCleared()
	}
}

class ViewModelFactory(
	private val repository: Repository,
	private val dataLive: SingleLiveData<StatusProcess>,
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T {
		return MainViewModel(repository, dataLive) as T
	}

}