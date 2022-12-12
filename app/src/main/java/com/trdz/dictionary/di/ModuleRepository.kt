package com.trdz.dictionary.di

import com.trdz.dictionary.model.data.ADataSource
import com.trdz.dictionary.model.data.InternalData
import com.trdz.dictionary.model.source_basis.DataSourceBasis
import com.trdz.dictionary.model.source_room.InternalStorage
import com.trdz.dictionary.model.source_server.ServerRetrofit
import com.trdz.dictionary.utility.KK_BASIS
import com.trdz.dictionary.utility.KK_INTERNAL
import com.trdz.dictionary.utility.KK_SERVER
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleRepositoryK = module {
	single<InternalData>() { InternalStorage() }
	single<ADataSource>(named(KK_BASIS)) { DataSourceBasis() }
	single<ADataSource>(named(KK_SERVER)) { ServerRetrofit() }
	single<ADataSource>(named(KK_INTERNAL)) { InternalStorage() }
}


