package com.trdz.dictionary.view

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.trdz.dictionary.R
import com.trdz.dictionary.utility.KEY_OPTIONS
import com.trdz.dictionary.utility.KEY_THEME
import com.trdz.dictionary.utility.stopToast
import org.koin.android.ext.android.inject
import java.util.concurrent.Executors

class MainActivity: AppCompatActivity() {

	//region Injected

	private val navigation: Navigation by inject()

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
		themeSettings()
		setContentView(R.layout.activity_main)
		menuConstruct()
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

	private fun menuConstruct() {
		setSupportActionBar(findViewById(R.id.toolbar))
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_tolbar_navigation, menu)
		return super.onCreateOptionsMenu(menu)
	}

	//endregion

}