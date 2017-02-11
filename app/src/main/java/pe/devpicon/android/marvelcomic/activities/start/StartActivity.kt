package pe.devpicon.android.marvelcomic.activities.start

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.ResultCodes
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.activities.BaseActivity
import pe.devpicon.android.marvelcomic.activities.list.MainActivity
import pe.devpicon.android.marvelcomic.activities.list.MainActivityTab

/**
 * Created by Armando on 9/2/2017.
 */
class StartActivity : BaseActivity() {

    private var TAG: String = javaClass.simpleName
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signin)

        Log.d(TAG, "onCreate")
        val auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            goToMainActivity()
        } else{
            goToLogin()
        }

    }

    private fun goToLogin() {
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setProviders(arrayListOf(
                        AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                        AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                .setIsSmartLockEnabled(false)
                .build(), RC_SIGN_IN)
    }


    /*override fun onStart() {
        super.onStart()

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(intentFor<MainActivity>())
            finish()
        } else {
            Log.d(TAG, "User is sign out")
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            handleSignInResult(resultCode, response)



        }
    }


    // TODO Terminar de implementar el hangleSignInResult
    private fun handleSignInResult(resultCode: Int, response: IdpResponse?) {
        Log.d(TAG, resultCode.toString())


        if (resultCode == ResultCodes.OK) {
            goToMainActivity()
            finish()
        } else {
            if (response == null) {
                toast(getString(R.string.sign_in_cancelled))

            }
            if (response!!.errorCode == ErrorCodes.NO_NETWORK) {
                toast(getString(R.string.no_intent_connection))

            }
            if (response!!.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                toast(getString(R.string.unknown_error))

            }

            goToLogin()
        }


    }

    private fun goToMainActivity() {
        startActivity(intentFor<MainActivityTab>())
    }

    private fun saveUserInfoInSharedPreferences(account: GoogleSignInAccount?) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("userAccount", 0)
        val edit = sharedPreferences.edit()
        edit.putString("displayName", account?.displayName)
        edit.putString("email", account?.email)
    }


}