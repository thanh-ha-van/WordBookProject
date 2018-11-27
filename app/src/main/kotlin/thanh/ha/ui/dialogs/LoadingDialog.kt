package thanh.ha.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.view.Window
import thanh.ha.R


class LoadingDialog(private var activity: Activity) {

    private lateinit var dialog: Dialog

    fun showDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.view_loading_dialog)
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }
}