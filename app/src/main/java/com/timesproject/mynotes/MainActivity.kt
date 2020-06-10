package com.timesproject.mynotes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.timesproject.mynotes.adapter.NoteListAdapter
import com.timesproject.mynotes.database.TextNoteDBHelper
import com.timesproject.mynotes.databinding.ActivityStartupBinding
import com.timesproject.mynotes.listener.MainActivityListener
import com.timesproject.mynotes.model.TextNote
import com.timesproject.mynotes.util.CommonConstants

class MainActivity : AppCompatActivity(), MainActivityListener {

    private lateinit var binding: ActivityStartupBinding
    private lateinit var noteDBHelper: TextNoteDBHelper
    private var noteIdListAdapter: NoteListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding()
        noteDBHelper = TextNoteDBHelper(this)
        loadNotes()
        setSupportActionBar(binding.toolbar)
    }

    private fun loadNotes() {
        val noteIdList = getNoteIdList()
        if(!noteIdList.isNullOrEmpty()) {
            noteIdListAdapter = NoteListAdapter(noteIdList, this)
            binding.noteListRecyclerView.visibility = View.VISIBLE
            binding.noteListRecyclerView.adapter = noteIdListAdapter
        } else {
            binding.noteListRecyclerView.visibility = View.GONE
        }
    }

    private fun setViewBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_startup)
        binding.toolbar.title = CommonConstants.MY_NOTES
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

    override fun onClickOfNoteId(noteId: String?) {
        val intent = Intent(this, FragmentLoadActivity::class.java)
        intent.putExtra(CommonConstants.KEY_NOTE_ID, noteId)
        startActivity(intent)
        //will remove toast afterwards
        Toast.makeText(this, noteId + "Loaded" , Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        val noteIdList = getNoteIdList()
        if(!noteIdList.isNullOrEmpty()) { noteIdListAdapter?.setNoteIdList(noteIdList) }
    }

    private fun getNoteIdList() : List<TextNote>? {
        return noteDBHelper.readAllNotes()
    }
}