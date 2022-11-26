package com.trdz.dictionary.presenter

import android.util.Log
import com.trdz.dictionary.base_utility.*
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.RepositoryExecutor
import com.trdz.dictionary.view.segment_users.WindowWordList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class MainPresenter(private val repository: RepositoryExecutor): MvpPresenter<WindowWordList>() {

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
	}

	fun startSearch(target: String) {
		Log.d("@@@", "Prs - Start loading")
		with(viewState) {
			repository.setSource(IN_STORAGE)
			loadingState(true)
			repository.getInitList(target)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{
						Log.d("@@@", "Prs - Internal load complete")
						val result = it.dataWord!!
						repository.dataUpdate(result.toMutableList())
						refresh(result)
						loadingState(false)
					},
					{
						Log.w("@@@", "Prs - Failed internal load start external loading $it")
						startLoad(target)
					})
		}
	}

	private fun startLoad(target: String) {
		with(viewState) {
			repository.setSource(IN_SERVER)
			repository.getInitList(target)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{
						Log.d("@@@", "Prs - External load complete")
						val result = it.dataWord!!
						repository.dataUpdate(result.toMutableList())
						repository.update()
						refresh(result)
						loadingState(false)
					},
					{
						Log.e("@@@", "Prs - Loading failed $it")
						viewState.errorCatch()
						loadingState(false)
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
		viewState.changeState(repository.getList(), position + 1, count)
	}


}