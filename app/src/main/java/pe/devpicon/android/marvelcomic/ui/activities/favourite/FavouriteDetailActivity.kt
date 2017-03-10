package pe.devpicon.android.marvelcomic.ui.activities.favourite

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_comic_detail.*
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.data.ComicContract
import pe.devpicon.android.marvelcomic.data.DatabaseManager
import pe.devpicon.android.marvelcomic.entities.Comic
import pe.devpicon.android.marvelcomic.ui.activities.BaseActivity
import pe.devpicon.android.marvelcomic.utils.loadImage
import pe.devpicon.android.marvelcomic.utils.parseDate

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

        val comicFromExtra = intent.getParcelableExtra<Comic>("comic")
        Log.d(TAG, "---->"+comicFromExtra.toString())


        Log.d(TAG, comic.toString())

        val cursor = DatabaseManager.getInstance(this).queryComicById(comicId)
        if (cursor != null && cursor.count > 0) {
            showProgressDialog()
            cursor.moveToFirst()
            val comic = getComicFromCursor(cursor)
            Log.d(javaClass.simpleName, comic.toString())

            with(comic) {
                img_comic_detail_cover.loadImage(imageURL)
                txt_comic_detail_name.text = title
                txt_comic_detail_price.text = if (price > 0.0) getString(R.string.format_usd, price.toString()) else getString(R.string.message_soldout)
                txt_comic_detail_description.text = if (!description.equals("null", true)) description else getString(R.string.message_not_available)
                txt_comic_detail_onsale_date.text = parseDate(onSaleDate)
                txt_comic_detail_pagecount.text = pageCount.toString()
                txt_comic_detail_series.text = series
                txt_comic_detail_creators.text = ""
                txt_comic_detail_characters.text = ""

            }



            dismissProgressDialog()

        }

    }
}
