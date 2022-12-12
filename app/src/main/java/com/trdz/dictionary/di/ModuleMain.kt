package com.trdz.dictionary.di

import com.trdz.dictionary.R
import com.trdz.dictionary.view.Navigation
import org.koin.dsl.module

val moduleMainK = module {
	single<Navigation>() { Navigation(R.id.container_fragment_base) }
}


