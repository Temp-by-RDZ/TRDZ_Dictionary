package com.trdz.dictionary.view

import android.view.Menu
import com.trdz.dictionary.R
import com.trdz.dictionary.utility.*
import com.trdz.dictionary.utility.MENU_FAVOR
import com.trdz.dictionary.utility.MENU_HISTORY
import kotlin.reflect.KProperty

class MenuController {
	private val delegator = DelegateMenu()

	fun itemActive(menu: Menu, ids: Int, icon: Int) {
		delegator.ids = ids
		delegator.icon = icon
		menuSet(menu)
	}
	fun itemDeactivate(menu: Menu, ids: Int) {
		delegator.ids = ids
		delegator.icon = ICON_BACK
		menuSet(menu)
	}
	private fun menuSet(menu: Menu) {
		val item = menu.findItem(delegator.ids)
		item.setIcon(delegator.icon)
	}
	fun subItem(menu: Menu, flag: Boolean) {
		delegator.ids = MENU_DELETE
		val dell = menu.findItem(delegator.ids)
		dell.isVisible = flag
	}

}

class DelegateMenu {
	var ids: Int by DelegateId()
	var icon: Int by DelegateIcon()
}
class DelegateId {
	var value = 0

	operator fun getValue(thisRef: Any?,property: KProperty<*>): Int {
		return when (value) {
			MENU_DELETE -> R.id.app_bar_delete
			MENU_HISTORY -> R.id.app_bar_history
			MENU_FAVOR -> R.id.app_bar_fav
			else -> {0}
		}
	}

	operator fun setValue(thisRef: Any?,property: KProperty<*>,value: Int) {
		this.value = value

	}
}

class DelegateIcon {
	var value = 0

	operator fun getValue(thisRef: Any?,property: KProperty<*>): Int {
		return when (value) {
			ICON_BACK -> R.drawable.ic_baseline_search_24
			ICON_HISTORY -> R.drawable.ic_baseline_books
			ICON_FAVOR -> R.drawable.ic_baseline_favorite_24
			else -> {0}
		}
	}

	operator fun setValue(thisRef: Any?,property: KProperty<*>,value: Int) {
		this.value = value
	}

}