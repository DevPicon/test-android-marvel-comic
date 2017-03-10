package pe.devpicon.android.marvelcomic.ui.activities.list

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main_tab.*
import kotlinx.android.synthetic.main.include_navheader.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.ui.activities.BaseActivity
import pe.devpicon.android.marvelcomic.ui.activities.profile.ProfileActivity
import pe.devpicon.android.marvelcomic.ui.fragments.favouritelist.ComicFavouriteListFragment
import pe.devpicon.android.marvelcomic.ui.fragments.list.ComicListFragment
import pe.devpicon.android.marvelcomic.utils.signOut


/**
 * Created by Armando on 10/2/2017.
 */
class MainActivityTab : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val auth = FirebaseAuth.getInstance();
        val currentUser = auth.currentUser
        if (currentUser != null) {

            setContentView(R.layout.activity_main_tab)

            setupSupportActionBar()
            setupViewPager()
            setupTabLayout()

            val navigationView = findViewById(R.id.nav_view) as NavigationView
            navigationView.setNavigationItemSelectedListener { menuItem ->
                // This method will trigger on item Click of navigation menu
                // Set item in checked state
                menuItem.isChecked = true

                // TODO: Manejar la Navegacion

                when(menuItem.itemId){
                    R.id.item_home -> toast("Home")
                    R.id.item_profile -> startActivity(intentFor<ProfileActivity>())
                    R.id.item_signout -> {
//                    AuthUI.getInstance()
//                            .signOut(this)
//                            .addOnCompleteListener(object : OnCompleteListener<Void> {
//                                override fun onComplete(task: Task<Void>) {
//                                    // user is now signed out
//                                    startActivity(Intent(this@MainActivityTab, StartActivity::class.java))
//                                    finish()
//                                }
//                            })
                        signOut(this@MainActivityTab)
                        finishAffinity()
                    }
                }

                // Closing drawer on item click
                drawer.closeDrawers()
                true
            }

            val headerView = navigationView.getHeaderView(0)

            with(currentUser){

                val textViewName = headerView.find<TextView>(R.id.navheader_name)
                val textViewEmail = headerView.find<TextView>(R.id.navheader_email)
                val imgViewThumbail = headerView.find<ImageView>(R.id.navheader_thumbnail)
                textViewName.text = displayName
                textViewEmail.text = email
                Glide.with(this@MainActivityTab).load(photoUrl)
                        .bitmapTransform(CropCircleTransformation(this@MainActivityTab))
                        .into(imgViewThumbail)
            }

        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupTabLayout() {
        tabs.setupWithViewPager(viewpager)
    }

    private fun setupViewPager() {
        val adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ComicListFragment(),getString(R.string.tab_comic_list))
        adapter.addFragment(ComicFavouriteListFragment(),getString(R.string.tab_comic_favourite_list))
        viewpager.adapter = adapter
    }

    fun setupSupportActionBar() {
        setSupportActionBar(toolbar)
        var supportActionBar: ActionBar? = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.menu)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
}

