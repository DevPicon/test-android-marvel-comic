package pe.devpicon.android.marvelcomic.activities.list

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import pe.devpicon.android.marvelcomic.entities.Comic
import pe.devpicon.android.marvelcomic.taks.ComicListTask
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.activities.BaseActivity
import pe.devpicon.android.marvelcomic.activities.detail.ComicDetailActivity
import pe.devpicon.android.marvelcomic.interfaces.Operations
import pe.devpicon.android.marvelcomic.taks.ComicSearchTask
import java.io.Serializable

class MainActivity : BaseActivity(), Operations.RequiredOps, View.OnClickListener {
    override fun showComics(comicList: List<Comic>) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(p0: View?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
/*
    override fun onClick(v: View?) {

        when(v?.id){
            R.id.btn_search -> {
                val comicSearchTask = ComicSearchTask()
                comicSearchTask.context = this
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

    //private val comics = listOf(Comic(1, "comic 1", 3.95, null), Comic(2,"comic 2", 0.0, null))
    private var comicAdapter : ComicAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        */
/*Log.d(javaClass.simpleName, comics.size.toString())*//*


        comicAdapter = ComicAdapter(null, object : ComicAdapter.OnItemClickListener {
            override fun invoke(comic: Comic) {
                toast("${comic.id} ${comic.title}")
                var intent = intentFor<ComicDetailActivity>()
                intent.putExtra("comicId", comic.id)
                startActivity(intent)
            }

        })
        comic_recyclerview.adapter = comicAdapter
        comic_recyclerview.layoutManager = LinearLayoutManager(this)

        checkIfDataIsAvailable()


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

    override fun onResume() {
        Log.d(javaClass.simpleName, "onResume")
        super.onResume()

        val task = ComicListTask()
        task.context = this
        task.view = this
        task.execute("")


    }



*/

}



