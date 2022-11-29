package com.trdz.dictionary.base_utility.di

import com.trdz.dictionary.view.MainActivity
import com.trdz.dictionary.view.WindowStart
import com.trdz.dictionary.view.segment_users.WindowWordListImp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		ModuleMain::class,
		ModuleViewModel::class,
		ModuleRepository::class,
	]
)

interface Component {

	fun inject(mainActivity: MainActivity)
	fun inject(fragment: WindowStart)
	fun inject(fragment: WindowWordListImp)

}
