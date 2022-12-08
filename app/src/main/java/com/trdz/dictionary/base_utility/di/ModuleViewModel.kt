package com.trdz.dictionary.base_utility.di

import com.trdz.dictionary.base_utility.*
import com.trdz.dictionary.model.DataLine
import com.trdz.dictionary.model.Repository
import com.trdz.dictionary.model.RepositoryExecutor
import com.trdz.dictionary.view_model.SingleLiveData
import com.trdz.dictionary.view_model.StatusProcess
import com.trdz.dictionary.view_model.ViewModelFactories
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleViewModelK = module {
	single<Repository>() {
		RepositoryExecutor(
			internalStorage = get(),
			dataBasis = get(named(KK_BASIS)),
			dataServer = get(named(KK_SERVER)),
			dataInternal = get(named(KK_INTERNAL)))
	}
	single<SingleLiveData<StatusProcess>>(named(KK_DLBASIC)) { SingleLiveData() }
	single<SingleLiveData<List<DataLine>>>(named(KK_DLLINE)) { SingleLiveData() }
	single<ViewModelFactories>() {
		ViewModelFactories(
			repository = get(),
			dataLive = get(named(KK_DLBASIC)),
			dataLiveList = get(named(KK_DLLINE)))
	}
}


