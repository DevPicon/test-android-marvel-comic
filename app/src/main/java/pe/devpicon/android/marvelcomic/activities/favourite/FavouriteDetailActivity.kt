package pe.devpicon.android.marvelcomic.activities.favourite

import android.app.ProgressDialog
import android.database.Cursor
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_comic_detail.*
import org.jetbrains.anko.activityUiThreadWithContext
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.activities.BaseActivity
import pe.devpicon.android.marvelcomic.data.ComicContract
import pe.devpicon.android.marvelcomic.data.DatabaseManager
import pe.devpicon.android.marvelcomic.entities.Characters
import pe.devpicon.android.marvelcomic.entities.Comic
import pe.devpicon.android.marvelcomic.entities.Creator
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat

class FavouriteDetailActivity : BaseActivity() {

    val TAG: String = javaClass.simpleName
    private var comic: Comic? = null

    private fun getComicFromCursor(cursor: Cursor): Comic {

        var comic = Comic(
                id = cursor.getInt(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_COMIC_ID)),
                title = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_TITLE)),
                description = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_DESCRIPTION)),
                price = cursor.getDouble(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_PRICE)),
                imageURL = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_IMAGE_URL)),
                onSaleDate = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_DATE)),
                pageCount = cursor.getInt(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_PAGE_COUNT)),
                series = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_SERIES)),
                thumbnailUrl = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_IMAGE_URL))

        )
        return comic
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_detail)

        val comicId = intent.getIntExtra("comicId", 0)

        Log.d(TAG, comic.toString())

        val cursor = DatabaseManager.getInstance(this).queryComicById(comicId)
        if (cursor != null && cursor!!.count > 0) {
            showProgressDialog()

            cursor.moveToFirst()

            val comic = getComicFromCursor(cursor)

            if (comic != null) {

                Log.d(javaClass.simpleName, comic.toString())

                with(comic) {
                    Glide.with(this@FavouriteDetailActivity)
                            .load(imageURL)
                            .fitCenter()
                            .into(img_comic_detail_cover)
                    txt_comic_detail_name.text = title
                    txt_comic_detail_price.text = if (price > 0.0) getString(R.string.format_usd, price.toString()) else getString(R.string.message_soldout)
                    txt_comic_detail_description.text = if (!description.equals("null", true)) description else getString(R.string.message_not_available)
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    val parse = simpleDateFormat.parse(onSaleDate)
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy")
                    txt_comic_detail_onsale_date.text = outputFormat.format(parse).toString()
                    txt_comic_detail_pagecount.text = pageCount.toString()
                    txt_comic_detail_series.text = series
                    txt_comic_detail_creators.text = ""
                    txt_comic_detail_characters.text = ""

                }


            }

            dismissProgressDialog()

        }

    }
}
