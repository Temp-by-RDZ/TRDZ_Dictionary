package com.trdz.dictionary.base_utility.di

import com.trdz.dictionary.base_utility.KK_BASIS
import com.trdz.dictionary.base_utility.KK_INTERNAL
import com.trdz.dictionary.base_utility.KK_SERVER
import com.trdz.dictionary.model.Repository
import com.trdz.dictionary.model.RepositoryExecutor
import com.trdz.dictionary.view_model.SingleLiveData
import com.trdz.dictionary.view_model.StatusProcess
import com.trdz.dictionary.view_model.ViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleViewModelK = module {
	single<Repository>() { RepositoryExecutor(
		internalStorage = get() ,
		dataBasis = get(named(KK_BASIS)),
		dataServer = get(named(KK_SERVER)),
		dataInternal = get(named(KK_INTERNAL))) }
	single<SingleLiveData<StatusProcess>>() { SingleLiveData() }
	single<ViewModelFactory>() { ViewModelFactory(repository = get(), dataLive = get()) }
}


