package com.trdz.dictionary.model

import io.reactivex.rxjava3.core.Completable

interface InternalData {
	fun saveUsers(words: List<DataWord>): Completable
}