package pe.devpicon.android.marvelcomic.ui.fragments.favouritelist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_favourites.*
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.ui.activities.detail.ComicDetailActivity
import pe.devpicon.android.marvelcomic.ui.activities.favourite.FavouriteDetailActivity
import pe.devpicon.android.marvelcomic.ui.adapters.ComicAdapter
import pe.devpicon.android.marvelcomic.ui.adapters.FavouriteAdapter
import pe.devpicon.android.marvelcomic.data.DatabaseManager
import pe.devpicon.android.marvelcomic.entities.Comic

/**
 * Created by Armando on 10/2/2017.
 */
class ComicFavouriteListFragment: Fragment(){


    private var favouriteAdapter: FavouriteAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_favourites, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favouriteAdapter = FavouriteAdapter(null, object : FavouriteAdapter.OnItemClickListener {
            override fun invoke(comic: Comic) {
                var intent = Intent(activity, FavouriteDetailActivity::class.java)
                intent.putExtra("comicId", comic.id)
                intent.putExtra("comic", comic)
                startActivity(intent)
            }

        })

        if(favouriteAdapter != null){
            recyclerview_favourite.adapter = favouriteAdapter
        }
        recyclerview_favourite.layoutManager = LinearLayoutManager(activity)

        val queryAllComics = DatabaseManager.getInstance(activity).queryAllComics("")

        if(favouriteAdapter != null){
            favouriteAdapter!!.cursor = queryAllComics
            favouriteAdapter!!.notifyDataSetChanged()
        }
    }
}