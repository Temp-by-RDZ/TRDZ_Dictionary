package com.trdz.dictionary

import android.app.Application
import com.trdz.dictionary.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@MyApp)
			modules(listOf(moduleMainK, moduleRepositoryK, moduleViewModelK, moduleDataK))
		}
	}

}