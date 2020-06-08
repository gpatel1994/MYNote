package com.timesproject.mynotes

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.timesproject.mynotes.databinding.ActivtiyFragmentLoadBinding
import com.timesproject.mynotes.listener.FragmentLoadActivityListener

class FragmentLoadActivity : FragmentActivity(), FragmentLoadActivityListener {

    private var fragmentTransaction:FragmentTransaction?= null
    lateinit var binding: ActivtiyFragmentLoadBinding
    private var noteId : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activtiy_fragment_load)
        initIntent()
        setFragmentManager()
        launchFragment()
        setToolbarTitle(null)
        setActionBar(binding.fragmentLoadToolbar)
    }

    private fun initIntent() {
        noteId = intent.extras?.getString("noteId")
    }

    private fun setFragmentManager() {
        fragmentTransaction = supportFragmentManager.beginTransaction()
    }

    private fun launchFragment() {
        if(noteId.isNullOrEmpty()) {
            fragmentTransaction?.replace(R.id.base_fragment, NewNoteFragment.newInstance())
        } else {
            fragmentTransaction?.replace(R.id.base_fragment, NewNoteFragment.newInstance(noteId))
        }
        fragmentTransaction?.commit()
    }

    override fun setToolbarTitle(toolbarTitle: String?) {
        val title = if (!toolbarTitle.isNullOrEmpty()) toolbarTitle else "New Note"
        binding.fragmentLoadToolbar.title = title
    }
}