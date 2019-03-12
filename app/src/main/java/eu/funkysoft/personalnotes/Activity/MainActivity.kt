package eu.funkysoft.personalnotes.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import eu.funkysoft.fitkomobile.adapters.NotesAdapter
import eu.funkysoft.personalnotes.Api.ApiClient
import eu.funkysoft.personalnotes.Api.Models.Note
import eu.funkysoft.personalnotes.Dialogs.AddDialog
import eu.funkysoft.personalnotes.Dialogs.ProgressDialog
import eu.funkysoft.personalnotes.Dialogs.SureDialog
import eu.funkysoft.personalnotes.R
import eu.funkysoft.personalnotes.Tools.Const
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity(), View.OnClickListener, NotesAdapter.OnItemClick {

    lateinit var recyclerViewNotes: RecyclerView
    lateinit var progressDialog: ProgressDialog
    lateinit var buttonAddNote: FloatingActionButton
    lateinit var notes: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddNote = findViewById(R.id.button_add_note)
        buttonAddNote.setOnClickListener(this)

        var linearLayoutManager = GridLayoutManager(this, 1)

        recyclerViewNotes = findViewById(R.id.recyclerview_notes)
        recyclerViewNotes.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?

        progressDialog = ProgressDialog(this)

        loadNotes()
    }

    fun loadNotes() {

        progressDialog.startProgress()
        ApiClient.noteApi.getNotes().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> loadNotesFinish(result) },
                { error -> ApiClient.showError(error, progressDialog) }
            )
    }

    fun loadNotesFinish(notes: ArrayList<Note>) {
        this.notes = notes
        progressDialog.finishOk(R.string.load_data_succes)
        recyclerViewNotes.adapter = NotesAdapter(notes, this)
    }

    fun deleteNote(id: Int) {
        progressDialog.startProgress()
        ApiClient.noteApi.deleteNoteById(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> deleteNoteFinish(id) },
                { error -> ApiClient.showError(error, progressDialog) }
            )
    }

    fun deleteNoteFinish(id: Int) {
        progressDialog.finishOk(R.string.remove_success)

        var count = 0
        notes.forEach {
            if (it.id == id)
                notes.removeAt(count)
            else
                count++
        }
        recyclerViewNotes.adapter = NotesAdapter(notes, this)
    }

    fun addNote(title: String) {
        var note = Note()
        note.title = title
        progressDialog.startProgress()
        ApiClient.noteApi.addNote(note).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> addNoteFinish(result) },
                { error -> ApiClient.showError(error, progressDialog) }
            )
    }

    fun addNoteFinish(note: Note) {
        progressDialog.finishOk(R.string.add_success)
        notes.add(note)
        recyclerViewNotes.adapter = NotesAdapter(notes, this)
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(Const.NOTE_EXTRA_KEY, note)
        startActivityForResult(intent,Const.REQUEST_CODE_DETAIL)
    }

    override fun onTrashClick(note: Note) {
        SureDialog(this, object : SureDialog.OnSureDialogClick {
            override fun onButtonClick(isAccept: Boolean) {
                if (isAccept)
                    deleteNote(note.id)
            }
        }).show()
    }

    override fun onClick(p0: View?) {
        if (p0 == buttonAddNote) {
            AddDialog(this, object : AddDialog.OnSureDialogClick {
                override fun onButtonClick(title: String) {
                    addNote(title)
                }
            },Const.EMPTY).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Const.REQUEST_CODE_DETAIL){
            val note = data!!.getSerializableExtra(Const.NOTE_EXTRA_KEY) as Note
            notes.forEach {
                if (it.id == note.id)
                    it.title = note.title
            }
            recyclerViewNotes.adapter = NotesAdapter(notes, this)
        }
    }
}
