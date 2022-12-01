package com.trdz.dictionary

import android.app.Application
import com.trdz.dictionary.base_utility.di.*
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.koin.core.context.startKoin

class MyApp: Application() {

	companion object {
		lateinit var instance: MyApp
		lateinit var di: DI
	}

	override fun onCreate() {
		super.onCreate()
		instance = this
		di = DIImpl().apply {
			DIModule(this, instance)
		}
		RxJavaPlugins.setErrorHandler {/*None*/ }
		startKoin {
			modules(listOf(moduleMainK, moduleRepositoryK, moduleViewModelK))
		}
	}

}