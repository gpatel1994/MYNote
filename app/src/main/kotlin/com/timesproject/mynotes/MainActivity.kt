package com.timesproject.mynotes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.timesproject.mynotes.databinding.ActivityStartupBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding()
        setSupportActionBar(binding.toolbar)
    }

    private fun setViewBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_startup)
        binding.toolbar.title = "My Notes"
        binding.fab.setOnClickListener { loadNewNoteFragment() }
    }

    private fun loadNewNoteFragment() {
        startActivity(Intent(this, FragmentLoadActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}