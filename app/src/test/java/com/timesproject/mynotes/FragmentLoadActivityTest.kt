package com.timesproject.mynotes

import android.content.Intent
import android.os.Bundle
import com.timesproject.mynotes.databinding.ActivtiyFragmentLoadBinding
import com.timesproject.mynotes.util.CommonConstants
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class FragmentLoadActivityTest : BaseMockkUnitTest() {

    private lateinit var fragmentLoadActivity: FragmentLoadActivity
    @Before
    fun setUp() {
        // spykk run the actual code while running the test on the Given class
        fragmentLoadActivity = spyk(FragmentLoadActivity())
    }

    @Test
    fun testInitIntent() {
        val mockIntent = mockk<Intent>(relaxed = true)
        val mockExtras = mockk<Bundle>(relaxed = true)
        every { fragmentLoadActivity.intent } returns mockIntent
        every { mockIntent.extras } returns mockExtras
        every { mockExtras.getString(CommonConstants.KEY_NOTE_ID) } returns "12345678"
        fragmentLoadActivity.initIntent()
        assertEquals("12345678", fragmentLoadActivity.noteId)
    }

    @Test
    fun testSetFragmentManager() {
        fragmentLoadActivity.setFragmentManager()
        assertNotNull(fragmentLoadActivity.fragmentTransaction)
    }
}