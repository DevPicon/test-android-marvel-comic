package pe.devpicon.android.marvelcomic.activities.start

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.intentFor
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.activities.BaseActivity
import pe.devpicon.android.marvelcomic.activities.list.MainActivity
import pe.devpicon.android.marvelcomic.taks.ComicListTask

/**
 * Created by Armando on 9/2/2017.
 */
class StartActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private var googleApiClient: GoogleApiClient? = null
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        sign_in_button.setOnClickListener(this)

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        Log.d(javaClass.simpleName, "onCreate ${googleApiClient.toString()}")

    }


    override fun onStart() {
        super.onStart()

        val opr: OptionalPendingResult<GoogleSignInResult> = Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result: GoogleSignInResult = opr.get()
            handleSignInResult(result)
        } else {
            showProgressDialog()
            opr.setResultCallback({ googleSignInResult: GoogleSignInResult ->
                kotlin.run {
                    dismissProgressDialog()
                    handleSignInResult(googleSignInResult)
                }
            })
        }
    }


    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(javaClass.simpleName, "onConnectionFailed:$connectionResult");

    }

    override fun onClick(v: View) {
        Log.d(javaClass.simpleName, v.id.toString())
        when (v.id) {
            R.id.sign_in_button -> signIn()
        }
    }

    private fun signIn() {
        Log.d(javaClass.simpleName, "signinMethod")
        var signinIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signinIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            Log.d(javaClass.simpleName, "onActivityResult ${data.toString()}")

            var result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            handleSignInResult(result)
        }
    }


    // TODO Terminar de implementar el hangleSignInResult
    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.d(javaClass.simpleName, result.isSuccess.toString())

//        if (result.isSuccess()) {
//            val account: GoogleSignInAccount? = result.signInAccount
//            saveUserInfoInSharedPreferences(account)



            var intent = intentFor<MainActivity>()
            startActivity(intent)


//        } else {

//            Log.d(javaClass.simpleName, "El login no fue satisfactorio")
//        }

    }

    private fun saveUserInfoInSharedPreferences(account: GoogleSignInAccount?) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("userAccount", 0)
        val edit = sharedPreferences.edit()
        edit.putString("displayName", account?.displayName)
        edit.putString("email", account?.email)
    }


}