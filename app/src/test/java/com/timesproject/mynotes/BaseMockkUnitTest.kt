package com.timesproject.mynotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.After

abstract class BaseMockkUnitTest {
    /**
     * creates mock(dummy) object of <GivenClass>
     * relaxed keyword creates without any expected behaviour
     */
    internal val mockLayoutInflater = mockk<LayoutInflater>(relaxed = true)
    internal val mockContext = mockk<Context>(relaxed = true)
    internal val mockParent = mockk<ViewGroup>(relaxed = true)
    internal val mockInflatedView = mockk<View>(relaxed = true)

    internal fun <T : ViewDataBinding> setupCreateViewHolderTestForAdapter(
        @LayoutRes layoutRes: Int,
        binding: T) {
        /**
         * mock the given class in side the braces.
         */
        mockkStatic(LayoutInflater::class, DataBindingUtil::class)
        every { LayoutInflater.from(mockContext) } returns mockLayoutInflater
        every { mockParent.context } returns mockContext
        every {
            DataBindingUtil.inflate<T>(
                mockLayoutInflater,
                layoutRes,
                mockParent,
                false
            )
        } returns binding
        every { binding.root } returns mockInflatedView
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}