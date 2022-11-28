package com.trdz.dictionary.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.dictionary.base_utility.*
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.RepositoryExecutor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
	private val dataLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val repository: RepositoryExecutor = RepositoryExecutor()
	): ViewModel() {

	fun getPodData(): LiveData<StatusProcess> = dataLive

	fun startSearch(target: String) {
		Log.d("@@@", "Prs - Start loading")
		with(dataLive) {
			repository.setSource(IN_STORAGE)
			postValue(StatusProcess.Loading)
			repository.getInitList(target)
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
					})
		}
	}

	private fun startLoad(target: String) {
		with(dataLive) {
			repository.setSource(IN_SERVER)
			repository.getInitList(target)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{
						Log.d("@@@", "Prs - External load complete")
						val result = it.dataWord!!
						repository.dataUpdate(result.toMutableList())
						repository.update(target)
						postValue(StatusProcess.Success(ModelResult(repository.getList())))
					},
					{
						Log.e("@@@", "Prs - Loading failed $it")
						postValue(StatusProcess.Error(-2,it))
					})
		}
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


}