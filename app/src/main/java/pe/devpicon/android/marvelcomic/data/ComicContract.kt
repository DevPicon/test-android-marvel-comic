package pe.devpicon.android.marvelcomic.data

import android.provider.BaseColumns

/**
 * Created by Armando on 8/2/2017.
 */

object ComicContract {
    class ComicEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "comic"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_COMIC_ID = "comicid"
            val COLUMN_NAME_PRICE = "price"
            val COLUMN_NAME_IMAGE_URL = "imageUrl"
            val COLUMN_NAME_DESCRIPTION = "description"
            val COLUMN_NAME_DATE = "date"
            val COLUMN_NAME_PAGE_COUNT = "pageCount"
            val COLUMN_NAME_SERIES = "series"
            val COLUMN_NAME_CREATORS = "creators"
            val COLUMN_NAME_CHARACTERS = "characters"
        }
    }
}
