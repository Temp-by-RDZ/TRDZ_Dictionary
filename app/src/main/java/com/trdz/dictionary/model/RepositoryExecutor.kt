package com.trdz.dictionary.model

import android.util.Log
import com.trdz.dictionary.base_utility.IN_BASIS
import com.trdz.dictionary.base_utility.IN_SERVER
import com.trdz.dictionary.base_utility.IN_STORAGE
import io.reactivex.rxjava3.core.Single

class RepositoryExecutor(
	private val internalStorage: InternalData,
	private val dataBasis: ADataSource,
	private val dataServer: ADataSource,
	private val dataInternal: ADataSource,
) {

	private var currentData: MutableList<DataWord> = listOf<DataWord>().toMutableList()
	private lateinit var dataSource: ADataSource

	fun dataUpdate(data: MutableList<DataWord>) {
		if (data.isEmpty()) {
			data.add(DataWord("Не найдено","Этого слова нет в ScyEng базе",-1,""))
		}
		currentData = data
	}

	fun setSource(index: Int) {
		when (index) {
			IN_BASIS -> dataSource = dataBasis
			IN_STORAGE -> dataSource = dataInternal
			IN_SERVER -> dataSource = dataServer
		}
	}

	fun update(target: String) {
		Log.d("@@@", "Rep - User Saving...")
		internalStorage.saveWords(currentData,target).subscribe({
			Log.d("@@@", "Rep - ...Done")
		}, {
			Log.d("@@@", "Rep - ...Failed $it")
		})
	}

	fun getInitList(target: String): Single<ServersResult> {
		return dataSource.loadWords(target)
	}

	fun getList(): List<DataWord> {
		return currentData
	}

	fun changeStateAt(data: DataWord, position: Int, state: Int): Int {
		currentData[position].state = state
		return setState(state * 2, data.group + 1)
	}

	private fun setState(state: Int, group: Int): Int {
		var count = 0
		currentData.forEach { tek ->
			if (tek.group == group) {
				tek.state = state
				count++
			}
		}
		return count
	}


}
