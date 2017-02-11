package pe.devpicon.android.marvelcomic.activities.list

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Armando on 10/2/2017.
 */
class ViewPagerAdapter(supportFragmentManager: FragmentManager?) : FragmentPagerAdapter(supportFragmentManager) {

    var fragmentList = mutableListOf<Fragment>()
    var fragmentTitleList = mutableListOf<String>()

    override fun getItem(position: Int): Fragment = fragmentList.get(position)

    override fun getCount(): Int = fragmentList.size

    fun addFragment(fragment: Fragment, title:String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList.get(position)
    }

}