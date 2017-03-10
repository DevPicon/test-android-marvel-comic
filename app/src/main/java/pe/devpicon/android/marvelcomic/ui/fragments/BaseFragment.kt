package pe.devpicon.android.marvelcomic.ui.fragments

import android.app.ProgressDialog
import android.support.v4.app.Fragment
import pe.devpicon.android.marvelcomic.interfaces.BaseView

/**
 * Created by Armando on 10/2/2017.
 */
open class BaseFragment: Fragment(), BaseView{
    var progressDialog: ProgressDialog? = null

    override fun showProgressDialog() {
        progressDialog = ProgressDialog.show(activity, "Loading...", "Please wait a minute...", true)
    }

    override fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}