package thanh.ha.base

import android.os.Bundle
import android.support.v4.app.Fragment
import thanh.ha.ui.dialogs.LoadingDialog


abstract class BaseFragment : Fragment() {

    private lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = LoadingDialog(activity!!)
    }

    protected fun showLoadingDialog() {
        dialog.showDialog()
    }

    protected fun hideLoadingDialog() {
        dialog.hideDialog()
    }

}