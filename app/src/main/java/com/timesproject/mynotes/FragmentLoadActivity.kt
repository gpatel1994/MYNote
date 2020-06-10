package com.timesproject.mynotes

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.timesproject.mynotes.databinding.ActivtiyFragmentLoadBinding
import com.timesproject.mynotes.listener.FragmentLoadActivityListener
import com.timesproject.mynotes.util.CommonConstants

class FragmentLoadActivity : FragmentActivity(), FragmentLoadActivityListener {

    internal var fragmentTransaction:FragmentTransaction?= null
    lateinit var binding: ActivtiyFragmentLoadBinding
    internal var noteId : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activtiy_fragment_load)
        initIntent()
        setFragmentManager()
        launchFragment()
        setToolbarTitle(CommonConstants.NEW_NOTE)
        setActionBar(binding.fragmentLoadToolbar)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun initIntent() {
        noteId = intent.extras?.getString(CommonConstants.KEY_NOTE_ID)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setFragmentManager() {
        fragmentTransaction = supportFragmentManager.beginTransaction()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun launchFragment() {
        if(noteId.isNullOrEmpty()) {
            fragmentTransaction?.replace(R.id.base_fragment, NewNoteFragment.newInstance())
        } else {
            fragmentTransaction?.replace(R.id.base_fragment, NewNoteFragment.newInstance(noteId))
        }
        fragmentTransaction?.commit()
    }

    override fun setToolbarTitle(toolbarTitle: String?) {
        toolbarTitle?.let { binding.fragmentLoadToolbar.title = it }
    }
}