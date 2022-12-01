package com.trdz.dictionary.base_utility.di

import com.trdz.dictionary.base_utility.KK_BASIS
import com.trdz.dictionary.base_utility.KK_INTERNAL
import com.trdz.dictionary.base_utility.KK_SERVER
import com.trdz.dictionary.model.ADataSource
import com.trdz.dictionary.model.InternalData
import com.trdz.dictionary.model.data_source_basis.DataSourceBasis
import com.trdz.dictionary.model.data_source_room.InternalStorage
import com.trdz.dictionary.model.data_source_server.ServerRetrofit
import org.koin.core.qualifier.named
import org.koin.dsl.module


val moduleRepositoryK = module {
	single<InternalData>() { InternalStorage() }
	single<ADataSource>(named(KK_BASIS)) { DataSourceBasis() }
	single<ADataSource>(named(KK_SERVER)) { ServerRetrofit() }
	single<ADataSource>(named(KK_INTERNAL)) { InternalStorage() }
}


