package pe.devpicon.android.marvelcomic.adapters

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.comic_item.view.*
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.data.ComicContract
import pe.devpicon.android.marvelcomic.entities.Comic

/**
 * Created by Armando on 10/2/2017.
 */
class FavouriteAdapter(var cursor: Cursor?, val itemClick: OnItemClickListener) : RecyclerView.Adapter<FavouriteAdapter.ComicViewHolder>() {

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        if (cursor == null || !cursor!!.moveToPosition(position)) {
            return
        }

        val comicfromCursor = getComicFromCursor(cursor!!)

        holder?.bindComic(comicfromCursor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return ComicViewHolder(view, itemClick)
    }

    fun getItem(position: Int): Comic? {
        if (cursor != null && cursor!!.count > 0) {
            return getComicFromCursor(cursor!!)
        }

        return null
    }

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

    override fun getItemCount(): Int = cursor?.count ?: 0

    override fun getItemId(position: Int): Long {
        if (cursor == null || !cursor!!.moveToPosition(position)) {
            return 0
        }

        return getComicFromCursor(cursor!!).id as Long

    }


    class ComicViewHolder(itemView: View, val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bindComic(comic: Comic) {
            if (comic != null) {

                with(comic) {
                    Log.d(javaClass.simpleName, comic.toString())

                    Glide.with(itemView.context)
                            .load(thumbnailUrl)
                            .fitCenter()
                            .override(48, 48)
                            .into(itemView.img_comic_cover)

                    itemView.txt_comic_name.text = title
                    itemView.txt_comic_price.text = if (price > 0) price.toString() else itemView.context.getString(R.string.message_not_available)
                    itemView.setOnClickListener { itemClick(this) }
                }
            }
        }

    }

    interface OnItemClickListener {
        operator fun invoke(comic: Comic)
    }
}