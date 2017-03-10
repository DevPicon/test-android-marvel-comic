package pe.devpicon.android.marvelcomic.ui.activities

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import pe.devpicon.android.marvelcomic.interfaces.BaseView

/**
 * Created by Armando on 9/2/2017.
 */
open class BaseActivity:AppCompatActivity(), BaseView{

    var progressDialog: ProgressDialog? = null

    override fun showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "Loading...", "Please wait a minute...", true)
    }

    override fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}