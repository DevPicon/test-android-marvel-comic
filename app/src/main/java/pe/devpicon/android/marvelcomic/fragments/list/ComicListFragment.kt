package pe.devpicon.android.marvelcomic.fragments.list

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.activities.detail.ComicDetailActivity
import pe.devpicon.android.marvelcomic.adapters.ComicAdapter
import pe.devpicon.android.marvelcomic.entities.Comic
import pe.devpicon.android.marvelcomic.fragments.BaseFragment
import pe.devpicon.android.marvelcomic.interfaces.Operations
import pe.devpicon.android.marvelcomic.taks.ComicSearchTask

import pe.devpicon.android.marvelcomic.taks.ComicListTask

/**
 * Created by Armando on 10/2/2017.
 */
class ComicListFragment: BaseFragment(), Operations.RequiredOps, View.OnClickListener {

    private var comicAdapter : ComicAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comicAdapter = ComicAdapter(null, object : ComicAdapter.OnItemClickListener {
            override fun invoke(comic: Comic) {
                var intent = Intent(activity, ComicDetailActivity::class.java)
                intent.putExtra("comicId", comic.id)
                startActivity(intent)
            }

        })

        if(comicAdapter != null){
            comic_recyclerview.adapter = comicAdapter
        }
        comic_recyclerview.layoutManager = LinearLayoutManager(activity)

        checkIfDataIsAvailable()


        val task = ComicListTask()
        task.context = activity
        task.view = this
        task.execute("")

        btn_search.setOnClickListener(this)


    }

    private fun checkIfDataIsAvailable() {
        if (comicAdapter != null && comicAdapter!!.itemCount > 0) {
            comic_recyclerview.visibility = View.VISIBLE
            txt_empty.visibility = View.GONE
        } else {
            comic_recyclerview.visibility = View.GONE
            txt_empty.visibility = View.VISIBLE
        }
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_search -> {
                val comicSearchTask = ComicSearchTask()
                comicSearchTask.context = activity
                comicSearchTask.view = this
                comicSearchTask.execute(edt_search.text.toString())
            }
        }
    }

    override fun showComics(comicList: List<Comic>) {
        Log.d(javaClass.simpleName, "showComics")
        comicAdapter?.items = comicList
        comicAdapter?.notifyDataSetChanged()

        checkIfDataIsAvailable()
    }
}