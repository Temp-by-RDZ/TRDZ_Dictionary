package com.trdz.dictionary

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trdz.dictionary.view.MainActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class ActivitySearchFragmentTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityFragment_NotNull() {
        scenario.onActivity {
            val wordFragmentBase = it.findViewById<SearchView>(R.id.target)
            assertNotNull(wordFragmentBase)
        }
    }

    @Test
    fun activityList_Included() {
        scenario.onActivity {
            val wordFragmentList = it.findViewById<TextView>(R.id.naming)
            assertNotNull(wordFragmentList)
        }
    }

    @Test
    fun activitySearchEnable_Working() {
        scenario.onActivity {
            val enableButton = it.findViewById<FrameLayout>(R.id.enabler)
            val disableFrame = it.findViewById<FrameLayout>(R.id.disabler)
            enableButton.performClick()
            assertEquals(View.VISIBLE, disableFrame.visibility)
        }
    }

    @Test
    fun activitySearchDisable_Working() {
        scenario.onActivity {
            val disableButton = it.findViewById<FrameLayout>(R.id.disabler)
            val enableFrame = it.findViewById<FrameLayout>(R.id.enabler)
            disableButton.performClick()
            assertEquals(View.VISIBLE, enableFrame.visibility)
        }
    }

    @Test
    fun activityEnableDisable_Conflict() {
        scenario.onActivity {
            val enableFrame = it.findViewById<FrameLayout>(R.id.enabler)
            val disableFrame = it.findViewById<FrameLayout>(R.id.disabler)
            assertNotEquals(disableFrame.visibility, enableFrame.visibility)
        }
    }

    @After
    fun close() {
        scenario.close()
        stopKoin()
    }
}
