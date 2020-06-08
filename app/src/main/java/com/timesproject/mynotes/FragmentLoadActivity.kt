package com.timesproject.mynotes

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.timesproject.mynotes.databinding.ActivtiyFragmentLoadBinding

class FragmentLoadActivity : FragmentActivity() {

    private var fragmentTransaction:FragmentTransaction?= null
    lateinit var binding: ActivtiyFragmentLoadBinding
    private var noteId : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activtiy_fragment_load)
        binding.fragmentLoadToolbar.title = "New Note"
        setActionBar(binding.fragmentLoadToolbar)
        initIntent()
        setFragmentManager()
        launchFragment()
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
}