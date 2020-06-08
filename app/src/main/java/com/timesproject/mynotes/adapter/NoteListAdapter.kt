package com.timesproject.mynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.timesproject.mynotes.R
import com.timesproject.mynotes.databinding.AdapterNoteListBinding
import com.timesproject.mynotes.listener.MainActivityListener
import com.timesproject.mynotes.util.listSize

class NoteListAdapter(
    private var noteListId: List<String?>?,
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

    override fun getItemCount(): Int = noteListId.listSize()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        noteListId?.get(position)?.let {
            holder.setNoteId(it)
        }
    }

    fun setNoteIdList(noteIdList: List<String?>) {
        noteListId = noteIdList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: AdapterNoteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var noteId: String? = null

        init {
            binding.noteIdTextView.setOnClickListener { loadSelectedNoteFromNoteId() }
        }

        private fun loadSelectedNoteFromNoteId() {
            noteId?.let { listener?.onClickOfNoteId(it) }
        }

        fun setNoteId(noteId: String) {
            this.noteId = noteId
            binding.noteIdTextView.text = noteId
        }
    }
}