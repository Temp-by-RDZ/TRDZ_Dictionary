package com.trdz.dictionary.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trdz.dictionary.MyApp
import com.trdz.dictionary.R
import com.trdz.dictionary.base_utility.KEY_OPTIONS
import com.trdz.dictionary.base_utility.KEY_THEME
import com.trdz.dictionary.base_utility.di.ModuleMain.provideNavigation
import com.trdz.dictionary.base_utility.showToast
import com.trdz.dictionary.base_utility.stopToast
import com.trdz.dictionary.model.RepositoryExecutor
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

	//region Elements

	@Inject lateinit var navigation: Navigation

	//endregion

	//region Customization

	override fun onBackPressed() {
		val fragmentList = supportFragmentManager.fragments

		var handled = false
		for (f in fragmentList) {
			if (f is CustomOnBackPressed) {
				handled = f.onBackPressed()
				if (handled) {
					break
				}
			}
		}

		if (!handled) super.onBackPressed()
	}

	//endregion

	//region Base realization
	override fun onDestroy() {
		stopToast()
		super.onDestroy()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		MyApp.instance.appComponent.inject(this)
		themeSettings()
		setContentView(R.layout.activity_main)
		if (savedInstanceState == null) {
			Log.d("@@@", "Start program")
			navigation.add(supportFragmentManager, WindowStart(), false, R.id.container_fragment_primal)
		}
	}

	private fun themeSettings() {
		when (getSharedPreferences(KEY_OPTIONS, Context.MODE_PRIVATE).getInt(KEY_THEME, 0)) {
			0 -> setTheme(R.style.MyBaseTheme)
			1 -> setTheme(R.style.MyGoldTheme)
			2 -> setTheme(R.style.MyFiolTheme)
		}
	}

	//endregion

}