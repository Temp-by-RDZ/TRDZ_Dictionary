package com.trdz.dictionary.base_utility.di

import com.trdz.dictionary.R
import com.trdz.dictionary.model.ADataSource
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.InternalData
import com.trdz.dictionary.model.data_source_basis.DataSourceBasis
import com.trdz.dictionary.model.data_source_room.InternalStorage
import com.trdz.dictionary.model.data_source_server.ServerRetrofit
import com.trdz.dictionary.view.Navigation
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object ModuleRepository {

	@Provides
	@Singleton
	fun provideInternalStorage(): InternalData {
		return InternalStorage()
	}
	@Provides
	@Singleton
	@Basis
	fun provideDataBasis(): ADataSource {
		return DataSourceBasis()
	}
	@Provides
	@Singleton
	@Server
	fun provideDataServer(): ADataSource {
		return ServerRetrofit()
	}
	@Provides
	@Singleton
	@Internal
	fun provideDataInternal(): ADataSource {
		return InternalStorage()
	}

	@Qualifier
	annotation class Basis
	@Qualifier
	annotation class Server
	@Qualifier
	annotation class Internal
}


