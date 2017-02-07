package pe.devpicon.android.marvelcomic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.comic_recyclerview;

class MainActivity : AppCompatActivity() {

    private val comics = listOf(Comic("comic 1", 3.95, null), Comic("comic 2", 0.0, null))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(javaClass.simpleName, comics.size.toString())

        val comicAdapter = ComicAdapter(comics)
        comic_recyclerview.adapter = comicAdapter
        comic_recyclerview.layoutManager = LinearLayoutManager(this)

    }
}
