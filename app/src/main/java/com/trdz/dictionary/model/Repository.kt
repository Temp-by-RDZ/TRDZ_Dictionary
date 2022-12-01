package com.trdz.dictionary.model

interface Repository {
	fun setSource(index: Int)
	suspend fun getInitList(target: String): RequestResults
	fun dataUpdate(data: MutableList<DataWord>)
	fun update(target: String)
	fun getList(): List<DataWord>
	fun changeStateAt(data: DataWord, position: Int, state: Int): Int
}