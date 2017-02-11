package pe.devpicon.android.marvelcomic.activities.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.intentFor
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.activities.BaseActivity
import pe.devpicon.android.marvelcomic.activities.start.StartActivity
import pe.devpicon.android.marvelcomic.utils.signOut

class ProfileActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_out -> {
                signOut(this@ProfileActivity)
                finishAffinity()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setTitle("")

        val auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            val currentUser = auth.currentUser

            txt_profile_header_name.text = currentUser!!.displayName
            txt_profile_header_email.text = currentUser!!.email

            val photoUrl = currentUser!!.photoUrl

            Glide.with(this)
                    .load(photoUrl)
                    .fitCenter()
                    .crossFade()
                    .into(img_profile_picture)


            Glide.with(this).load(photoUrl)
                    .bitmapTransform(CropCircleTransformation(this))
                    .into(img_profile_picture_thumbnail)

            btn_sign_out.setOnClickListener(this)

        } else {
            goToStartActivity()
        }


    }

    private fun goToStartActivity() {
        startActivity(intentFor<StartActivity>())
        finish()
    }
}
