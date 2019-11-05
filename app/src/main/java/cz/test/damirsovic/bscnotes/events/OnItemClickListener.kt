package cz.test.damirsovic.bscnotes.events

import cz.test.damirsovic.bscnotes.model.Note

interface OnItemClickListener {

    fun onClick(note: Note)

}