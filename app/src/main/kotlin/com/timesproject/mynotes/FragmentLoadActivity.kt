package com.timesproject.mynotes

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.timesproject.mynotes.databinding.ActivtiyFragmentLoadBinding

class FragmentLoadActivity : FragmentActivity() {

    private var fragmentTransaction:FragmentTransaction?= null
    lateinit var binding: ActivtiyFragmentLoadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activtiy_fragment_load)
        binding.fragmentLoadToolbar.title = "New Note"
        setActionBar(binding.fragmentLoadToolbar)
        setFragmentManager()
        launchFragment()
    }

    private fun setFragmentManager() {
        fragmentTransaction = supportFragmentManager.beginTransaction()
    }

    private fun launchFragment() {
        fragmentTransaction?.replace(R.id.base_fragment, NewNoteFragment.newInstance())?.commit()
    }
}