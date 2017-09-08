package fm.weigl.refsberlin.main.presenter

import android.support.v4.app.Fragment
import fm.weigl.refsberlin.R
import fm.weigl.refsberlin.abouttheapp.view.AboutTheAppFragment
import fm.weigl.refsberlin.gameslist.view.GamesListFragment
import javax.inject.Inject

class FragmentMenuMapper @Inject constructor() {

    fun menuIdForFragment(fragment: Fragment) = when (fragment) {
        is GamesListFragment   -> R.id.nav_gameslist
        is AboutTheAppFragment -> R.id.nav_about_the_app
        else                   -> -1
    }
}