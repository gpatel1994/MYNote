package com.timesproject.mynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.timesproject.mynotes.database.TextNoteDBHelper
import com.timesproject.mynotes.databinding.FragmentNoteListBinding
import com.timesproject.mynotes.model.TextNote
import com.timesproject.mynotes.util.NoteUtil

class NewNoteFragment : Fragment() {
    companion object Factory {
        fun newInstance(): NewNoteFragment {
            return NewNoteFragment()
        }

        fun newInstance(noteId: String?) : NewNoteFragment {
            val fragment = NewNoteFragment()
            val bundle = Bundle()
            bundle.putString("noteId", noteId)
            fragment.arguments = bundle
            return fragment
        }
    }
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var noteDBHelper: TextNoteDBHelper
    private var noteId:String? = null
    private var noteText:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)
        activity?.let { noteDBHelper = TextNoteDBHelper(it.applicationContext) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFromBundle()
        setNoteText()
        binding.saveNoteButton.setOnClickListener { onClickSaveButton() }
    }

    private fun setNoteText() {
        noteText = noteDBHelper.readTextNote(noteId)?.get(0)?.noteText
        if(!noteText.isNullOrEmpty()) {
            binding.inputText.setText(noteText)
        }
    }

    private fun initFromBundle() {
        arguments?.let {
            noteId = it.getString("noteId", null)
        }
    }

    private fun onClickSaveButton() {
        val noteId = NoteUtil.generateNoteId()
        val noteText = binding.inputText.editableText.toString()

        if(!noteId.isNullOrEmpty() && noteText.isNotEmpty()) {
            val result = noteDBHelper.saveNoteInDB(TextNote(noteId, noteText))
            if(result) {
                Toast.makeText(context, "Note Saved", Toast.LENGTH_LONG).show()
                activity?.supportFragmentManager?.popBackStack()
            } else {
                Toast.makeText(context, "Error in Saving Note", Toast.LENGTH_LONG).show()
            }
        }

    }
}