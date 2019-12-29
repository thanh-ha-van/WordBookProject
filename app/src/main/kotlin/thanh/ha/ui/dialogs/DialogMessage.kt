package thanh.ha.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import thanh.ha.R

class DialogMessage(context: Context?) {

    private val alertDialog: AlertDialog
    private val tvMessage: TextView
    private var onClickButton: OnClickButton? = null

    fun setOnClickButton(onClickButton: OnClickButton?) {
        this.onClickButton = onClickButton
    }

    fun setMessage(message: String?) {
        tvMessage.text = message
    }

    fun showDialog() {
        alertDialog.show()
    }

    interface OnClickButton {
        fun onClickNegative()
        fun onClickPositive()
    }

    init {
        val inflater = LayoutInflater.from(context)
        val builder = AlertDialog.Builder(context)
        val view = inflater.inflate(R.layout.view_yes_no_dialog, null)
        builder.setView(view)
        alertDialog = builder.create()
        tvMessage = view.findViewById(R.id.tv_content)
        val tvActionNegative = view.findViewById<TextView>(R.id.btn_cancel_dialog)
        val tvActionPositive = view.findViewById<TextView>(R.id.btn_ok_dialog)
        tvActionNegative.setOnClickListener {
            onClickButton!!.onClickNegative()
            alertDialog.dismiss()
        }
        tvActionPositive.setOnClickListener {
            onClickButton!!.onClickPositive()
            alertDialog.dismiss()
        }
    }
}