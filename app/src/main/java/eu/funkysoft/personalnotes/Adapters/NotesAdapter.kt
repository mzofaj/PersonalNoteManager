package eu.funkysoft.fitkomobile.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import eu.funkysoft.personalnotes.Activity.MainActivity
import eu.funkysoft.personalnotes.Api.Models.Note
import eu.funkysoft.personalnotes.R


class NotesAdapter(private val notes: ArrayList<Note>, private val activity: MainActivity) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(activity).inflate(R.layout.item_note, parent, false)
        val textTitle = rootView.findViewById<TextView>(R.id.text_item_note_title)
        val textContent = rootView.findViewById<TextView>(R.id.text_item_note_content)
        val imageTrash = rootView.findViewById<ImageView>(R.id.image_item_note_trash)
        return ViewHolder(rootView, textTitle, textContent,imageTrash)
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val note = notes[p1]

        holder.textTitle.text = note.title
        holder.textContent.text = note.title
        holder.rootView.setOnClickListener {
            activity.onNoteClick(note)
        }

        holder.imageTrash.setOnClickListener{
            activity.onTrashClick(note)
        }
    }


    override fun getItemCount(): Int {
        return notes.size
    }


    class ViewHolder(
        val rootView: View,
        val textTitle: TextView,
        val textContent: TextView,
        val imageTrash:ImageView
    ) : RecyclerView.ViewHolder(rootView)

    public interface OnItemClick {
        fun onNoteClick(note: Note)
        fun onTrashClick(note: Note)
    }
}