package pe.devpicon.android.marvelcomic.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import pe.devpicon.android.marvelcomic.Comic
import pe.devpicon.android.marvelcomic.ComicAdapter
import pe.devpicon.android.marvelcomic.ComicTask
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.interfaces.Operations
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), Operations.RequiredOps {
    override fun showComics(comicList: List<Comic>) {
        Log.d(javaClass.simpleName, "showComics")
        comicAdapter?.items = comicList
        comicAdapter?.notifyDataSetChanged()
    }

    private val comics = listOf(Comic("comic 1", 3.95, null), Comic("comic 2", 0.0, null))
    private var comicAdapter : ComicAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(javaClass.simpleName, comics.size.toString())

        comicAdapter = ComicAdapter(comics)
        comic_recyclerview.adapter = comicAdapter
        comic_recyclerview.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        Log.d(javaClass.simpleName, "onResume")
        super.onResume()

        val task = ComicTask()
        task.ops = this
        task.execute("")
    }




}

interface MainAcitivityOps {

}
