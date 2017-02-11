package pe.devpicon.android.marvelcomic.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import pe.devpicon.android.marvelcomic.activities.BaseActivity
import pe.devpicon.android.marvelcomic.activities.start.StartActivity

/**
 * Created by Armando on 11/2/2017.
 */

fun signOut(context: BaseActivity){
    AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener(object : OnCompleteListener<Void> {
                override fun onComplete(task: Task<Void>) {
                    // user is now signed out
                    val intent = Intent(context, StartActivity::class.java)
                    ContextCompat.startActivity(context, intent, null)

                }
            })

}