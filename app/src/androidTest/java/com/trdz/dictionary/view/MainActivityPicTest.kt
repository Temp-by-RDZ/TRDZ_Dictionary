package com.trdz.dictionary.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.trdz.dictionary.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityPicTest {

	@Rule
	@JvmField
	var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

	@Test
	fun mainActivityPicTest() {
		val frameLayout = onView(
			allOf(withId(R.id.enabler),
				childAtPosition(
					childAtPosition(
						withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
						4),
					1),
				isDisplayed()))
		frameLayout.perform(click())

		val searchAutoComplete = onView(
			allOf(withId(R.id.search_src_text),
				childAtPosition(
					allOf(withId(R.id.search_plate),
						childAtPosition(
							withId(R.id.search_edit_frame),
							1)),
					0),
				isDisplayed()))
		searchAutoComplete.perform(click())

		val searchAutoComplete2 = onView(
			allOf(withId(R.id.search_src_text),
				childAtPosition(
					allOf(withId(R.id.search_plate),
						childAtPosition(
							withId(R.id.search_edit_frame),
							1)),
					0),
				isDisplayed()))
		searchAutoComplete2.perform(replaceText("fus"), closeSoftKeyboard())

		val frameLayout2 = onView(
			allOf(withId(R.id.disabler),
				childAtPosition(
					childAtPosition(
						withClassName(`is`("android.widget.LinearLayout")),
						0),
					1),
				isDisplayed()))
		frameLayout2.perform(click())

		val recyclerView = onView(
			allOf(withId(R.id.recyclerView),
				childAtPosition(
					withClassName(`is`("android.widget.FrameLayout")),
					0)))
		recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

		val recyclerView2 = onView(
			allOf(withId(R.id.recyclerView),
				childAtPosition(
					withClassName(`is`("android.widget.FrameLayout")),
					0)))
		recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

		val imageView = onView(
			allOf(withId(R.id.pic),
				withParent(allOf(withId(R.id.secondBox),
					withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)))),
				isDisplayed()))
		imageView.check(matches(isDisplayed()))
	}

	private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int,
    ): Matcher<View> {

		return object: TypeSafeMatcher<View>() {
			override fun describeTo(description: Description) {
				description.appendText("Child at position $position in parent ")
				parentMatcher.describeTo(description)
			}

			public override fun matchesSafely(view: View): Boolean {
				val parent = view.parent
				return parent is ViewGroup && parentMatcher.matches(parent)
						&& view == parent.getChildAt(position)
			}
		}
	}
}
