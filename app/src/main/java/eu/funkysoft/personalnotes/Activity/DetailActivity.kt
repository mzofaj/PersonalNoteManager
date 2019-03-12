package eu.funkysoft.personalnotes.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.TextView
import eu.funkysoft.personalnotes.Api.ApiClient
import eu.funkysoft.personalnotes.Api.Models.Note
import eu.funkysoft.personalnotes.Dialogs.AddDialog
import eu.funkysoft.personalnotes.Dialogs.ProgressDialog
import eu.funkysoft.personalnotes.R
import eu.funkysoft.personalnotes.Tools.Const
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.content.Intent
import android.app.Activity


class DetailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var progressDialog: ProgressDialog
    lateinit var note: Note
    lateinit var buttonEditNote: FloatingActionButton
    lateinit var textTittle: TextView
    lateinit var textContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        buttonEditNote = findViewById(R.id.button_edit_note)
        buttonEditNote.setOnClickListener(this)

        textContent = findViewById(R.id.text_detail_content)
        textTittle = findViewById(R.id.text_detail_tittle)

        progressDialog = ProgressDialog(this)

        note = intent.getSerializableExtra(Const.NOTE_EXTRA_KEY) as Note
        loadNote()
    }

    fun loadNote() {

        progressDialog.startProgress()
        ApiClient.noteApi.getNoteById(note.id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> loadNotesFinish(result) },
                { error -> ApiClient.showError(error, progressDialog) }
            )
    }

    fun loadNotesFinish(note: Note) {
        this.note.title = note.title
        progressDialog.finishOk(R.string.load_data_succes)
        textTittle.text = note.title
        textContent.text = note.title
    }

    fun editNote(title: String) {
        progressDialog.startProgress()

        var noteTemp = note
        noteTemp.title = title
        ApiClient.noteApi.editNoteById(note.id, noteTemp).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> loadNotesFinish(result) },
                { error -> ApiClient.showError(error, progressDialog) }
            )
    }


    override fun onClick(p0: View?) {
        if (p0 == buttonEditNote) {
            AddDialog(this, object : AddDialog.OnSureDialogClick {
                override fun onButtonClick(title: String) {
                    editNote(title)
                }
            }, note.title).show()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(Const.NOTE_EXTRA_KEY, note)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
