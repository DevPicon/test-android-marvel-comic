package pe.devpicon.android.marvelcomic.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import android.util.Log
import pe.devpicon.android.marvelcomic.entities.Comic
import java.sql.SQLException

/**
 * Created by Armando on 8/2/2017.
 */
class ComicDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "comic.db"
        val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val SQL_CREATE_COMIC_TABLE = "CREATE TABLE ${ComicContract.ComicEntry.TABLE_NAME} (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${ComicContract.ComicEntry.COLUMN_NAME_COMIC_ID} TEXT NOT NULL," +
                "${ComicContract.ComicEntry.COLUMN_NAME_TITLE} TEXT NOT NULL," +
                "${ComicContract.ComicEntry.COLUMN_NAME_PRICE} REAL NOT NULL DEFAULT 0.0," +
                "${ComicContract.ComicEntry.COLUMN_NAME_DATE} TEXT NOT NULL," +
                "${ComicContract.ComicEntry.COLUMN_NAME_DESCRIPTION} TEXT NOT NULL," +
                "${ComicContract.ComicEntry.COLUMN_NAME_IMAGE_URL} TEXT NOT NULL," +
                "${ComicContract.ComicEntry.COLUMN_NAME_PAGE_COUNT} INTEGER NOT NULL DEFAULT 0," +
                "${ComicContract.ComicEntry.COLUMN_NAME_SERIES} TEXT NOT NULL, " +
                "${ComicContract.ComicEntry.COLUMN_NAME_CHARACTERS} TEXT NOT NULL, " +
                "${ComicContract.ComicEntry.COLUMN_NAME_CREATORS} TEXT NOT NULL" +
                ");"


        db?.execSQL(SQL_CREATE_COMIC_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS ${ComicContract.ComicEntry.TABLE_NAME}")
        onCreate(db)

    }

}