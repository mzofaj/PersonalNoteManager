package eu.funkysoft.personalnotes.Dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import eu.funkysoft.personalnotes.R
import eu.funkysoft.personalnotes.Tools.Const

class AddDialog(context: Context,private val listener: OnSureDialogClick,private val text:String) : BaseDialog(context), View.OnClickListener {

    lateinit var buttonSave: Button
    lateinit var inputText: EditText

    override fun onClick(p0: View?) {
        if (p0 == buttonSave) {
            var text = inputText.text.toString()
            if (text != Const.EMPTY) {
                listener.onButtonClick(inputText.text.toString())
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add)

        inputText = findViewById(R.id.editext_add_dialog_save)
        inputText.setText(text)

        buttonSave = findViewById(R.id.button_add_dialog_save)
        buttonSave.setOnClickListener(this)


    }

    interface OnSureDialogClick {
        fun onButtonClick(title: String)
    }
}