package com.trdz.dictionary.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trdz.dictionary.base_utility.IN_SERVER
import com.trdz.dictionary.base_utility.IN_STORAGE
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
	private val repository: Repository,
	private val dataLive: SingleLiveData<StatusProcess>,
): ViewModel() {

	fun getPodData(): LiveData<StatusProcess> = dataLive
	private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

	fun startSearch(target: String) {
		Log.d("@@@", "Prs - Start loading")
		with(dataLive) {
			postValue(StatusProcess.Loading)
			repository.setSource(IN_STORAGE)
			disposables.add(repository.getInitList(target)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{
						Log.d("@@@", "Prs - Internal load complete")
						val result = it.dataWord!!
						repository.dataUpdate(result.toMutableList())
						postValue(StatusProcess.Success(ModelResult(repository.getList())))
					},
					{
						Log.w("@@@", "Prs - Failed internal load start external loading $it")
						startLoad(target)
					}))
		}
	}

	private fun startLoad(target: String) {
		with(dataLive) {
			repository.setSource(IN_SERVER)
			disposables.add(repository.getInitList(target)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{
						Log.d("@@@", "Prs - External load complete")
						val result = it.dataWord!!
						repository.dataUpdate(result.toMutableList())
						disposables.add(repository.update(target))
						postValue(StatusProcess.Success(ModelResult(repository.getList())))
					},
					{
						Log.e("@@@", "Prs - Loading failed $it")
						postValue(StatusProcess.Error(-2, it))
					}))
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
		disposables.clear()
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