package eu.funkysoft.personalnotes.Dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import eu.funkysoft.personalnotes.R


class ProgressDialog(context: Context?) : BaseDialog(context!!),View.OnClickListener {

    private lateinit var imageStatus: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var textProgress: TextView
    private lateinit var buttonOk: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)

        imageStatus = findViewById(R.id.image_progress_dialog)
        progressBar = findViewById(R.id.progressbar_progress_dialog)
        textProgress = findViewById(R.id.text_progress_dialog)
        buttonOk = findViewById(R.id.button_progress_dialog)
        buttonOk.setOnClickListener(this)

    }

    fun startProgress() {
        show()
        imageStatus.visibility = View.GONE
        buttonOk.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        textProgress.visibility = View.VISIBLE
        textProgress.setText(R.string.please_wait)
    }

    fun finishOk(messageRes:Int) {
        imageStatus.visibility = View.VISIBLE
        imageStatus.setImageResource(R.drawable.icon_ok)
        buttonOk.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        textProgress.visibility = View.VISIBLE
        textProgress.setText(messageRes)
    }

    fun finishFail(message:String) {
        imageStatus.visibility = View.VISIBLE
        imageStatus.setImageResource(R.drawable.icon_fail)
        buttonOk.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        textProgress.visibility = View.VISIBLE
        textProgress.text = message
    }

    override fun onClick(v: View?) {
        if(v == buttonOk){
            dismiss()
        }
    }
}