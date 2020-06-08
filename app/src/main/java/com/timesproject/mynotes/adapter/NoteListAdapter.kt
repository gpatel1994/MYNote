package com.timesproject.mynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.timesproject.mynotes.R
import com.timesproject.mynotes.databinding.AdapterNoteListBinding
import com.timesproject.mynotes.listener.MainActivityListener
import com.timesproject.mynotes.model.TextNote
import com.timesproject.mynotes.util.listSize

class NoteListAdapter(
    private var noteList: List<TextNote>?,
    internal var listener: MainActivityListener?
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_note_list, parent, false
            )
        )
    }

    override fun getItemCount(): Int = noteList.listSize()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        noteList?.get(position)?.let {
            holder.setNoteId(it)
        }
    }

    fun setNoteIdList(noteIdList: List<TextNote>) {
        noteList = noteIdList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: AdapterNoteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var note: TextNote? = null

        init {
            binding.noteIdTextView.setOnClickListener { loadSelectedNoteFromNoteId() }
        }

        private fun loadSelectedNoteFromNoteId() {
            note?.let { listener?.onClickOfNoteId(it.noteId) }
        }

        fun setNoteId(note: TextNote) {
            this.note = note
            setNoteTitleToTextView()

        }

        private fun setNoteTitleToTextView() {
            note?.let {
                binding.noteIdTextView.text = it.noteTitle
            }
        }
    }
}