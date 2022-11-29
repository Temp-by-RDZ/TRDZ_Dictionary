package com.trdz.dictionary.base_utility.di

import com.trdz.dictionary.model.ADataSource
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.InternalData
import com.trdz.dictionary.model.RepositoryExecutor
import com.trdz.dictionary.model.data_source_basis.DataSourceBasis
import com.trdz.dictionary.model.data_source_room.InternalStorage
import com.trdz.dictionary.model.data_source_server.ServerRetrofit
import com.trdz.dictionary.view_model.SingleLiveData
import com.trdz.dictionary.view_model.StatusProcess
import com.trdz.dictionary.view_model.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ModuleViewModel {

	@Provides
	@Singleton
	fun provideRepository(
		internalStorage: InternalData,
		@ModuleRepository.Basis
		dataBasis: ADataSource,
		@ModuleRepository.Server
		dataServer: ADataSource,
		@ModuleRepository.Internal
		dataInternal: ADataSource,
	): RepositoryExecutor {
		return RepositoryExecutor(internalStorage, dataBasis, dataServer, dataInternal)
	}

	@Provides
	@Singleton
	fun provideLiveData(): SingleLiveData<StatusProcess> {
		return SingleLiveData()
	}

	@Provides
	@Singleton
	fun providesFactory(repository: RepositoryExecutor, dataLive: SingleLiveData<StatusProcess>): ViewModelFactory {
		return ViewModelFactory(repository,dataLive)
	}

}


