package com.github.polydome.popstash.app.feature.stash

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.polydome.popstash.app.R
import org.junit.Test

class SaveFromClipboardFragmentTest {
    @Test
    fun onSaveClick_callsSaveCommand() {
        val scenario = launchFragmentInContainer<SaveFromClipboardFragment>()
        scenario.moveToState(Lifecycle.State.CREATED)
        Espresso.onView(withText(R.string.link_save)).perform(click())
    }
}