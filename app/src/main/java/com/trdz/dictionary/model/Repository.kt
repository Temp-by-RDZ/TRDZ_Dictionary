package com.trdz.dictionary.model

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

interface Repository {
	fun setSource(index: Int)
	fun getInitList(target: String): Single<ServersResult>
	fun dataUpdate(data: MutableList<DataWord>)
	fun update(target: String): Disposable
	fun getList(): List<DataWord>
	fun changeStateAt(data: DataWord, position: Int, state: Int): Int
}