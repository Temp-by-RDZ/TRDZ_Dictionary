package com.trdz.dictionary.base_utility.di

import com.trdz.dictionary.R
import com.trdz.dictionary.view.Navigation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ModuleMain {

	@Provides
	@Singleton
	fun provideNavigation(): Navigation {
		return Navigation(R.id.container_fragment_base)
	}

}


