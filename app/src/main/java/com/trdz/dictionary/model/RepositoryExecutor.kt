package com.trdz.dictionary.model

import android.util.Log
import com.trdz.dictionary.base_utility.IN_BASIS
import com.trdz.dictionary.base_utility.IN_SERVER
import com.trdz.dictionary.base_utility.IN_STORAGE
import com.trdz.dictionary.model.data_source_basis.DataSourceBasis
import com.trdz.dictionary.model.data_source_room.InternalStorage
import com.trdz.dictionary.model.data_source_server.ServerRetrofit
import io.reactivex.rxjava3.core.Single

class RepositoryExecutor {

	private lateinit var dataSource: ADataSource
	private lateinit var currentData: MutableList<DataWord>
	private val internalStorage: InternalStorage = InternalStorage()

	fun dataUpdate(data: MutableList<DataWord>) {
		currentData = data
	}

	fun setSource(index: Int) {
		when (index) {
			IN_BASIS -> dataSource = DataSourceBasis()
			IN_STORAGE -> dataSource = InternalStorage()
			IN_SERVER -> dataSource = ServerRetrofit()
		}
	}

	fun update() {
		Log.d("@@@", "Rep - User Saving...")
		internalStorage.saveUsers(currentData).subscribe({
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
