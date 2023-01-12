package com.trdz.dictionary.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.geekbrains.tests.*
import com.geekbrains.tests.TEST_ITEM_MEMORY
import com.geekbrains.tests.TEST_ITEM_SEARCHER
import com.geekbrains.tests.TEST_MENU_FAVOR
import com.geekbrains.tests.TEST_MENU_HISTORY
import com.geekbrains.tests.TEST_TITLE_FAV
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {


	private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
	private val context = ApplicationProvider.getApplicationContext<Context>()
	private val packageName = context.packageName

	@Before
	fun setup() {
		uiDevice.pressHome()
		val intent = context.packageManager.getLaunchIntentForPackage(packageName)
		intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)  //Чистим бэкстек от запущенных ранее Активити
		context.startActivity(intent)
		uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
	}

	@Test
	fun test_WorkStateStarted() {
		val bottomBar = uiDevice.findObject(By.res(packageName, TEST_ITEM_SEARCHER))
		Assert.assertNotNull(bottomBar)
	}

	@Test
	fun test_OpenFavorScreen() {
		val toFavor: UiObject2 = uiDevice.findObject( By.res(packageName, TEST_MENU_FAVOR))
		toFavor.click()
		val qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		val title = uiDevice.findObject(By.res(packageName, TEST_ITEM_TITLE))
		Assert.assertEquals(title.text, TEST_TITLE_FAV)
	}

	@Test
	fun test_BackFromFavorScreen() {
		var toFavor: UiObject2 = uiDevice.findObject( By.res(packageName, TEST_MENU_FAVOR))
		toFavor.click()
		val qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		toFavor = uiDevice.findObject( By.res(packageName, TEST_MENU_FAVOR))
		toFavor.click()
		val searcher = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_SEARCH)),
			TIMEOUT
		)
		Assert.assertNotNull(searcher)
	}

	@Test
	fun test_FromFavorToHistoryScreen() {
		val toFavor: UiObject2 = uiDevice.findObject( By.res(packageName, TEST_MENU_FAVOR))
		toFavor.click()
		var qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		val toHistory: UiObject2 =  uiDevice.findObject( By.res(packageName, TEST_MENU_HISTORY))
		toHistory.click()
		qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		val title = uiDevice.findObject(By.res(packageName, TEST_ITEM_TITLE))
		Assert.assertEquals(title.text, TEST_TITLE_HISTORY)
	}

	@Test
	fun test_OpenHistoryScreen() {
		val toHistory: UiObject2 = uiDevice.findObject( By.res(packageName, TEST_MENU_HISTORY))
		toHistory.click()
		val qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		val title = uiDevice.findObject(By.res(packageName, TEST_ITEM_TITLE))
		Assert.assertEquals(title.text, TEST_TITLE_HISTORY)
	}

	@Test
	fun test_BackFromHistoryScreen() {
		val toHistory: UiObject2 = uiDevice.findObject( By.res(packageName, TEST_MENU_HISTORY))
		toHistory.click()
		val qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		val toFavor: UiObject2 = uiDevice.findObject( By.res(packageName, TEST_MENU_HISTORY))
		toFavor.click()
		val searcher = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_SEARCH)),
			TIMEOUT
		)
		Assert.assertNotNull(searcher)
	}

	@Test
	fun test_FromHistoryToFavorScreen() {
		var toHistory: UiObject2 = uiDevice.findObject( By.res(packageName, TEST_MENU_HISTORY))
		toHistory.click()
		var qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		toHistory = uiDevice.findObject( By.res(packageName, TEST_MENU_FAVOR))
		toHistory.click()
		qualifier = uiDevice.wait(
			Until.findObject(By.res(packageName, TEST_ITEM_MEMORY)),
			TIMEOUT
		)
		Assert.assertNotNull(qualifier)
		val title = uiDevice.findObject(By.res(packageName, TEST_ITEM_TITLE))
		Assert.assertEquals(title.text, TEST_TITLE_FAV)
	}

	companion object {
		private const val TIMEOUT = 7000L
	}
}
