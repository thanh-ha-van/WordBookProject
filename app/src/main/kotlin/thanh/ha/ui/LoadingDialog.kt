package thanh.ha.ui

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import thanh.ha.R


class LoadingDialog(private var activity: Activity) {

    private lateinit var dialog: Dialog

    fun showDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_loading_dialog)

        val gifImageView = dialog.findViewById<ImageView>(R.id.custom_loading_imageView)

        val imageViewTarget = GlideDrawableImageViewTarget(gifImageView)
        Glide.with(activity)
                .load(R.drawable.loading_1)
                .placeholder(R.drawable.loading_1)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget)
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }

}