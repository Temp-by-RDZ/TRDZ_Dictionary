package com.trdz.dictionary

import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trdz.dictionary.view.MainActivity
import com.trdz.dictionary.view.segment_word.WindowWordListRecycle
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityWordsTest {

	private lateinit var scenario: ActivityScenario<MainActivity>

	@Before
	fun setup() {
		scenario = ActivityScenario.launch(MainActivity::class.java)
	}

	@Test
	fun activitySearch_ScrollTo() {
		onView(withId(R.id.target)).perform(setSearchViewText("dog"))
		onView(isRoot()).perform(delay())
		onView(withId(R.id.recyclerView))
			.perform(
				RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
					hasDescendant(withText("dogleg"))
				)
			)
	}

	@Test
	fun activitySearch_PerformClickAtPosition() {
		onView(withId(R.id.target)).perform(setSearchViewText("dog"))
		onView(isRoot()).perform(delay())
		onView(withId(R.id.recyclerView))
			.perform(
				RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
					0,
					click()
				)
			)
	}

	@Test
	fun activity_Stater_Enable() {
		onView(withId(R.id.disabler)).perform(clickBlind())
		onView(withId(R.id.enabler)).perform(click())
		onView(withId(R.id.disabler)).check(matches(isDisplayed()))
	}

	@Test
	fun activity_Stater_Disable() {
		onView(withId(R.id.enabler)).perform(clickBlind())
		onView(withId(R.id.disabler)).perform(click())
		onView(withId(R.id.enabler)).check(matches(isDisplayed()))
	}

	@Test
	fun activity_Search_IsStable() {
		onView(withId(R.id.enabler)).perform(clickBlind())
		onView(withId(R.id.target)).perform(setSearchViewText("god"))
			.check(searchViewCorrected("god"))
	}

	@Test
	fun activity_Search_IsWorking() {
		onView(withId(R.id.target)).perform(setSearchViewText("dog"))
		onView(isRoot()).perform(delay())
		onView(withId(R.id.naming)).check(matches(withText("Найденые слова")))
	}

	@Test
	fun activity_Search_InEnable() {
		onView(withId(R.id.disabler)).perform(clickBlind())
		onView(withId(R.id.enabler)).perform(click())
		onView(withId(R.id.target)).perform(setSearchViewText("dog"))
		onView(isRoot()).perform(delay())
		onView(withId(R.id.disabler)).perform(click())
		onView(withId(R.id.naming)).check(matches(withText("Найденые слова")))
	}

	private fun clickBlind(): ViewAction {
		return object: ViewAction {
			override fun getConstraints(): Matcher<View> = ViewMatchers.isEnabled()
			override fun getDescription(): String = "Blind click on view"
			override fun perform(uiController: UiController?, view: View?) {
				view?.apply {
					if (this.visibility == View.VISIBLE) view.performClick()
				}
			}
		}
	}

	private fun setSearchViewText(text: String): ViewAction {
		return object: ViewAction {
			override fun getConstraints(): Matcher<View> {
				return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
			}

			override fun getDescription(): String = "Change view text"
			override fun perform(uiController: UiController?, view: View?) {
				(view as SearchView).setQuery(text, false)
			}
		}
	}

	private fun searchViewCorrected(text: String): ViewAssertion = ViewAssertion { view, noViewFoundException ->
		if (view != null) {
			val searchView = (view as SearchView)
			Assert.assertEquals(text, searchView.query.toString())
		}
		else throw noViewFoundException
	}

	private fun delay(): ViewAction? {
		return object: ViewAction {
			override fun getConstraints(): Matcher<View> = isRoot()
			override fun getDescription(): String = "wait for $3 seconds"
			override fun perform(uiController: UiController, v: View?) {
				uiController.loopMainThreadForAtLeast(3000)
			}
		}
	}

	@After
	fun close() {
		scenario.close()
	}

	companion object {
		fun clickCentralized(): ViewAction {
			return GeneralClickAction(
				Tap.SINGLE,
				GeneralLocation.CENTER,
				Press.FINGER,
				InputDevice.SOURCE_MOUSE,
				MotionEvent.BUTTON_PRIMARY)
		}
	}
}
