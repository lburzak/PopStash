package com.github.polydome.popstash.app.feature.stash

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.presentation.viewmodel.SaveFromClipboardViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.SaveFromClipboardViewModel.Command
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test

class SaveFromClipboardFragmentTest {
    private lateinit var scenario: FragmentScenario<SaveFromClipboardFragment>
    private lateinit var shouldDisplayDialog: MutableLiveData<Boolean>
    private val viewModel: SaveFromClipboardViewModel = mockk(relaxUnitFun = true) {
        every { urlInClipboard } answers { MutableLiveData("http://google.com") }
        every { shouldDisplayDialog } answers { this@SaveFromClipboardFragmentTest.shouldDisplayDialog }
    }

    @Before
    fun setUp() {
        shouldDisplayDialog = MutableLiveData()

        scenario = launchFragmentInContainer(
                themeResId = R.style.AppTheme
        ) { SaveFromClipboardFragment(viewModel) }
    }

    @Test
    fun shouldNotDisplayDialog_onResumed_doesNotShowDialog() {
        shouldDisplayDialog.postValue(false)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onDialogView().check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldDisplayDialog_onResumed_displaysDialog() {
        shouldDisplayDialog.postValue(true)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onDialogView().check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayDialog_onSaveClick_callsSaveCommand() {
        shouldDisplayDialog.postValue(true)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onSaveButton().perform(click())

        verify(exactly = 1) { viewModel.command(Command.SAVE) }
    }

    @Test
    fun shouldDisplayDialog_onSwipeRight_dismissesDialog() {
        shouldDisplayDialog.postValue(true)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onDialogView().perform(swipeRight())
        onDialogView().check(matches(not(isCompletelyDisplayed())))
    }

    private fun onDialogView(): ViewInteraction =
            onView(withId(R.id.save_from_clipboard_content))

    private fun onSaveButton(): ViewInteraction =
            onView(withText(R.string.link_save))
}