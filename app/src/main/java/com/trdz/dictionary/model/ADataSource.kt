package com.trdz.dictionary.model

import io.reactivex.rxjava3.core.Single

interface ADataSource {
	fun loadWords(target: String): Single<ServersResult>
}